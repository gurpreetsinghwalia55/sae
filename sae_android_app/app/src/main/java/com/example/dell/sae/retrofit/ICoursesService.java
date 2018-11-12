package com.example.dell.sae.retrofit;

import com.example.dell.sae.models.TeacherCourse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ICoursesService {
    @GET("courses/teacher/{tid}")
    Call<List<TeacherCourse>> getTeacherCourses(@Path("tid") int tid);
}
