package com.example.dell.sae.services;

import com.example.dell.sae.callbacks.IClassesListCallback;
import com.example.dell.sae.callbacks.IEvaluationClassesListCallback;
import com.example.dell.sae.callbacks.ITeacherCallback;
import com.example.dell.sae.models.EvaluationClass;
import com.example.dell.sae.models.Teacher;
import com.example.dell.sae.models.TeacherClass;
import com.example.dell.sae.retrofit.ITeachersService;
import com.example.dell.sae.retrofit.RetrofitProvider;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class TeachersService {

    public void getClassesByTeacherAndCourse(int tid, int cid, final IClassesListCallback callback) {
        Retrofit retrofit = RetrofitProvider.newInstance();
        ITeachersService service = retrofit.create(ITeachersService.class);
        service.getClassesByTeacherAndCourse(tid, cid).enqueue(new Callback<List<TeacherClass>>() {
            @Override
            public void onResponse(Call<List<TeacherClass>> call, Response<List<TeacherClass>> response) {
                if (response.errorBody() != null) {
                    callback.onError(new Exception(response.message()));
                } else {
                    callback.onClassesList(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<TeacherClass>> call, Throwable t) {
                callback.onError(new Exception(t.toString()));
            }
        });
    }

    public void getTeacherByCode(String code, final ITeacherCallback callback) {
        Retrofit retrofit = RetrofitProvider.newInstance();
        ITeachersService service = retrofit.create(ITeachersService.class);
        service.getTeacherByCode(code).enqueue(new Callback<Teacher>() {
            @Override
            public void onResponse(Call<Teacher> call, Response<Teacher> response) {
                if (response.errorBody() != null) {
                    callback.onError(new Exception(response.message()));
                } else {
                    callback.onTeacher(response.body());
                }
            }

            @Override
            public void onFailure(Call<Teacher> call, Throwable t) {
                callback.onError(new Exception(t.toString()));
            }
        });
    }

    public void getEvaluationClassesByTeacherAndExam(int tid, int eid, final IEvaluationClassesListCallback callback) {
        Retrofit retrofit = RetrofitProvider.newInstance();
        ITeachersService service = retrofit.create(ITeachersService.class);
        service.getEvaluationClassesByTeacherAndExam(tid, eid).enqueue(new Callback<List<EvaluationClass>>() {
            @Override
            public void onResponse(Call<List<EvaluationClass>> call, Response<List<EvaluationClass>> response) {
                if (response.errorBody() != null) {
                    callback.onError(new Exception(response.message()));
                } else {
                    callback.onEvaluationClassesList(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<EvaluationClass>> call, Throwable t) {
                callback.onError(new Exception(t.toString()));
            }
        });
    }
}
