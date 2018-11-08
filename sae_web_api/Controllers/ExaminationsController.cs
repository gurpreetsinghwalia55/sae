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
        [HttpGet("teacher/{code}")]
        public ActionResult<List<Examination>> GetExaminationListByTeacherCode(string code)
        {
            var dao = new ExaminationsDao();
            return dao.GetExaminationListByTeacherCode(code);
        }
    }
}
