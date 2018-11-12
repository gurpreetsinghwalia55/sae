package com.example.dell.sae.services;

import com.example.dell.sae.callbacks.ITeacherCoursesListCallback;
import com.example.dell.sae.models.Examination;
import com.example.dell.sae.models.TeacherCourse;
import com.example.dell.sae.retrofit.ICoursesService;
import com.example.dell.sae.retrofit.RetrofitProvider;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class CoursesService {
    public void getTeacherCourses(int tid, final ITeacherCoursesListCallback callback) {
        Retrofit retrofit = RetrofitProvider.newInstance();
        ICoursesService service = retrofit.create(ICoursesService.class);
        service.getTeacherCourses(tid).enqueue(new Callback<List<TeacherCourse>>() {
            @Override
            public void onResponse(Call<List<TeacherCourse>> call, Response<List<TeacherCourse>> response) {
                if (response.errorBody() != null) {
                    callback.onError(new Exception(response.message()));
                } else {
                    callback.onTeacherCoursesList(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<TeacherCourse>> call, Throwable t) {
                callback.onError(new Exception(t.toString()));
            }
        });
    }
}
