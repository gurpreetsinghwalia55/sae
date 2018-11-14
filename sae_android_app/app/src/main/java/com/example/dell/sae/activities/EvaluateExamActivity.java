package com.example.dell.sae.activities;

import android.os.Bundle;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dell.sae.Persistence;
import com.example.dell.sae.R;
import com.example.dell.sae.adapters.ExaminationSpinnerAdapter;
import com.example.dell.sae.adapters.StudentsEvaluationsRecyclerViewAdapter;
import com.example.dell.sae.callbacks.IExaminationsListCallback;
import com.example.dell.sae.models.Examination;
import com.example.dell.sae.services.ExaminationsService;
import com.example.dell.sae.services.TeachersService;

import java.util.List;

public class EvaluateExamActivity extends AppCompatActivity {

    private int count = 10;
    private AppCompatSpinner examSpinner;
    private AppCompatSpinner classSpinner;
    private RecyclerView studentsEvaluationsRecyclerView;
    private ExaminationSpinnerAdapter examAdapter;
    private ArrayAdapter<String> classAdapter;
    private NestedScrollView nestedScrollView;
    private ProgressBar evaluateExamProgressBar;
    private List<Examination> exams;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluate_exam);

        // Applying ActionBar
        Toolbar toolbar = findViewById(R.id.evaluate_exam_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(VectorDrawableCompat.create(getResources(), R.drawable.ic_close_black_24dp, getTheme()));

        prepareViews();

        evaluateExamProgressBar.setVisibility(View.VISIBLE);
        nestedScrollView.setVisibility(View.GONE);

        ExaminationsService service = new ExaminationsService();
        service.getPendingExaminationsByTeacher(Persistence.teacher.getId(), new IExaminationsListCallback() {
            @Override
            public void onExaminationsList(List<Examination> examinationsList) {
                EvaluateExamActivity.this.exams = examinationsList;
                if (exams.size() == 0) {
                    Toast.makeText(EvaluateExamActivity.this, "No pending exams to evaluate", Toast.LENGTH_SHORT).show();
                    finish();
                }
                examAdapter = new ExaminationSpinnerAdapter(EvaluateExamActivity.this, R.layout.examination_spinner_row, examinationsList);
                examSpinner.setAdapter(examAdapter);

                examSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        TeachersService teachersService = new TeachersService();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
                examSpinner.setSelection(0);

                classAdapter = new ArrayAdapter<String>(EvaluateExamActivity.this, android.R.layout.simple_spinner_item, new String[]{"3COE 20 - 23", "3COE 7 - 9"});
                classAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                classSpinner.setAdapter(classAdapter);

                studentsEvaluationsRecyclerView.setLayoutManager(new LinearLayoutManager(EvaluateExamActivity.this, LinearLayoutManager.VERTICAL, false));
                final StudentsEvaluationsRecyclerViewAdapter adapter = new StudentsEvaluationsRecyclerViewAdapter();
                adapter.setCount(count);
                studentsEvaluationsRecyclerView.setAdapter(adapter);

                evaluateExamProgressBar.setVisibility(View.GONE);
                nestedScrollView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onError(Exception e) {
                Toast.makeText(EvaluateExamActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                evaluateExamProgressBar.setVisibility(View.GONE);
            }
        });
    }

    private void prepareViews() {
        examSpinner = findViewById(R.id.examinationSpinner);
        classSpinner = findViewById(R.id.classSpinner);
        studentsEvaluationsRecyclerView = findViewById(R.id.studentsEvaluationsRecyclerView);
        evaluateExamProgressBar = findViewById(R.id.evaluateExamProgressBar);
        nestedScrollView = findViewById(R.id.evaluateExamNsv);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_evaluate_exam, menu);
        return true;
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
