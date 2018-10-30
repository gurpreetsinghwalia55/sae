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

public class RecentExamsRecyclerViewAdapter extends RecyclerView.Adapter<RecentExamsRecyclerViewAdapter.MyViewHolder> {
    private Context context;

    public RecentExamsRecyclerViewAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new MyViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recent_exams_list_row, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int i) {
        int resId = 0;
        switch (i) {
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
        }

        VectorDrawableCompat drawable = VectorDrawableCompat.create(context.getResources(), resId, context.getTheme());
        holder.subjectIconImageView.setImageDrawable(drawable);
    }

    @Override
    public int getItemCount() {
        return 3;
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
        }
    }
}
