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
        public List<Examination> GetExaminationListByTeacher(int id, int len)
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
                while (reader.Read()) {
                    var e = new Examination() {
                        Id = int.Parse(reader["id"].ToString()),
                        Course = new Course() {
                            Id = int.Parse(reader["c_id"].ToString()),
                            CourseName = reader["cname"].ToString(),
                            CourseCode = reader["ccode"].ToString()
                        },
                        ExaminationType = reader["etype"].ToString(),
                        DateTime = DateTime.Parse(reader["dt"].ToString()),
                        TotalMarks = int.Parse(reader["tmarks"].ToString()),
                        ReferenceAnswerSheet = reader["rans"].ToString(),
                        EvaluationStatus = false
                    };
                    
                    using (var statusConnection = SqlConnectionManager.GetConnection())
                    using (var statusCommand = new NpgsqlCommand()) {
                        statusCommand.Connection = statusConnection;
                        statusCommand.CommandText = "select * from getExamEvaluationStatus(@tid, @eid)";
                        statusCommand.Parameters.AddWithValue("@tid", id);
                        statusCommand.Parameters.AddWithValue("@eid", e.Id);
                        var statusReader = statusCommand.ExecuteReader();
                        if (statusReader.Read()) {
                            e.EvaluationStatus = statusReader.GetBoolean(0);
                        }
                    }
                    
                    examinations.Add(e);
                }

                return examinations;
            }
        }
    }
}