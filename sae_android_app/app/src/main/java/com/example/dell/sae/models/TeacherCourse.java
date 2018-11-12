package com.example.dell.sae.models;

import org.parceler.Parcel;

import java.util.List;

@Parcel
public class TeacherCourse {
    Teacher teacher;
    Course course;
    List<TeacherClass> classes;

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public List<TeacherClass> getClasses() {
        return classes;
    }

    public void setClasses(List<TeacherClass> classes) {
        this.classes = classes;
    }
}
