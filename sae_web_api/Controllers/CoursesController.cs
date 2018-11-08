using System.Collections.Generic;
using Microsoft.AspNetCore.Mvc;
using sae_web_api.Dao.Impl;
using sae_web_api.Models;

namespace sae_web_api.Controllers
{
    [Route("api/[controller]")]
    [ApiController]

    public class CoursesController : ControllerBase
    {
        [HttpGet("teacher/{code}")]
        public ActionResult<List<TeacherCourse>> GetCourses(int code)
        {
            var dao = new TeacherCoursesDao();
            return dao.GetTeacherCourses(code);
        }
    }
}
