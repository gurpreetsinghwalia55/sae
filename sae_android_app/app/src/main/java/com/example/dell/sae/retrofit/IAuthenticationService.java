package com.example.dell.sae.retrofit;

import com.example.dell.sae.models.Credential;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface IAuthenticationService {
    @POST("auth/login")
    Call<String> login(@Body Credential credential);
}
