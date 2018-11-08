using sae_web_api.Models;

namespace sae_web_api.Dao.Interfaces
{
    public interface ITeachersDao
    {
        Teacher GetTeacherByCode(string code);
    }
}