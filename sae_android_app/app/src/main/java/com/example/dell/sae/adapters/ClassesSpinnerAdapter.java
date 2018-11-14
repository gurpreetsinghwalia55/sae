package com.example.dell.sae.adapters;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.dell.sae.R;
import com.example.dell.sae.models.EvaluationClass;

import java.util.List;

public class ClassesSpinnerAdapter extends ArrayAdapter<EvaluationClass> {

    private final List<EvaluationClass> classes;
    private Context context;
    private int res;
    private LayoutInflater inflater;

    public ClassesSpinnerAdapter(Context context, @LayoutRes int res, List<EvaluationClass> classes) {
        super(context, res, classes);
        this.classes = classes;
        this.res = res;
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return createItemView(position, convertView, parent);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return createItemView(position, convertView, parent);
    }

    private View createItemView(int position, View convertView, ViewGroup parent) {
        View view = inflater.inflate(res, parent, false);
        TextView className = view.findViewById(R.id.className);
        TextView status = view.findViewById(R.id.status);
        EvaluationClass evaluationClass = classes.get(position);
        className.setText(evaluationClass.getTeacherClass().toString());
        if (evaluationClass.getEvaluationStatus()) {
            status.setText("Evaluated");
            status.setTextColor(ContextCompat.getColor(context, R.color.green));
        } else {
            status.setText("Pending");
            status.setTextColor(ContextCompat.getColor(context, R.color.red));
        }
        return view;
    }
}
