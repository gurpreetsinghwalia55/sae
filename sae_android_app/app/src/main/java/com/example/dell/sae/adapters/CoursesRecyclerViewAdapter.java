package com.example.dell.sae.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dell.sae.R;

public class CoursesRecyclerViewAdapter extends RecyclerView.Adapter<CoursesRecyclerViewAdapter.MyViewHolder> {
    private Context context;

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        this.context = viewGroup.getContext();
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.courses_list_row, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 5;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        RecyclerView classesRecyclerView;
        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            classesRecyclerView = itemView.findViewById(R.id.classesRecyclerView);
            classesRecyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
            classesRecyclerView.setAdapter(new ClassesRecyclerViewAdapter());
        }
    }
}
