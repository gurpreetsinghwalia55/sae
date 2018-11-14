package com.example.dell.sae.services;

import com.example.dell.sae.callbacks.IEvaluationsListCallback;
import com.example.dell.sae.models.Evaluation;
import com.example.dell.sae.retrofit.IEvaluationsService;
import com.example.dell.sae.retrofit.RetrofitProvider;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class EvaluationsService {
    public void getClassEvaluationDetail(int cid, int eid, final IEvaluationsListCallback callback) {
        Retrofit retrofit = RetrofitProvider.newInstance();
        IEvaluationsService service = retrofit.create(IEvaluationsService.class);
        service.getClassEvaluationDetail(cid, eid).enqueue(new Callback<List<Evaluation>>() {
            @Override
            public void onResponse(Call<List<Evaluation>> call, Response<List<Evaluation>> response) {
                if (response.errorBody() != null) {
                    callback.onError(new Exception(response.message()));
                } else {
                    callback.onEvaluationsList(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Evaluation>> call, Throwable t) {
                callback.onError(new Exception(t.toString()));
            }
        });
    }
}
