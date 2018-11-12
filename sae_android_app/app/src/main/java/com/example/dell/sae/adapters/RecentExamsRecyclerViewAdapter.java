package com.example.dell.sae.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dell.sae.R;
import com.example.dell.sae.callbacks.ExamsListItemClickCallback;
import com.example.dell.sae.models.Examination;

import java.text.SimpleDateFormat;
import java.util.List;

public class RecentExamsRecyclerViewAdapter extends RecyclerView.Adapter<RecentExamsRecyclerViewAdapter.MyViewHolder> {
    private Context context;
    private List<Examination> examinations;
    private ExamsListItemClickCallback callback;

    public RecentExamsRecyclerViewAdapter(List<Examination> examinations) {
        this.examinations = examinations;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        this.context = viewGroup.getContext();
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.recent_exams_list_row, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int i) {
        final Examination examination = examinations.get(i);
        holder.subjectNameTextView.setText(examination.getCourse().getCourseName());
        holder.subjectCodeTextView.setText(examination.getCourse().getCourseCode());
        holder.examTypeTextView.setText(examination.getExaminationType());
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yy");
        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a");
        holder.dateTextView.setText(dateFormat.format(examination.getDateTime()));
        holder.timeTextView.setText(timeFormat.format(examination.getDateTime()));
        int resId = 0;
        switch (examination.getCourse().getCourseCode()) {
            case "UCS608":
                resId = R.drawable.ic_cloud_computing;
                break;
            case "UCS616":
                resId = R.drawable.ic_ads;
                break;
            case "UCS503":
                resId = R.drawable.ic_se;
                break;
            case "UCS507":
                resId = R.drawable.ic_ca;
                break;
            case "UCS521":
                resId = R.drawable.ic_ai;
                break;
        }
        VectorDrawableCompat drawable = VectorDrawableCompat.create(context.getResources(), resId, context.getTheme());
        holder.subjectIconImageView.setImageDrawable(drawable);

        if (examination.getEvaluationStatus()) {
            holder.evalStatus.setText("Evaluated");
            holder.evalStatus.setTextColor(ContextCompat.getColor(context, R.color.green));
        } else {
            holder.evalStatus.setText("Pending");
            holder.evalStatus.setTextColor(ContextCompat.getColor(context, R.color.red));
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (callback != null) {
                    callback.onItemClick(examination);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return examinations.size();
    }

    public void setOnItemClickCallback(ExamsListItemClickCallback callback) {
        this.callback = callback;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView subjectIconImageView;
        TextView subjectNameTextView, subjectCodeTextView, examTypeTextView, dateTextView, timeTextView, evalStatus;
        View itemView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            this.itemView = itemView;
            subjectIconImageView = itemView.findViewById(R.id.subjectIcon);
            subjectNameTextView = itemView.findViewById(R.id.subjectName);
            subjectCodeTextView = itemView.findViewById(R.id.subjectCode);
            examTypeTextView = itemView.findViewById(R.id.examType);
            dateTextView = itemView.findViewById(R.id.date);
            timeTextView = itemView.findViewById(R.id.time);
            evalStatus = itemView.findViewById(R.id.evalStatus);
        }
    }
}
