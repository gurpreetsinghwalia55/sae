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

public class CoursesRecyclerViewAdapter extends RecyclerView.Adapter<CoursesRecyclerViewAdapter.MyViewHolder> {
    private Context context;

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        this.context = viewGroup.getContext();
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.courses_list_row, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int i) {
        int resId = 0;
        switch (i % 5) {
            case 0:
                holder.subjectName.setText("Advanced Data Structures");
                holder.subjectCode.setText("UCS 616");
                resId = R.drawable.ic_ads;
                break;
            case 1:
                holder.subjectName.setText("Computer Architecture");
                holder.subjectCode.setText("UCS 507");
                resId = R.drawable.ic_ca;
                break;
            case 2:
                holder.subjectName.setText("Parallel and Distributed Computing");
                holder.subjectCode.setText("UCS 608");
                resId = R.drawable.ic_cloud_computing;
                break;
            case 3:
                holder.subjectName.setText("Artificial Intelligence");
                holder.subjectCode.setText("UCS 521");
                resId = R.drawable.ic_ai;
                break;
            case 4:
                holder.subjectName.setText("Software Engineering");
                holder.subjectCode.setText("UCS 503");
                resId = R.drawable.ic_se;
                break;
        }
        VectorDrawableCompat drawable = VectorDrawableCompat.create(context.getResources(), resId, context.getTheme());
        holder.subjectIcon.setImageDrawable(drawable);
    }

    @Override
    public int getItemCount() {
        return 5;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        RecyclerView classesRecyclerView;
        TextView subjectName, subjectCode;
        ImageView subjectIcon;
        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            classesRecyclerView = itemView.findViewById(R.id.classesRecyclerView);
            classesRecyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
            classesRecyclerView.setAdapter(new ClassesRecyclerViewAdapter());

            subjectName = itemView.findViewById(R.id.subjectName);
            subjectCode = itemView.findViewById(R.id.subjectCode);
            subjectIcon = itemView.findViewById(R.id.subjectIcon);
        }
    }
}
