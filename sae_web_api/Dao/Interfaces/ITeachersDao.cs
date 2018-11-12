using System.Collections.Generic;
using sae_web_api.Models;

namespace sae_web_api.Dao.Interfaces
{
    public interface ITeachersDao
    {
        Teacher GetTeacherByCode(string code);
        List<Class> GetClassesByTeacherAndCourse(int tid, int cid);
    }
}