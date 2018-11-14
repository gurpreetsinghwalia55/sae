package com.example.dell.sae.callbacks;

import com.example.dell.sae.models.EvaluationClass;

import java.util.List;

public interface IEvaluationClassesListCallback extends IApiCallback {
    void onEvaluationClassesList(List<EvaluationClass> classesList);
}
