package com.example.dell.sae.services;

import com.example.dell.sae.callbacks.IFileDownloadCallback;
import com.example.dell.sae.retrofit.IFileDownloadService;
import com.example.dell.sae.retrofit.RetrofitProvider;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class FileDownloaderService {
    public void downloadFile(String url, final IFileDownloadCallback callback) {
        Retrofit retrofit = RetrofitProvider.newInstance();
        IFileDownloadService service = retrofit.create(IFileDownloadService.class);
        service.downloadFile(url).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    callback.onFile(response.body().byteStream());
                } else {
                    callback.onError(new Exception(response.message()));
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                callback.onError(new Exception(t.toString()));
            }
        });
    }
}
