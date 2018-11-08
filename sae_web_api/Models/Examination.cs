namespace sae_web_api.Models
{
    public class Examination
    {
        public int Id { get; set; }
        public string CourseID { get; set; }
        public string CourseName { get; set; }
        public string CourseCode { get; set; }
        public string ExaminationType { get; set; }
        public string DateTime { get; set; }
        public string TotalMarks { get; set; }
    }
}