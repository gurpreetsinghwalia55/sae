using Microsoft.AspNetCore.Mvc;
using sae_web_api.Dao.Impl;
using sae_web_api.Models;

namespace sae_web_api.Controllers
{
    
    [Route("api/[controller]")]
    [ApiController]
    public class TeachersController : ControllerBase
    {
        [HttpGet("{code}")]
        public ActionResult<Teacher> GetTeacherByCode(string code)
        {
            var dao = new TeachersDao();
            return dao.GetTeacherByCode(code);
        }
    }
}