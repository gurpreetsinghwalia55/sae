package com.example.dell.sae.callbacks;

import com.example.dell.sae.models.TeacherCourse;

import java.util.List;

public interface ITeacherCoursesListCallback extends IApiCallback {
    void onTeacherCoursesList(List<TeacherCourse> teacherCourseList);
}
