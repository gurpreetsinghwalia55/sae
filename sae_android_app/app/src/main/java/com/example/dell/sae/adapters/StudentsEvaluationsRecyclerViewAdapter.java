package com.example.dell.sae.adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dell.sae.R;
import com.example.dell.sae.Utils;
import com.example.dell.sae.callbacks.IUploadButtonClickListener;
import com.example.dell.sae.models.Evaluation;
import com.github.akashandroid90.imageletter.MaterialLetterIcon;

import java.util.ArrayList;
import java.util.List;

public class StudentsEvaluationsRecyclerViewAdapter extends RecyclerView.Adapter<StudentsEvaluationsRecyclerViewAdapter.MyViewHolder> {
    private Context context;
    private List<Evaluation> evaluations;
    private IUploadButtonClickListener uploadButtonClickListener;

    public StudentsEvaluationsRecyclerViewAdapter(List<Evaluation> evaluations) {
        this.evaluations = evaluations;
    }

    public List<Evaluation> getEvaluations() {
        return evaluations;
    }

    public void setEvaluations(List<Evaluation> evaluations) {
        this.evaluations = evaluations;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        this.context = viewGroup.getContext();
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.mark_students_evaluations_list_row, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int i) {
        final int pos = i;
        final Evaluation evaluation = evaluations.get(i);
        holder.studentName.setText(evaluation.getStudent().getName());
        holder.rollno.setText(evaluation.getStudent().getRollno());
        holder.studentIcon.setLetter((evaluation.getStudent().getName().charAt(0) + "").toUpperCase());
        holder.studentIcon.setShapeColor(ContextCompat.getColor(context, Utils.getRandomColor()));
        if (evaluation.getAnswerSheet() == null || evaluation.getAnswerSheet().isEmpty()) {
            holder.file.setText("Not evaluated yet");
            holder.file.setTextColor(Color.parseColor("#757575"));
            holder.file.setTypeface(Typeface.defaultFromStyle(Typeface.ITALIC));
        } else {
            holder.file.setText("Uploaded");
            holder.file.setTextColor(ContextCompat.getColor(context, R.color.green));
            holder.file.setTypeface(Typeface.DEFAULT);
        }
        if (uploadButtonClickListener != null) {
            holder.uploadButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    uploadButtonClickListener.onUploadButtonClick(pos);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return evaluations.size();
    }

    public void setUploadButtonClickListener(IUploadButtonClickListener uploadButtonClickListener) {
        this.uploadButtonClickListener = uploadButtonClickListener;
    }

    public List<Evaluation> getEvaluationsToUpload() {
        List<Evaluation> result = new ArrayList<>();
        for (Evaluation evaluation : getEvaluations()) {
            if (evaluation.getAnswerSheet() != null && !evaluation.getAnswerSheet().isEmpty()) result.add(evaluation);
        }
        return result;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView studentName, rollno, file;
        MaterialLetterIcon studentIcon;
        ImageView uploadButton;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            studentName = itemView.findViewById(R.id.studentName);
            rollno = itemView.findViewById(R.id.rollno);
            studentIcon = itemView.findViewById(R.id.studentIcon);
            file = itemView.findViewById(R.id.file);
            uploadButton = itemView.findViewById(R.id.uploadButton);
        }
    }
}
