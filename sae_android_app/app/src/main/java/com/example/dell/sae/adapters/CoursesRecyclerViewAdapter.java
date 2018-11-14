package com.example.dell.sae.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dell.sae.R;
import com.example.dell.sae.Utils;
import com.example.dell.sae.models.TeacherCourse;

import java.util.List;

public class CoursesRecyclerViewAdapter extends RecyclerView.Adapter<CoursesRecyclerViewAdapter.MyViewHolder> {

    private Context context;
    private List<TeacherCourse> teacherCourses;

    public CoursesRecyclerViewAdapter(List<TeacherCourse> teacherCourses) {
        this.teacherCourses = teacherCourses;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        this.context = viewGroup.getContext();
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.courses_list_row, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int i) {
        TeacherCourse course = teacherCourses.get(i);

        holder.subjectName.setText(course.getCourse().getCourseName());
        holder.subjectCode.setText(course.getCourse().getCourseCode());

        int resId = Utils.getSubjectIcon(course.getCourse().getCourseCode());

        VectorDrawableCompat drawable = VectorDrawableCompat.create(context.getResources(), resId, context.getTheme());
        holder.subjectIcon.setImageDrawable(drawable);

        holder.classesRecyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        holder.classesRecyclerView.setAdapter(new ClassesRecyclerViewAdapter(course.getClasses()));
    }

    @Override
    public int getItemCount() {
        return teacherCourses.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        RecyclerView classesRecyclerView;
        TextView subjectName, subjectCode;
        ImageView subjectIcon;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            classesRecyclerView = itemView.findViewById(R.id.classesRecyclerView);
            subjectName = itemView.findViewById(R.id.subjectName);
            subjectCode = itemView.findViewById(R.id.subjectCode);
            subjectIcon = itemView.findViewById(R.id.subjectIcon);
        }
    }
}
