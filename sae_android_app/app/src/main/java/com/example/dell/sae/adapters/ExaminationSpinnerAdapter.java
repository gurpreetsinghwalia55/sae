package com.example.dell.sae.adapters;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.dell.sae.R;
import com.example.dell.sae.Utils;
import com.example.dell.sae.models.Examination;

import java.text.SimpleDateFormat;
import java.util.List;

public class ExaminationSpinnerAdapter extends ArrayAdapter<Examination> {
    @NonNull
    private final Context context;
    private final int resource;
    private final List<Examination> exams;
    private LayoutInflater inflater;

    public ExaminationSpinnerAdapter(@NonNull Context context, int resource, List<Examination> exams) {
        super(context, resource);
        this.context = context;
        this.resource = resource;
        this.inflater = LayoutInflater.from(context);
        this.exams = exams;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return createView(position, convertView, parent, resource);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return createView(position, convertView, parent, resource);
    }

    @Override
    public int getCount() {
        return exams.size();
    }

    private View createView(int position, View convertView, ViewGroup parent, int resId) {
        View view = inflater.inflate(resId, parent, false);
        ImageView subjectIcon = view.findViewById(R.id.subjectIcon);
        TextView name, code, examType, date, time;
        name = view.findViewById(R.id.subjectName);
        code = view.findViewById(R.id.subjectCode);
        examType = view.findViewById(R.id.examType);
        date = view.findViewById(R.id.date);
        time = view.findViewById(R.id.time);

        Examination exam = exams.get(position);
        name.setText(exam.getCourse().getCourseName());
        code.setText(exam.getCourse().getCourseCode());
        examType.setText(exam.getExaminationType());
        date.setText(new SimpleDateFormat("dd/MM/yy").format(exam.getDateTime()));
        time.setText(new SimpleDateFormat("h:mm a").format(exam.getDateTime()));
        subjectIcon.setImageDrawable(VectorDrawableCompat.create(context.getResources(), Utils.getSubjectIcon(exam.getCourse().getCourseCode()), context.getTheme()));

        return view;
    }

}
