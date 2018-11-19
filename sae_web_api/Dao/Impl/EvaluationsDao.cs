using System;
using System.Collections.Generic;
using System.Linq;
using Npgsql;
using NpgsqlTypes;
using sae_web_api.Dao.Interfaces;
using sae_web_api.Models;

namespace sae_web_api.Dao.Impl
{
    public class EvaluationsDao : IEvaluationsDao
    {
        public List<Evaluation> GetClassEvaluationDetail(int classId, int examId)
        {
            using (var studentsConnection = SqlConnectionManager.GetConnection())
            using (var studentsCommand = new NpgsqlCommand())
            {
                studentsCommand.Connection = studentsConnection;
                studentsCommand.CommandText = "select * from getStudentsByClass(@cid)";
                studentsCommand.Parameters.AddWithValue("@cid", classId);
                var studentsReader = studentsCommand.ExecuteReader();
                var evaluations = new List<Evaluation>();
                while (studentsReader.Read())
                {
                    var e = new Evaluation()
                    {
                        Student = new Student()
                        {
                            Id = int.Parse(studentsReader["id"].ToString()),
                            Rollno = studentsReader["rollno"].ToString(),
                            Name = studentsReader["name"].ToString()
                        }
                    };
                    using (var evalConnection = SqlConnectionManager.GetConnection())
                    using (var evalCommand = new NpgsqlCommand())
                    {
                        evalCommand.Connection = evalConnection;
                        evalCommand.CommandText = "select * from getStudentEvaluationDetail(@sid, @eid)";
                        evalCommand.Parameters.AddWithValue("@sid", e.Student.Id);
                        evalCommand.Parameters.AddWithValue("@eid", examId);

                        var evalReader = evalCommand.ExecuteReader();
                        if (evalReader.Read())
                        {
                            e.Id = int.Parse(evalReader["id"].ToString());
                            e.Examination = new Examination()
                            {
                                Id = int.Parse(evalReader["exam_id"].ToString()),
                                TotalMarks = int.Parse(evalReader["total_marks"].ToString())
                            };
                            e.Teacher = new Teacher()
                            {
                                Id = int.Parse(evalReader["teacher_id"].ToString())
                            };
                            e.MarksObtained = int.Parse(evalReader["marks_obtained"].ToString());
                            e.DateTime = DateTime.Parse(evalReader["datetime"].ToString());
                            e.AnswerSheet = evalReader["ans_sheet"].ToString();
                            e.Status = true;
                        }
                        else
                        {
                            e.Status = false;
                        }
                    }

                    evaluations.Add(e);
                }

                return evaluations;
            }
        }

        public List<Evaluation> GetUnevaluatedStudents(int cid, int eid)
        {
            return GetClassEvaluationDetail(cid, eid).Where(e => !e.Status).ToList();
        }

        public void CreateEvaluation(Evaluation evaluation)
        {
            using (var connection = SqlConnectionManager.GetConnection())
            using (var command = new NpgsqlCommand())
            {
                command.Connection = connection;
                command.CommandText = "select * from createEvaluation(@eid, @tid, @sid, @m, @d, @ans)";
                command.Parameters.AddWithValue("@eid", evaluation.Examination.Id);
                command.Parameters.AddWithValue("@tid", evaluation.Teacher.Id);
                command.Parameters.AddWithValue("@sid", evaluation.Student.Id);
                command.Parameters.AddWithValue("@m", evaluation.MarksObtained);
                command.Parameters.Add("@d", NpgsqlDbType.Timestamp).Value = evaluation.DateTime;
                command.Parameters.AddWithValue("@ans", evaluation.AnswerSheet);

                command.ExecuteReader();
            }
        }
    }
}