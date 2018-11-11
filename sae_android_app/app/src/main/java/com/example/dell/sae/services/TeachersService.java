package com.example.dell.sae.services;

import com.example.dell.sae.callbacks.ITeacherCallback;
import com.example.dell.sae.models.Teacher;
import com.example.dell.sae.retrofit.ITeachersService;
import com.example.dell.sae.retrofit.RetrofitProvider;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class TeachersService {
    public void getTeacherByCode(String code, final ITeacherCallback callback) {
        Retrofit retrofit = RetrofitProvider.newInstance();
        ITeachersService service = retrofit.create(ITeachersService.class);
        service.getTeacherByCode(code).enqueue(new Callback<Teacher>() {
            @Override
            public void onResponse(Call<Teacher> call, Response<Teacher> response) {
                if (response.errorBody() != null) {
                    callback.onError(new Exception(response.message()));
                } else {
                    callback.onTeacher(response.body());
                }
            }

            @Override
            public void onFailure(Call<Teacher> call, Throwable t) {
                callback.onError(new Exception(t.toString()));
            }
        });
    }
}
