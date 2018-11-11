using System;

namespace sae_web_api.Models
{
    public class Examination
    {
        public int Id { get; set; }
        public Course Course { get; set; }
        public string ExaminationType { get; set; }
        public DateTime DateTime { get; set; }
        public int TotalMarks { get; set; }
        public string ReferenceAnswerSheet { get; set; }
    }
}