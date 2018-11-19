package com.example.dell.sae.retrofit;

import com.example.dell.sae.Constants;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitProvider {
    public static Retrofit newInstance() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).connectTimeout(30, TimeUnit.MINUTES).readTimeout(30, TimeUnit.MINUTES).writeTimeout(30, TimeUnit.MINUTES).build();
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'hh:mm:ss").create();
        return new Retrofit.Builder()
                .baseUrl(Constants.API_BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }
}
