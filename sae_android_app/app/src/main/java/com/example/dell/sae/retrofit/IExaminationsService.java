package com.example.dell.sae.retrofit;

import com.example.dell.sae.models.Examination;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface IExaminationsService {
    @GET("examinations/teacher/{tid}")
    Call<List<Examination>> getExaminationsByTeacher(@Path("tid") int tid, @Query("len") int len);
}
