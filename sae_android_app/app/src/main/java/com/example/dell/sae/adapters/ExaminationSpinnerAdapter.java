package com.example.dell.sae.adapters;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;

import com.example.dell.sae.R;

public class ExaminationSpinnerAdapter extends ArrayAdapter<String> {
    @NonNull
    private final Context context;
    private final int resource;
    private LayoutInflater inflater;

    public ExaminationSpinnerAdapter(@NonNull Context context, int resource, String[] objects) {
        super(context, resource);
        this.context = context;
        this.resource = resource;
        this.inflater = LayoutInflater.from(context);
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
        return 4;
    }

    private View createView(int position, View convertView, ViewGroup parent, int resId) {
        View view = inflater.inflate(resId, parent, false);
        RelativeLayout root = view.findViewById(R.id.root);
        return view;
    }

}
