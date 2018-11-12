package com.example.dell.sae.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dell.sae.Constants;
import com.example.dell.sae.Persistence;
import com.example.dell.sae.R;
import com.example.dell.sae.adapters.ViewPagerAdapter;
import com.example.dell.sae.callbacks.IClassesListCallback;
import com.example.dell.sae.fragments.StudentsListFragment;
import com.example.dell.sae.models.Examination;
import com.example.dell.sae.models.TeacherClass;
import com.example.dell.sae.services.TeachersService;

import org.parceler.Parcels;

import java.text.SimpleDateFormat;
import java.util.List;

public class EvaluationDetailActivity extends AppCompatActivity {

    private TabLayout classesTabLayout;
    private ViewPager classesViewPager;
    private ViewPagerAdapter viewPagerAdapter;
    private ProgressBar evaluationDetailProgressBar;
    private NestedScrollView nestedScrollView;
    private Examination examination;
    private Toolbar toolbar;
    private TextView subjectCode;
    private TextView examType;
    private TextView date;
    private TextView time;
    private TextView status;
    private ImageView subjectCodeIcon;
//    private ProgressBar classesProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluation_detail);

        // Applying Action Bar
        toolbar = findViewById(R.id.eval_detail_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        prepareViews();

        examination = (Examination) Parcels.unwrap(getIntent().getParcelableExtra(Constants.EXTRA_EXAMINATION));

        populateViews();
    }

    private void populateViews() {
        evaluationDetailProgressBar.setVisibility(View.VISIBLE);
        nestedScrollView.setVisibility(View.GONE);

        toolbar.setTitle(examination.getCourse().getCourseName());
        status.setText(examination.getEvaluationStatus() ? "Evaluated" : "Pending");
        status.setTextColor(ContextCompat.getColor(this, examination.getEvaluationStatus() ? R.color.green : R.color.red));
        subjectCode.setText(examination.getCourse().getCourseCode());
        int resId = 0;
        switch (examination.getCourse().getCourseCode()) {
            case "UCS608":
                resId = R.drawable.ic_cloud_computing;
                break;
            case "UCS616":
                resId = R.drawable.ic_ads;
                break;
            case "UCS503":
                resId = R.drawable.ic_se;
                break;
            case "UCS507":
                resId = R.drawable.ic_ca;
                break;
            case "UCS521":
                resId = R.drawable.ic_ai;
                break;
        }
        subjectCodeIcon.setImageDrawable(VectorDrawableCompat.create(getResources(), resId, getTheme()));
        examType.setText(examination.getExaminationType());
        date.setText(new SimpleDateFormat("dd MMM, yyyy").format(examination.getDateTime()));
        time.setText(new SimpleDateFormat("h:mm a").format(examination.getDateTime()));

        nestedScrollView.setVisibility(View.VISIBLE);
        evaluationDetailProgressBar.setVisibility(View.GONE);

//        classesProgressBar.setVisibility(View.VISIBLE);
        classesTabLayout.setVisibility(View.GONE);
        classesViewPager.setVisibility(View.GONE);

        TeachersService service = new TeachersService();
        service.getClassesByTeacherAndCourse(Persistence.teacher.getId(), examination.getCourse().getId(), new IClassesListCallback() {
            @Override
            public void onClassesList(List<TeacherClass> classesList) {
                viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
                for (TeacherClass teacherClass : classesList) {
                    viewPagerAdapter.addFragment(new StudentsListFragment(), teacherClass.getYear() + teacherClass.getBranch() + " " + teacherClass.getSectionFrom() + " - " + teacherClass.getSectionTo());
                }
                classesViewPager.setAdapter(viewPagerAdapter);
                classesTabLayout.setupWithViewPager(classesViewPager);

                classesTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                    @Override
                    public void onTabSelected(TabLayout.Tab tab) {
                        if (tab.getPosition() % 2 != 0) {
                            classesTabLayout.setTabTextColors(Color.parseColor("#212121"), Color.parseColor("#23A24D"));
                            classesTabLayout.setSelectedTabIndicatorColor(Color.parseColor("#23A24D"));
                        } else {
                            classesTabLayout.setTabTextColors(Color.parseColor("#212121"), Color.parseColor("#F44336"));
                            classesTabLayout.setSelectedTabIndicatorColor(Color.parseColor("#F44336"));
                        }
                    }

                    @Override
                    public void onTabUnselected(TabLayout.Tab tab) {

                    }

                    @Override
                    public void onTabReselected(TabLayout.Tab tab) {
                        if (tab.getPosition() % 2 != 0) {
                            classesTabLayout.setTabTextColors(Color.parseColor("#212121"), Color.parseColor("#23A24D"));
                            classesTabLayout.setSelectedTabIndicatorColor(Color.parseColor("#23A24D"));
                        } else {
                            classesTabLayout.setTabTextColors(Color.parseColor("#212121"), Color.parseColor("#F44336"));
                            classesTabLayout.setSelectedTabIndicatorColor(Color.parseColor("#F44336"));
                        }
                    }
                });
                classesTabLayout.getTabAt(0).select();

//                classesProgressBar.setVisibility(View.GONE);
                classesTabLayout.setVisibility(View.VISIBLE);
                classesViewPager.setVisibility(View.VISIBLE);
            }

            @Override
            public void onError(Exception e) {
                Toast.makeText(EvaluationDetailActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
//                classesProgressBar.setVisibility(View.GONE);
            }
        });
    }

    private void prepareViews() {
        classesTabLayout = findViewById(R.id.classesTabs);
        classesViewPager = findViewById(R.id.classesViewPager);
        evaluationDetailProgressBar = findViewById(R.id.evaluationDetailProgressBar);
        nestedScrollView = findViewById(R.id.eval_detail_nsv);
        subjectCode = findViewById(R.id.subjectCode);
        examType = findViewById(R.id.examType);
        date = findViewById(R.id.date);
        time = findViewById(R.id.time);
        subjectCodeIcon = findViewById(R.id.subjectCodeIcon);
        status = findViewById(R.id.status);
//        classesProgressBar = findViewById(R.id.classesProgressBar);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            super.onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
