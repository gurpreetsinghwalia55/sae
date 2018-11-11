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
        [HttpGet("teacher/{id}")]
        public ActionResult<List<TeacherCourse>> GetCourses(int id)
        {
            var dao = new TeacherCoursesDao();
            return dao.GetTeacherCourses(id);
        }
    }
}
