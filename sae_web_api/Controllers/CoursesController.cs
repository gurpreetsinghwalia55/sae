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
        [HttpGet("courseslist/{code}")]
        public ActionResult<List<Courses>> GetCourses(int code)
        {
            var dao = new CoursesDao();
            return dao.GetCourses(code);
        }
    }
}
