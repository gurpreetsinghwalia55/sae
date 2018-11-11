package com.example.dell.sae.callbacks;

import com.example.dell.sae.models.Examination;

import java.util.List;

public interface IExaminationsListCallback extends IApiCallback {
    void onExaminationsList(List<Examination> examinationsList);
}
