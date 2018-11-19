package com.example.dell.sae.retrofit;

import com.example.dell.sae.models.AnswerSheet;
import com.example.dell.sae.models.Examination;

import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface IExaminationsService {
    @GET("examinations/teacher/{tid}")
    Call<List<Examination>> getExaminationsByTeacher(@Path("tid") int tid, @Query("len") int len);

    @GET("examinations/teacher/{tid}/p")
    Call<List<Examination>> getPendingExaminationsByTeacher(@Path("tid") int tid);

    @POST("examinations/ref-ans-sheet/{eid}")
    Call<AnswerSheet> uploadReferenceAnswerSheet(@Path("eid") int eid, @Body AnswerSheet sheet);

    @POST("examinations/student-ans-sheet/{eid}")
    Call<AnswerSheet> uploadStudentAnswerSheet(@Path("eid") int eid, @Body AnswerSheet sheet);
}
