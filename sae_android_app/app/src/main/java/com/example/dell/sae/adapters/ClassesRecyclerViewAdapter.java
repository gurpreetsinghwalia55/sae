package com.example.dell.sae.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.dell.sae.R;
import com.example.dell.sae.models.*;
import java.util.List;

class ClassesRecyclerViewAdapter extends RecyclerView.Adapter<ClassesRecyclerViewAdapter.MyViewHolder> {
    private Context context;
    private List<TeacherClass> classes;

    public ClassesRecyclerViewAdapter(List<TeacherClass> classes) {
        this.classes = classes;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        this.context = viewGroup.getContext();
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.classes_list_row, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        TeacherClass teacherClass = classes.get(i);
        String classText = teacherClass.toString();
        myViewHolder.classTextView.setText(classText);
    }

    @Override
    public int getItemCount() {
        return classes.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView classTextView;
        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            classTextView = itemView.findViewById(R.id.classTextView);
        }
    }
}
