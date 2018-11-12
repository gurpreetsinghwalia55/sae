using System.Collections.Generic;
using Microsoft.AspNetCore.Mvc;
using sae_web_api.Dao.Impl;
using sae_web_api.Models;

namespace sae_web_api.Controllers
{
    [Route("api/[controller]")]
    [ApiController]

    public class ExaminationsController : ControllerBase
    {
        [HttpGet("teacher/{id}")]
        public ActionResult<List<Examination>> GetExaminationListByTeacherCode(int id, [FromQuery(Name = "len")] int len)
        {
            var dao = new ExaminationsDao();
            return dao.GetExaminationListByTeacher(id, len);
        }
    }
}
