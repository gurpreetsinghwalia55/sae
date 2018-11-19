package com.example.dell.sae.services;

import com.example.dell.sae.callbacks.IExaminationsListCallback;
import com.example.dell.sae.callbacks.IAnswerSheetCallback;
import com.example.dell.sae.models.AnswerSheet;
import com.example.dell.sae.models.Examination;
import com.example.dell.sae.retrofit.IExaminationsService;
import com.example.dell.sae.retrofit.RetrofitProvider;

import java.util.List;

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

    public void uploadReferenceAnswerSheet(int eid, AnswerSheet sheet, final IAnswerSheetCallback callback) {
        Retrofit retrofit = RetrofitProvider.newInstance();
        IExaminationsService service = retrofit.create(IExaminationsService.class);
        service.uploadReferenceAnswerSheet(eid, sheet).enqueue(new Callback<AnswerSheet>() {
            @Override
            public void onResponse(Call<AnswerSheet> call, Response<AnswerSheet> response) {
                if (response.isSuccessful()) {
                    callback.onAnswerSheet(response.body());
                } else {
                    callback.onError(new Exception(response.message()));
                }
            }

            @Override
            public void onFailure(Call<AnswerSheet> call, Throwable t) {
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

    public void uploadStudentAnswerSheet(int evalId, AnswerSheet sheet, final IAnswerSheetCallback callback) {
        Retrofit retrofit = RetrofitProvider.newInstance();
        IExaminationsService service = retrofit.create(IExaminationsService.class);
        service.uploadStudentAnswerSheet(evalId, sheet).enqueue(new Callback<AnswerSheet>() {
            @Override
            public void onResponse(Call<AnswerSheet> call, Response<AnswerSheet> response) {
                if (response.isSuccessful()) {
                    callback.onAnswerSheet(response.body());
                } else {
                    callback.onError(new Exception(response.message()));
                }
            }

            @Override
            public void onFailure(Call<AnswerSheet> call, Throwable t) {
                callback.onError(new Exception(t.toString()));
            }
        });
    }
}
