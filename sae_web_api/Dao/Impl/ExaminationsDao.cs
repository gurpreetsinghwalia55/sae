using System.Collections.Generic;
using Microsoft.EntityFrameworkCore;
using Npgsql;
using sae_web_api.Dao.Interfaces;
using sae_web_api.Models;

namespace sae_web_api.Dao.Impl
{
    public class ExaminationsDao : IExaminationsDao
    {
        public List<Examination> GetExaminationListByTeacherCode(string code)
        {
            using (var connection = SqlConnectionManager.GetConnection())
            using (var command = new NpgsqlCommand())
            {
                var examinations = new List<Examination>();
                command.Connection = connection;
                command.CommandText = "select * from teacherExaminationList(@code)";
                command.Parameters.AddWithValue("@code", code);
                var reader = command.ExecuteReader();
                while (reader.Read())
                {
                    var e = new Examination()
                    {
                        Id = int.Parse(reader["id"].ToString()),
                        CourseID = reader["cid"].ToString(),
                        CourseName = reader["cname"].ToString(),
                        CourseCode = reader["ccode"].ToString(),
                        ExaminationType = reader["etype"].ToString(),
                        DateTime = reader["dt"].ToString(),
                        TotalMarks = reader["tmarks"].ToString()
                    };
                    examinations.Add(e);
                }

                return examinations;
            }
        }
    }
}