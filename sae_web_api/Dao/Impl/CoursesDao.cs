using System.Collections.Generic;
using Microsoft.EntityFrameworkCore;
using Npgsql;
using sae_web_api.Dao.Interfaces;
using sae_web_api.Models;

namespace sae_web_api.Dao.Impl
{
    public class CoursesDao : ICoursesDao
    {
        public List<Courses> GetCourses(int code)
        {
            using (var connection = SqlConnectionManager.GetConnection())
            using (var command = new NpgsqlCommand())
            {
                var Courses = new List<Courses>();
                command.Connection = connection;
                command.CommandText = "select * from  courseslist(@code)";
                command.Parameters.AddWithValue("@code", code);
                var reader = command.ExecuteReader();
                while (reader.Read())
                {
                    var e = new Courses()
                    {
                        Id = int.Parse(reader["id"].ToString()),
                        CourseName = reader["cname"].ToString(),
                        CourseCode = reader["ccode"].ToString()
                    };
                    Courses.Add(e);
                }

                return Courses;
            }
        }
    }
}