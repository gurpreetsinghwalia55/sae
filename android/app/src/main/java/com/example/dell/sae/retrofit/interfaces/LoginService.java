package com.example.dell.sae.retrofit.interfaces;

import com.example.dell.sae.models.Credential;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface LoginService {

    @POST("login")
    Call<String> login(@Body Credential credential);

}
