using System;

namespace sae_web_api.Models
{
    public class Evaluation
    {
        public int? Id { get; set; }
        public Examination Examination { get; set; }
        public Teacher Teacher { get; set; }
        public Student Student { get; set; }
        public int? MarksObtained { get; set; }
        public DateTime? DateTime { get; set; }
        public string AnswerSheet { get; set; }
        public bool Status { get; set; }
    }
}