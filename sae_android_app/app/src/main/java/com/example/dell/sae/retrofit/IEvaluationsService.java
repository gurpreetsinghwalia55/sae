package com.example.dell.sae.retrofit;

import com.example.dell.sae.models.Evaluation;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface IEvaluationsService {
    @GET("evaluations/class/{cid}/exam/{eid}")
    Call<List<Evaluation>> getClassEvaluationDetail(@Path("cid") int cid, @Path("eid") int eid);

    @GET("evaluations/class/{cid}/exam/{eid}/p")
    Call<List<Evaluation>> getUnevaluatedStudents(@Path("cid") int cid, @Path("eid") int eid);

    @POST("evaluations/evaluate-students")
    Call<String> evaluateStudents(@Body List<Evaluation> evaluations);
}
