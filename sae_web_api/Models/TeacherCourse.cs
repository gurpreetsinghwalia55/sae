using System.Collections.Generic;

namespace sae_web_api.Models
{
    public class TeacherCourse
    {
        public Teacher Teacher { get; set; }
        public Course Course { get; set; }
        public List<Class> Classes { get; set; }
    }
}