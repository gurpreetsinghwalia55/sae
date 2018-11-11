using System;
using System.Collections.Generic;
using Microsoft.EntityFrameworkCore;
using Npgsql;
using sae_web_api.Dao.Interfaces;
using sae_web_api.Models;

namespace sae_web_api.Dao.Impl
{
    public class ExaminationsDao : IExaminationsDao
    {
        public List<Examination> GetExaminationListByTeacherCode(int id, int len)
        {
            using (var connection = SqlConnectionManager.GetConnection())
            using (var command = new NpgsqlCommand())
            {
                var examinations = new List<Examination>();
                command.Connection = connection;
                command.CommandText = "select * from teacherExaminationList(@id, @len)";
                command.Parameters.AddWithValue("@id", id);
                command.Parameters.AddWithValue("@len", len);
                var reader = command.ExecuteReader();
                while (reader.Read())
                {
                    examinations.Add(new Examination() {
                        Id = int.Parse(reader["id"].ToString()),
                        Course = new Course() {
                            Id = int.Parse(reader["c_id"].ToString()),
                            CourseName = reader["cname"].ToString(),
                            CourseCode = reader["ccode"].ToString()
                        },
                        ExaminationType = reader["etype"].ToString(),
                        DateTime = DateTime.Parse(reader["dt"].ToString()),
                        TotalMarks = int.Parse(reader["tmarks"].ToString()),
                        ReferenceAnswerSheet = reader["rans"].ToString()
                    });
                }

                return examinations;
            }
        }
    }
}