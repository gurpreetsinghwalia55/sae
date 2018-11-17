package com.example.dell.sae.services;

import android.util.Log;

import com.example.dell.sae.callbacks.IExaminationsListCallback;
import com.example.dell.sae.callbacks.IReferenceAnswerSheetCallback;
import com.example.dell.sae.models.Examination;
import com.example.dell.sae.retrofit.IExaminationsService;
import com.example.dell.sae.retrofit.RetrofitProvider;

import java.io.File;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ExaminationsService {
    public void getExaminationsByTeacher(int tid, int len, final IExaminationsListCallback callback) {
        Retrofit retrofit = RetrofitProvider.newInstance();
        IExaminationsService service = retrofit.create(IExaminationsService.class);
        service.getExaminationsByTeacher(tid, len).enqueue(new Callback<List<Examination>>() {
            @Override
            public void onResponse(Call<List<Examination>> call, Response<List<Examination>> response) {
                if (response.errorBody() != null) {
                    callback.onError(new Exception(response.message()));
                } else {
                    callback.onExaminationsList(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Examination>> call, Throwable t) {
                callback.onError(new Exception(t.toString()));
            }
        });
    }

    public void uploadReferenceAnswerSheet(int eid, String fileName, File file, final IReferenceAnswerSheetCallback callback) {
        RequestBody body = RequestBody.create(MediaType.parse("application/pdf"), file);
        Retrofit retrofit = RetrofitProvider.newInstance();
        IExaminationsService service = retrofit.create(IExaminationsService.class);
        service.uploadReferenceAnswerSheet(eid, body, fileName).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    callback.onSheet(response.body());
                } else {
                    callback.onError(new Exception(response.message()));
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                callback.onError(new Exception(t.toString()));
            }
        });
    }

    public void getPendingExaminationsByTeacher(int tid, final IExaminationsListCallback callback) {
        Retrofit retrofit = RetrofitProvider.newInstance();
        IExaminationsService service = retrofit.create(IExaminationsService.class);
        service.getPendingExaminationsByTeacher(tid).enqueue(new Callback<List<Examination>>() {
            @Override
            public void onResponse(Call<List<Examination>> call, Response<List<Examination>> response) {
                if (response.errorBody() != null) {
                    callback.onError(new Exception(response.message()));
                } else {
                    callback.onExaminationsList(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Examination>> call, Throwable t) {
                callback.onError(new Exception(t.toString()));
            }
        });
    }
}
