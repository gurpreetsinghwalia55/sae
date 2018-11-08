using System;

namespace sae_web_api.Dao.Interfaces
{
    public interface IAuthenticationDao
    {
        bool login(string username, string password);
    }
}