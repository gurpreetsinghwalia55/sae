package com.example.dell.sae.callbacks;

import com.example.dell.sae.models.Evaluation;

import java.util.List;

public interface IEvaluationsListCallback extends IApiCallback {
    void onEvaluationsList(List<Evaluation> evaluationsList);
}
