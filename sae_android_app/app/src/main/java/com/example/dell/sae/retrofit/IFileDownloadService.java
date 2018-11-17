package com.example.dell.sae.retrofit;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface IFileDownloadService {
    @GET
    Call<ResponseBody> downloadFile(@Url String url);
}
