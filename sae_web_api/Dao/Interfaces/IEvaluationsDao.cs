using System;
using System.Collections.Generic;
using Npgsql;
using sae_web_api.Models;

namespace sae_web_api.Dao.Interfaces
{
    public interface IEvaluationsDao
    {
        List<Evaluation> GetClassEvaluationDetail(int classId, int examId);
    }
}