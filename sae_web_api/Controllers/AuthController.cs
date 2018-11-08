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
    public class AuthController : ControllerBase
    {
        [HttpPost("login")]
        [Consumes("application/json")]
        public ActionResult<string> Login([FromBody] Credential credential)
        {
            var dao = new AuthenticationDao();
            return dao.login(credential.username, credential.password).ToString().ToLower();
        }
    }
}