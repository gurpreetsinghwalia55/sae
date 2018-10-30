package com.example.dell.sae.fragments;


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
import com.example.dell.sae.adapters.RecentExamsRecyclerViewAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class TeacherProfileFragment extends Fragment {

    public TeacherProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_teacher_profile, container, false);
        Toolbar toolbar = rootView.findViewById(R.id.teacher_profile_toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        DrawerLayout drawerLayout = getActivity().findViewById(R.id.drawerLayout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(getActivity(), drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        RecyclerView recentExamsRecyclerView = rootView.findViewById(R.id.recentExamsRecyclerView);
        recentExamsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        RecentExamsRecyclerViewAdapter adapter = new RecentExamsRecyclerViewAdapter();
        recentExamsRecyclerView.setAdapter(adapter);

        return rootView;
    }

}
