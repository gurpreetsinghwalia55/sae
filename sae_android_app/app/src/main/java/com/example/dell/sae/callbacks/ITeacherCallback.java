package com.example.dell.sae.callbacks;

import com.example.dell.sae.models.Teacher;

public interface ITeacherCallback extends IApiCallback {
    void onTeacher(Teacher teacher);
}
