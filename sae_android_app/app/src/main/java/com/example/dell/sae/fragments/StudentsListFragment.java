package com.example.dell.sae.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dell.sae.R;
import com.example.dell.sae.adapters.StudentsListRecyclerViewAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class StudentsListFragment extends Fragment {


    public StudentsListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_students_list, container, false);
        RecyclerView studentsListRecyclerView = rootView.findViewById(R.id.studentsListRecyclerView);
        studentsListRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        StudentsListRecyclerViewAdapter adapter = new StudentsListRecyclerViewAdapter();
        studentsListRecyclerView.setAdapter(adapter);
        return rootView;
    }

}
