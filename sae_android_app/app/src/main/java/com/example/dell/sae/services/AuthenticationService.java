package com.example.dell.sae.services;

import com.example.dell.sae.callbacks.ILoginCallback;
import com.example.dell.sae.models.Credential;
import com.example.dell.sae.retrofit.IAuthenticationService;
import com.example.dell.sae.retrofit.RetrofitProvider;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class AuthenticationService {
    public void login(Credential credential, final ILoginCallback callback) {
        Retrofit retrofit = RetrofitProvider.newInstance();
        IAuthenticationService service = retrofit.create(IAuthenticationService.class);
        service.login(credential).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.errorBody() != null) {
                    callback.onError(new Exception(response.message()));
                } else {
                    callback.onLogin(Boolean.parseBoolean(response.body()));
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                callback.onError(new Exception(t.toString()));
            }
        });
    }
}
