package com.example.dell.sae.callbacks;

import com.example.dell.sae.models.TeacherClass;

import java.util.List;

public interface IClassesListCallback extends IApiCallback {
    void onClassesList(List<TeacherClass> classesList);
}
