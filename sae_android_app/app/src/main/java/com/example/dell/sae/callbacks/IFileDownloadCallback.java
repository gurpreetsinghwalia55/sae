package com.example.dell.sae.callbacks;

import java.io.InputStream;

public interface IFileDownloadCallback extends IApiCallback {
    void onFile(InputStream fileStream);
}
