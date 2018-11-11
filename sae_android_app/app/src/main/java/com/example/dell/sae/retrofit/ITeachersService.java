package com.example.dell.sae.retrofit;

import com.example.dell.sae.models.Teacher;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ITeachersService {
    @GET("teachers/{code}")
    Call<Teacher> getTeacherByCode(@Path("code") String code);
}
