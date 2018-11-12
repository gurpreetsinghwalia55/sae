using System.Collections.Generic;
using sae_web_api.Controllers;
using sae_web_api.Models;

namespace sae_web_api.Dao.Interfaces
{
    public interface IExaminationsDao
    {
        List<Examination> GetExaminationListByTeacher(int id, int len);
    }
}