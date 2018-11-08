using Microsoft.EntityFrameworkCore;
using Npgsql;
using sae_web_api.Dao.Interfaces;
using sae_web_api.Models;

namespace sae_web_api.Dao.Impl
{
    public class TeachersDao : ITeachersDao
    {
        public Teacher GetTeacherByCode(string code)
        {
            using(var connection = SqlConnectionManager.GetConnection())
            using (var command = new NpgsqlCommand())
            {
                command.Connection = connection;
                command.CommandText = "select * from profileteacherinfo(@code)";
                command.Parameters.AddWithValue("@code", code);
                var reader = command.ExecuteReader();
                if (reader.Read())
                {
                    return new Teacher()
                    {
                        Id = int.Parse(reader["tid"].ToString()),
                        Name = reader["tname"].ToString(),
                        Code = reader["tcode"].ToString(),
                        Contact = reader["tcontact"].ToString(),
                        Designation = reader["tdes"].ToString(),
                        Email = reader["tmail"].ToString()
                    };
                }

                return null;
            }
        }
    }
}