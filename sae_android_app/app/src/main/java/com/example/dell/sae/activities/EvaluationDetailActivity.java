package com.example.dell.sae.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dell.sae.Constants;
import com.example.dell.sae.Persistence;
import com.example.dell.sae.R;
import com.example.dell.sae.adapters.ClassesSpinnerAdapter;
import com.example.dell.sae.adapters.StudentsListRecyclerViewAdapter;
import com.example.dell.sae.adapters.ViewPagerAdapter;
import com.example.dell.sae.callbacks.IClassesListCallback;
import com.example.dell.sae.callbacks.IEvaluationClassesListCallback;
import com.example.dell.sae.fragments.StudentsListFragment;
import com.example.dell.sae.models.EvaluationClass;
import com.example.dell.sae.models.Examination;
import com.example.dell.sae.models.TeacherClass;
import com.example.dell.sae.services.TeachersService;

import org.parceler.Parcels;

import java.text.SimpleDateFormat;
import java.util.List;

public class EvaluationDetailActivity extends AppCompatActivity {

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
    private AppCompatSpinner classSpinner;
    private ClassesSpinnerAdapter classAdapter;
    private List<EvaluationClass> classes;

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

        examination = Parcels.unwrap(getIntent().getParcelableExtra(Constants.EXTRA_EXAMINATION));

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

        TeachersService service = new TeachersService();
        service.getEvaluationClassesByTeacherAndExam(Persistence.teacher.getId(), examination.getId(), new IEvaluationClassesListCallback() {
            @Override
            public void onEvaluationClassesList(List<EvaluationClass> classesList) {
                classes = classesList;
                classAdapter = new ClassesSpinnerAdapter(EvaluationDetailActivity.this, R.layout.class_spinner_item, classes);
                classSpinner.setAdapter(classAdapter);

                classSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });

                classSpinner.setSelection(0);

                RecyclerView studentsListRecyclerView = findViewById(R.id.studentsListRecyclerView);
                studentsListRecyclerView.setLayoutManager(new LinearLayoutManager(EvaluationDetailActivity.this, LinearLayoutManager.VERTICAL, false));
                StudentsListRecyclerViewAdapter adapter = new StudentsListRecyclerViewAdapter();
                studentsListRecyclerView.setAdapter(adapter);
            }

            @Override
            public void onError(Exception e) {
                Toast.makeText(EvaluationDetailActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void prepareViews() {
        evaluationDetailProgressBar = findViewById(R.id.evaluationDetailProgressBar);
        nestedScrollView = findViewById(R.id.eval_detail_nsv);
        subjectCode = findViewById(R.id.subjectCode);
        examType = findViewById(R.id.examType);
        date = findViewById(R.id.date);
        time = findViewById(R.id.time);
        subjectCodeIcon = findViewById(R.id.subjectCodeIcon);
        status = findViewById(R.id.status);
        classSpinner = findViewById(R.id.classSpinner);
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
