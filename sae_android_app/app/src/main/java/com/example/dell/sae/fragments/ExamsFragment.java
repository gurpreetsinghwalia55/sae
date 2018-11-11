package com.example.dell.sae.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dell.sae.R;
import com.example.dell.sae.activities.EvaluationDetailActivity;
import com.example.dell.sae.adapters.RecentExamsRecyclerViewAdapter;
import com.example.dell.sae.callbacks.ExamsListItemClickCallback;
import com.example.dell.sae.models.Examination;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ExamsFragment extends Fragment {


    public ExamsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_exams, container, false);
        Toolbar toolbar = rootView.findViewById(R.id.exams_toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        DrawerLayout drawerLayout = getActivity().findViewById(R.id.drawerLayout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(getActivity(), drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        RecyclerView examsRecyclerView = rootView.findViewById(R.id.examsRecyclerView);
        examsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        RecentExamsRecyclerViewAdapter adapter = new RecentExamsRecyclerViewAdapter(new ArrayList<Examination>());
        examsRecyclerView.setAdapter(adapter);

        adapter.setOnItemClickCallback(new ExamsListItemClickCallback() {
            @Override
            public void onItemClick(String exam) {
                startActivity(new Intent(getContext(), EvaluationDetailActivity.class));
            }
        });

        return rootView;
    }

}
