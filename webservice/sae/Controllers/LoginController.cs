using Microsoft.AspNetCore.Mvc;
using sae.Models;
using System.Linq;

namespace sae.Controllers {
    [Route("api/[controller]")]
    [ApiController]
    public class LoginController {

        [HttpGet]
        public ActionResult<string> hello() {
            return "Hello World";
        }

        [HttpPost]
        public ActionResult<bool> login([FromBody] Credential credential) {
            string username = credential.Username;
            string password = credential.Password;
            return username.Equals("johndoe") && password.Equals("test123");
        }
    }
}