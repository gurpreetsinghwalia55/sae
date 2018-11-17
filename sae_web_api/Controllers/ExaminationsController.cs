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
        public ActionResult<string> UploadReferenceAnswerSheet(int eid, [FromForm(Name = "file")] IFormFile file, [FromForm(Name = "name")] string name)
        {
            name = name.Replace("\"", " ").Trim();
            Directory.CreateDirectory(REFERENCE_ANS_SHEET_UPLOAD_PATH);
            using (var stream = new FileStream(Path.Combine(REFERENCE_ANS_SHEET_UPLOAD_PATH, name), FileMode.Create))
            {
                file.CopyTo(stream);
            }

            var dao = new ExaminationsDao();
            return dao.AddReferenceAnswerSheet(eid, $"/api/examinations/ref-ans-sheet/get/{name}").Replace("\"", " ").Trim();
        }

        [HttpGet("ref-ans-sheet/get/{fileName}")]
        public IActionResult GetReferenceAnswerSheet(string fileName)
        {
            var filePath = Path.Combine(REFERENCE_ANS_SHEET_UPLOAD_PATH, fileName);
            var stream = System.IO.File.OpenRead(filePath);
            return File(stream, "application/pdf");
        }
    }
}