package com.example.dell.sae.retrofit;

import com.example.dell.sae.models.Teacher;
import com.example.dell.sae.models.TeacherClass;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ITeachersService {
    @GET("teachers/{code}")
    Call<Teacher> getTeacherByCode(@Path("code") String code);

    @GET("classes/teacher/{tid}/course/{cid}")
    Call<List<TeacherClass>> getClassesByTeacherAndCourse(@Path("tid") int tid, @Path("cid") int cid);
}
