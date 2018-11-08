using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Rewrite.Internal;
using Npgsql;
using sae_web_api.Dao.Interfaces;

namespace sae_web_api.Dao.Impl
{
    public class AuthenticationDao : IAuthenticationDao
    {
        public bool login(string username, string password)
        {
            using (var connection = SqlConnectionManager.GetConnection())
            using (var command = new NpgsqlCommand())
            {
                command.Connection = connection;
                command.CommandText = "select login_auth(@username, @password)";
                command.Parameters.AddWithValue("@username", username);
                command.Parameters.AddWithValue("@password", password);

                var reader = command.ExecuteReader();
                if (reader.Read())
                {
                    return reader.GetInt32(0) == 1;
                }

                return false;
            }
        }
    }
}