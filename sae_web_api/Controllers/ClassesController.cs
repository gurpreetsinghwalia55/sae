using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Mvc;
using sae_web_api.Dao.Impl;
using sae_web_api.Models;

namespace sae_web_api.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class ClassesController : ControllerBase
    {
        [HttpGet("teacher/{tid}/course/{cid}")]
        public ActionResult<List<Class>> GetClassesByTeacherAndCourse(int tid, int cid)
        {
            var dao = new TeachersDao();
            return dao.GetClassesByTeacherAndCourse(tid, cid);
        }
    }
}