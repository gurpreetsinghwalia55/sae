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
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.dell.sae.Constants;
import com.example.dell.sae.Persistence;
import com.example.dell.sae.R;
import com.example.dell.sae.adapters.CoursesRecyclerViewAdapter;
import com.example.dell.sae.adapters.RecentExamsRecyclerViewAdapter;
import com.example.dell.sae.callbacks.ITeacherCallback;
import com.example.dell.sae.callbacks.ITeacherCoursesListCallback;
import com.example.dell.sae.models.Teacher;
import com.example.dell.sae.models.TeacherCourse;
import com.example.dell.sae.services.CoursesService;
import com.example.dell.sae.services.TeachersService;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class CoursesFragment extends Fragment {

    private RecyclerView coursesRecyclerView;
    private ProgressBar coursesProgressBar;

    public CoursesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_courses, container, false);

        // Applying ActionBar
        Toolbar toolbar = rootView.findViewById(R.id.courses_toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        DrawerLayout drawerLayout = getActivity().findViewById(R.id.drawerLayout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(getActivity(), drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        prepareViews(rootView);

        coursesRecyclerView.setVisibility(View.GONE);
        coursesProgressBar.setVisibility(View.VISIBLE);

        if (Persistence.teacher == null) {
            TeachersService teachersService = new TeachersService();
            teachersService.getTeacherByCode(getActivity().getIntent().getStringExtra(Constants.EXTRA_TEACHER_CODE), new ITeacherCallback() {
                @Override
                public void onTeacher(Teacher teacher) {
                    Persistence.teacher = teacher;

                    loadCourses();
                }

                @Override
                public void onError(Exception e) {
                    Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    coursesProgressBar.setVisibility(View.GONE);
                }
            });
        } else {
            loadCourses();
        }

        return rootView;
    }

    private void loadCourses() {
        CoursesService service = new CoursesService();
        service.getTeacherCourses(Persistence.teacher.getId(), new ITeacherCoursesListCallback() {
            @Override
            public void onTeacherCoursesList(List<TeacherCourse> teacherCourseList) {
                coursesRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                coursesRecyclerView.setAdapter(new CoursesRecyclerViewAdapter(teacherCourseList));

                coursesRecyclerView.setVisibility(View.VISIBLE);
                coursesProgressBar.setVisibility(View.GONE);
            }

            @Override
            public void onError(Exception e) {
                Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                coursesProgressBar.setVisibility(View.GONE);
            }
        });
    }

    private void prepareViews(View rootView) {
        coursesRecyclerView = rootView.findViewById(R.id.coursesRecyclerView);
        coursesProgressBar = rootView.findViewById(R.id.coursesProgressBar);
    }

}
