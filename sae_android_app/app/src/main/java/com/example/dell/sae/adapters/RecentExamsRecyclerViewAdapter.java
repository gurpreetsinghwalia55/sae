package com.example.dell.sae.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dell.sae.R;
import com.example.dell.sae.callbacks.ExamsListItemClickCallback;

public class RecentExamsRecyclerViewAdapter extends RecyclerView.Adapter<RecentExamsRecyclerViewAdapter.MyViewHolder> {
    private Context context;
    private int count;
    private ExamsListItemClickCallback callback;

    public RecentExamsRecyclerViewAdapter(Context context, int count) {
        this.context = context;
        this.count = count;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new MyViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recent_exams_list_row, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int i) {
        int resId = 0;
        switch (i % 5) {
            case 0:
                holder.subjectNameTextView.setText("Artificial Intelligence");
                holder.subjectCodeTextView.setText("UCS 521");
                holder.examTypeTextView.setText("MST");
                holder.dateTextView.setText("29/09/18");
                holder.timeTextView.setText("1:00 PM");
                resId = R.drawable.ic_ai;
                break;
            case 1:
                holder.subjectNameTextView.setText("Advanced Data Structures");
                holder.subjectCodeTextView.setText("UCS 616");
                holder.examTypeTextView.setText("MST");
                holder.dateTextView.setText("28/09/18");
                holder.timeTextView.setText("1:00 PM");
                resId = R.drawable.ic_ads;
                break;
            case 2:
                holder.subjectNameTextView.setText("Software Engineering");
                holder.subjectCodeTextView.setText("UCS 503");
                holder.examTypeTextView.setText("MST");
                holder.dateTextView.setText("26/09/18");
                holder.timeTextView.setText("1:00 PM");
                resId = R.drawable.ic_se;
                break;
            case 3:
                holder.subjectNameTextView.setText("Computer Architecture");
                holder.subjectCodeTextView.setText("UCS 507");
                holder.examTypeTextView.setText("MST");
                holder.dateTextView.setText("25/09/18");
                holder.timeTextView.setText("1:00 PM");
                resId = R.drawable.ic_ca;
                break;
            case 4:
                holder.subjectNameTextView.setText("Parallel and Distributed Computing");
                holder.subjectCodeTextView.setText("UCS 608");
                holder.examTypeTextView.setText("MST");
                holder.dateTextView.setText("24/09/18");
                holder.timeTextView.setText("1:00 PM");
                resId = R.drawable.ic_cloud_computing;
                break;
        }

        VectorDrawableCompat drawable = VectorDrawableCompat.create(context.getResources(), resId, context.getTheme());
        holder.subjectIconImageView.setImageDrawable(drawable);
    }

    @Override
    public int getItemCount() {
        return count;
    }

    public void setOnItemClickCallback(ExamsListItemClickCallback callback) {
        this.callback = callback;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView subjectIconImageView;
        TextView subjectNameTextView, subjectCodeTextView, examTypeTextView, dateTextView, timeTextView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            subjectIconImageView = itemView.findViewById(R.id.subjectIcon);
            subjectNameTextView = itemView.findViewById(R.id.subjectName);
            subjectCodeTextView = itemView.findViewById(R.id.subjectCode);
            examTypeTextView = itemView.findViewById(R.id.examType);
            dateTextView = itemView.findViewById(R.id.date);
            timeTextView = itemView.findViewById(R.id.time);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (callback != null) {
                        callback.onItemClick("Hello");
                    }
                }
            });
        }
    }
}
