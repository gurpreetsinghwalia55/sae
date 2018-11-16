package com.example.dell.sae.retrofit;

import com.example.dell.sae.models.EvaluationClass;
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

    @GET("classes/teacher/{tid}/exam/{eid}")
    Call<List<EvaluationClass>> getEvaluationClassesByTeacherAndExam(@Path("tid") int tid, @Path("eid") int eid);

    @GET("classes/teacher/{tid}/exam/{eid}/p")
    Call<List<EvaluationClass>> getPendingEvaluationClassesByTeacherAndExam(@Path("tid") int tid, @Path("eid") int eid);
}
