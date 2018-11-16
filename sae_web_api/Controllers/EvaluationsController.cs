using System.Collections.Generic;
using Microsoft.AspNetCore.Mvc;
using sae_web_api.Dao.Impl;
using sae_web_api.Models;

namespace sae_web_api.Controllers
{
    [Route("api/[controller]")]
    [ApiController]

    public class EvaluationsController : ControllerBase
    {
        [HttpGet("class/{cid}/exam/{eid}")]
        public ActionResult<List<Evaluation>> GetClassEvaluationDetail(int cid, int eid)
        {
            var dao = new EvaluationsDao();
            return dao.GetClassEvaluationDetail(cid, eid);
        }
        
        [HttpGet("class/{cid}/exam/{eid}/p")]
        public ActionResult<List<Evaluation>> GetUnevaluatedStudents(int cid, int eid)
        {
            var dao = new EvaluationsDao();
            return dao.GetUnevaluatedStudents(cid, eid);
        }
    }
}
