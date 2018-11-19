using System;
using System.Collections.Generic;
using System.IO;
using System.Json;
using ikvm.extensions;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Razor.Language.Extensions;
using org.apache.pdfbox.pdmodel;
using org.apache.pdfbox.util;
using ParallelDots;
using sae_web_api.Dao.Impl;
using sae_web_api.Models;
using SautinSoft;
using Convert = System.Convert;

namespace sae_web_api.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class EvaluationsController : ControllerBase
    {
        private readonly string STUDENT_ANS_SHEET_UPLOAD_PATH = "D:\\sae\\student_ans_sheets";
        private readonly string REFERENCE_ANS_SHEET_UPLOAD_PATH = "D:\\sae\\ref_ans_sheets";

        [HttpGet("class/{cid}/exam/{eid}")]
        public ActionResult<List<Evaluation>> GetClassEvaluationDetail(int cid, int eid)
        {
            var dao = new EvaluationsDao();
            return dao.GetClassEvaluationDetail(cid, eid);
        }

        [HttpGet("class/{cid}/exam/{eid}/p")]
        public ActionResult<List<Evaluation>> GetUnevaluatedStudents(int cid, int eid)
        {
            var dao = new EvaluationsDao();
            return dao.GetUnevaluatedStudents(cid, eid);
        }

        [HttpPost("evaluate-students")]
        public ActionResult<bool> EvaluateStudents([FromBody] List<Evaluation> evaluations)
        {
            var dao = new EvaluationsDao();
            Directory.CreateDirectory(STUDENT_ANS_SHEET_UPLOAD_PATH);
            foreach (var e in evaluations)
            {
                Console.WriteLine("-------------------------------------------------------");
                Console.WriteLine($"Student Id {e.Student.Id}");
                Console.WriteLine($"DateTime {e.DateTime?.ToString("yyyy-MM-dd")}");
                var date = e.DateTime?.ToString("yyyy_MM_dd");
                var fileName = $"AnswerKey_Student_{e.Student.Id}_{e.Examination.Course.CourseCode}_{date}.pdf";
                var filePath = Path.Combine(STUDENT_ANS_SHEET_UPLOAD_PATH, fileName);
                var bytes = Convert.FromBase64String(e.AnswerSheet);
                System.IO.File.WriteAllBytes(filePath, bytes);
                Console.WriteLine("File Written");

                var refSheetName =
                    e.Examination.ReferenceAnswerSheet.Substring(
                        e.Examination.ReferenceAnswerSheet.LastIndexOf("/") + 1);

                var refSheetPath = Path.Combine(REFERENCE_ANS_SHEET_UPLOAD_PATH, refSheetName);
                Console.WriteLine($"Ref Sheet Path = {refSheetPath}");

                var studentPdf = new PdfFocus();
                Console.WriteLine($"Opening Student Pdf...");
                studentPdf.OpenPdf(filePath);
                var studentAnsText = studentPdf.ToText();
                Console.WriteLine("Student pdf read successfully!");
                studentPdf.ClosePdf();
                Console.WriteLine("Closing student pdf");

                var refPdf = new PdfFocus();
                Console.WriteLine("Opening reference pdf");
                refPdf.OpenPdf(refSheetPath);
                var refAnsText = refPdf.ToText();
                Console.WriteLine("Reference pdf read successfully!");
                refPdf.ClosePdf();
                Console.WriteLine("Closing reference pdf");

                paralleldots pd = new paralleldots("AliC73YnPPScR8dJJEMD8qxinhFTTUjFPmJGs5yknY0");
                Console.WriteLine("Calculating score");
                var similarity = pd.similarity(studentAnsText, refAnsText);

                var json = JsonValue.Parse(similarity);
                var score = double.Parse(json["normalized_score"].ToString());
                Console.WriteLine($"Score = {score}");
                var percent = score / 5.0;
                Console.WriteLine($"Percentage = {percent}");

                e.MarksObtained = (int?) (e.Examination.TotalMarks * percent);

                e.AnswerSheet = $"/api/examinations/student-ans-sheet/get/{fileName}";

                Console.WriteLine("Writing result to database");
                dao.CreateEvaluation(e);
                Console.WriteLine("Written Successfully!");
            }

            return true;
        }
    }
}