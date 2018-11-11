package com.example.dell.sae.services;

import com.example.dell.sae.callbacks.IExaminationsListCallback;
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
}
