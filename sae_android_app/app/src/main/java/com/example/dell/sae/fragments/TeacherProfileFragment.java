package com.example.dell.sae.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
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

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class TeacherProfileFragment extends Fragment {

    private RecyclerView recentExamsRecyclerView;
    private RecentExamsRecyclerViewAdapter examsAdapter;
    private RelativeLayout seeMoreRow;
    private NestedScrollView nestedScrollView;
    private ProgressBar profileProgressBar, recentExamsProgressBar;
    private Teacher teacher;
    private TextView email, contact, designation;

    public TeacherProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_teacher_profile, container, false);

        // Applying Action Bar
        Toolbar toolbar = rootView.findViewById(R.id.teacher_profile_toolbar);
        toolbar.setTitle(" ");
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        DrawerLayout drawerLayout = getActivity().findViewById(R.id.drawerLayout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(getActivity(), drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        prepareViews(rootView);

        profileProgressBar.setVisibility(View.VISIBLE);
        nestedScrollView.setVisibility(View.INVISIBLE);

        TeachersService service = new TeachersService();
        String teacherCode = getActivity().getIntent().getStringExtra(Constants.EXTRA_TEACHER_CODE);
        service.getTeacherByCode(teacherCode, new ITeacherCallback() {
            @Override
            public void onTeacher(Teacher teacher) {
                Persistence.teacher = teacher;
                TeacherProfileFragment.this.teacher = teacher;
                nestedScrollView.setVisibility(View.VISIBLE);
                profileProgressBar.setVisibility(View.GONE);
                populateViews();
            }

            @Override
            public void onError(Exception e) {
                Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                profileProgressBar.setVisibility(View.GONE);
            }
        });

        return rootView;
    }

    private void populateViews() {
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(teacher.getName());

        email.setText(teacher.getEmail());
        contact.setText(teacher.getContact());
        designation.setText(teacher.getDesignation());

        recentExamsRecyclerView.setVisibility(View.GONE);
        seeMoreRow.setVisibility(View.GONE);
        recentExamsProgressBar.setVisibility(View.VISIBLE);

        ExaminationsService service = new ExaminationsService();
        service.getExaminationsByTeacher(teacher.getId(), 3, new IExaminationsListCallback() {
            @Override
            public void onExaminationsList(List<Examination> examinationsList) {
                recentExamsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                examsAdapter = new RecentExamsRecyclerViewAdapter(examinationsList);
                recentExamsRecyclerView.setAdapter(examsAdapter);

                recentExamsRecyclerView.setVisibility(View.VISIBLE);
                seeMoreRow.setVisibility(View.VISIBLE);
                recentExamsProgressBar.setVisibility(View.GONE);
                examsAdapter.setOnItemClickCallback(new ExamsListItemClickCallback() {
                    @Override
                    public void onItemClick(Examination exam) {
                        Intent intent = new Intent(getContext(), EvaluationDetailActivity.class);
                        intent.putExtra(Constants.EXTRA_EXAMINATION, Parcels.wrap(exam));
                        startActivity(intent);
                    }
                });

                seeMoreRow.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.teacher_fragment_container, new ExamsFragment());
                        fragmentTransaction.commit();
                        ((NavigationView) getActivity().findViewById(R.id.nav_view)).setCheckedItem(R.id.nav_exams);
                    }
                });
            }

            @Override
            public void onError(Exception e) {
                Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                recentExamsProgressBar.setVisibility(View.GONE);
            }
        });
    }

    private void prepareViews(View rootView) {
        recentExamsRecyclerView = rootView.findViewById(R.id.recentExamsRecyclerView);
        seeMoreRow = rootView.findViewById(R.id.seeMoreRow);
        profileProgressBar = rootView.findViewById(R.id.profileProgressBar);
        nestedScrollView = rootView.findViewById(R.id.profileNsv);
        email = rootView.findViewById(R.id.email);
        contact = rootView.findViewById(R.id.contact);
        designation = rootView.findViewById(R.id.designation);
        recentExamsProgressBar = rootView.findViewById(R.id.recentExamsProgressBar);
    }

}
