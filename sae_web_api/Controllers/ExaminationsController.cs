using System;
using System.Collections.Generic;
using System.IO;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using sae_web_api.Dao.Impl;
using sae_web_api.Models;

namespace sae_web_api.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class ExaminationsController : ControllerBase
    {
        private readonly string REFERENCE_ANS_SHEET_UPLOAD_PATH = "D:\\sae\\ref_ans_sheets";
        private readonly string STUDENT_ANS_SHEET_UPLOAD_PATH = "D:\\sae\\student_ans_sheets";

        [HttpGet("teacher/{id}")]
        public ActionResult<List<Examination>> GetExaminationListByTeacherCode(int id,
            [FromQuery(Name = "len")] int len)
        {
            var dao = new ExaminationsDao();
            return dao.GetExaminationListByTeacher(id, len);
        }

        [HttpGet("teacher/{id}/p")]
        public ActionResult<List<Examination>> GetPendingExaminationListByTeacherCode(int id)
        {
            var dao = new ExaminationsDao();
            return dao.GetPendingExaminationListByTeacher(id);
        }

        [HttpPost("ref-ans-sheet/{eid}")]
        public ActionResult<AnswerSheet> UploadReferenceAnswerSheet(int eid, [FromBody] AnswerSheet sheet)
        {
            Directory.CreateDirectory(REFERENCE_ANS_SHEET_UPLOAD_PATH);
            if (sheet?.FileData.Length > 0)
            {
                var filePath = Path.Combine(REFERENCE_ANS_SHEET_UPLOAD_PATH, sheet.FileName);
                var bytes = Convert.FromBase64String(sheet.FileData);
                System.IO.File.WriteAllBytes(filePath, bytes);
                
                var dao = new ExaminationsDao();
                string path = dao.AddReferenceAnswerSheet(eid, $"/api/examinations/ref-ans-sheet/get/{sheet.FileName}");
                sheet.FileData = "";
                sheet.FilePath = path;
                return sheet;
            }
            
            return new AnswerSheet();
        }
        
        [HttpPost("student-ans-sheet/{eid}")]
        public ActionResult<AnswerSheet> UploadStudentAnswerSheet(int eid, [FromBody] AnswerSheet sheet)
        {
            Directory.CreateDirectory(STUDENT_ANS_SHEET_UPLOAD_PATH);
            if (sheet?.FileData.Length > 0)
            {
                var filePath = Path.Combine(STUDENT_ANS_SHEET_UPLOAD_PATH, sheet.FileName);
                var bytes = Convert.FromBase64String(sheet.FileData);
                System.IO.File.WriteAllBytes(filePath, bytes);
                
                var dao = new ExaminationsDao();
                string path = dao.AddStudentAnswerSheet(eid, $"/api/examinations/student-ans-sheet/get/{sheet.FileName}");
                sheet.FileData = "";
                sheet.FilePath = path;
                return sheet;
            }
            
            return new AnswerSheet();
        }

        [HttpGet("ref-ans-sheet/get/{fileName}")]
        public IActionResult GetReferenceAnswerSheet(string fileName)
        {
            var filePath = Path.Combine(REFERENCE_ANS_SHEET_UPLOAD_PATH, fileName);
            var stream = System.IO.File.OpenRead(filePath);
            return File(stream, "application/pdf");
        }
        
        [HttpGet("student-ans-sheet/get/{fileName}")]
        public IActionResult GetStudentAnswerSheet(string fileName)
        {
            var filePath = Path.Combine(REFERENCE_ANS_SHEET_UPLOAD_PATH, fileName);
            var stream = System.IO.File.OpenRead(filePath);
            return File(stream, "application/pdf");
        }
    }
}