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
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.dell.sae.Constants;
import com.example.dell.sae.Persistence;
import com.example.dell.sae.R;
import com.example.dell.sae.activities.EvaluationDetailActivity;
import com.example.dell.sae.adapters.RecentExamsRecyclerViewAdapter;
import com.example.dell.sae.callbacks.ExamsListItemClickCallback;
import com.example.dell.sae.callbacks.IExaminationsListCallback;
import com.example.dell.sae.callbacks.ITeacherCallback;
import com.example.dell.sae.models.Examination;
import com.example.dell.sae.models.Teacher;
import com.example.dell.sae.services.ExaminationsService;
import com.example.dell.sae.services.TeachersService;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ExamsFragment extends Fragment {


    private RecyclerView examsRecyclerView;
    private ProgressBar examsProgressBar;

    public ExamsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_exams, container, false);

        // Applying ActionBar
        Toolbar toolbar = rootView.findViewById(R.id.exams_toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        DrawerLayout drawerLayout = getActivity().findViewById(R.id.drawerLayout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(getActivity(), drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        prepareViews(rootView);
        examsProgressBar.setVisibility(View.VISIBLE);
        examsRecyclerView.setVisibility(View.GONE);

        if (Persistence.teacher == null) {
            TeachersService teachersService = new TeachersService();
            teachersService.getTeacherByCode(getActivity().getIntent().getStringExtra(Constants.EXTRA_TEACHER_CODE), new ITeacherCallback() {
                @Override
                public void onTeacher(Teacher teacher) {
                    Persistence.teacher = teacher;

                    loadExams();
                }

                @Override
                public void onError(Exception e) {
                    Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    examsProgressBar.setVisibility(View.GONE);
                }
            });
        } else {
            loadExams();
        }

        return rootView;
    }

    private void loadExams() {
        ExaminationsService service = new ExaminationsService();
        service.getExaminationsByTeacher(Persistence.teacher.getId(), 0, new IExaminationsListCallback() {
            @Override
            public void onExaminationsList(List<Examination> examinationsList) {
                examsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                RecentExamsRecyclerViewAdapter adapter = new RecentExamsRecyclerViewAdapter(examinationsList);
                examsRecyclerView.setAdapter(adapter);

                adapter.setOnItemClickCallback(new ExamsListItemClickCallback() {
                    @Override
                    public void onItemClick(Examination exam) {
                        Intent intent = new Intent(getContext(), EvaluationDetailActivity.class);
                        intent.putExtra(Constants.EXTRA_EXAMINATION, Parcels.wrap(exam));
                        startActivity(intent);
                    }
                });

                examsRecyclerView.setVisibility(View.VISIBLE);
                examsProgressBar.setVisibility(View.GONE);
            }

            @Override
            public void onError(Exception e) {
                Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                examsProgressBar.setVisibility(View.GONE);
            }
        });
    }

    private void prepareViews(View rootView) {
        examsRecyclerView = rootView.findViewById(R.id.examsRecyclerView);
        examsProgressBar = rootView.findViewById(R.id.examsProgressBar);
    }

}
