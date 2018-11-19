package com.example.dell.sae.callbacks;

import com.example.dell.sae.models.AnswerSheet;

public interface IAnswerSheetCallback extends IApiCallback {
    void onAnswerSheet(AnswerSheet sheet);
}
