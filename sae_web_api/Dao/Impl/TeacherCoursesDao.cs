using System.Collections.Generic;
using Microsoft.EntityFrameworkCore;
using Npgsql;
using sae_web_api.Dao.Interfaces;
using sae_web_api.Models;

namespace sae_web_api.Dao.Impl
{
    public class TeacherCoursesDao : ITeacherCoursesDao
    {
        public List<TeacherCourse> GetTeacherCourses(int tid)
        {
            using (var connection = SqlConnectionManager.GetConnection())
            using (var command = new NpgsqlCommand())
            {
                var teacherCourses = new List<TeacherCourse>();
                command.Connection = connection;
                command.CommandText = "select * from  courseslist(@tid)";
                command.Parameters.AddWithValue("@tid", tid);
                var reader = command.ExecuteReader();
                while (reader.Read())
                {
                    var teacherCourse = new TeacherCourse()
                    {
                        Teacher = new Teacher() { Id = tid },
                        Course = new Course()
                        {
                            Id = int.Parse(reader["cid"].ToString()),
                            CourseName = reader["course_name"].ToString(),
                            CourseCode = reader["course_code"].ToString()
                        },
                        Classes = new List<Class>()
                    };
                    using (var classConnection = SqlConnectionManager.GetConnection())
                    using (var classCommand = new NpgsqlCommand())
                    {
                        classCommand.Connection = classConnection;
                        classCommand.CommandText = "select * from getClassesByTeacherAndCourse(@tid, @cid)";
                        classCommand.Parameters.AddWithValue("@tid", tid);
                        classCommand.Parameters.AddWithValue("@cid", teacherCourse.Course.Id);
                        var classReader = classCommand.ExecuteReader();
                        while (classReader.Read())
                        {
                            teacherCourse.Classes.Add(new Class()
                            {
                                Id = int.Parse(classReader["id"].ToString()),
                                Branch = classReader["branch"].ToString(),
                                Year = int.Parse(classReader["year"].ToString()),
                                SectionFrom = int.Parse(classReader["sec_from"].ToString()),
                                SectionTo = int.Parse(classReader["sec_to"].ToString())
                            });
                        }
                    }
                    teacherCourses.Add(teacherCourse);
                }

                return teacherCourses;
            }
        }
    }
}