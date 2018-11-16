using System.Collections.Generic;
using System.Linq;
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

        public List<EvaluationClass> GetPendingEvaluationClassesByTeacherAndExam(int tid, int eid)
        {
            return GetEvaluationClassesByTeacherAndExam(tid, eid).Where(c => !c.EvaluationStatus).ToList();
        }
        
        public List<EvaluationClass> GetEvaluationClassesByTeacherAndExam(int tid, int eid)
        {
            using (var classConnection = SqlConnectionManager.GetConnection())
            using (var classCommand = new NpgsqlCommand())
            {
                classCommand.Connection = classConnection;
                classCommand.CommandText = "select * from getClassesByTeacherAndExam(@tid, @eid)";
                classCommand.Parameters.AddWithValue("@tid", tid);
                classCommand.Parameters.AddWithValue("@eid", eid);
                var classReader = classCommand.ExecuteReader();
                var classes = new List<EvaluationClass>();
                while (classReader.Read())
                {
                    var e = new EvaluationClass();
                    var c = new Class()
                    {
                        Id = int.Parse(classReader["id"].ToString()),
                        Branch = classReader["branch"].ToString(),
                        Year = int.Parse(classReader["year"].ToString()),
                        SectionFrom = int.Parse(classReader["sec_from"].ToString()),
                        SectionTo = int.Parse(classReader["sec_to"].ToString())
                    };
                    e.TeacherClass = c;
                    using (var statusConnection = SqlConnectionManager.GetConnection())
                    using (var statusCommand = new NpgsqlCommand())
                    {
                        statusCommand.Connection = statusConnection;
                        statusCommand.CommandText = "select * from getClassEvaluationStatus(@cid, @eid)";
                        statusCommand.Parameters.AddWithValue("@cid", c.Id);
                        statusCommand.Parameters.AddWithValue("@eid", eid);
                        var statusReader = statusCommand.ExecuteReader();
                        if (statusReader.Read())
                        {
                            e.EvaluationStatus = statusReader.GetBoolean(0);
                        }
                        else
                        {
                            e.EvaluationStatus = false;
                        }
                    }
                    classes.Add(e);
                }

                
                
                return classes;
            }
        }
        
        public List<Class> GetClassesByTeacherAndCourse(int tid, int cid)
        {
            using (var classConnection = SqlConnectionManager.GetConnection())
            using (var classCommand = new NpgsqlCommand())
            {
                classCommand.Connection = classConnection;
                classCommand.CommandText = "select * from getClassesByTeacherAndCourse(@tid, @cid)";
                classCommand.Parameters.AddWithValue("@tid", tid);
                classCommand.Parameters.AddWithValue("@cid", cid);
                var classReader = classCommand.ExecuteReader();
                var classes = new List<Class>();
                while (classReader.Read())
                {
                    var c = new Class()
                    {
                        Id = int.Parse(classReader["id"].ToString()),
                        Branch = classReader["branch"].ToString(),
                        Year = int.Parse(classReader["year"].ToString()),
                        SectionFrom = int.Parse(classReader["sec_from"].ToString()),
                        SectionTo = int.Parse(classReader["sec_to"].ToString())
                    };
                    classes.Add(c);
                }

                return classes;
            }
        }
    }
}