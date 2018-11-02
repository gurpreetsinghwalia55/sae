package com.example.dell.sae.activities;

import android.os.Bundle;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.dell.sae.R;
import com.example.dell.sae.adapters.ExaminationSpinnerAdapter;
import com.example.dell.sae.adapters.StudentsEvaluationsRecyclerViewAdapter;

public class EvaluateExamActivity extends AppCompatActivity {

    private int count = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluate_exam);

        Toolbar toolbar = findViewById(R.id.evaluate_exam_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(VectorDrawableCompat.create(getResources(), R.drawable.ic_close_black_24dp, getTheme()));

        AppCompatSpinner examSpinner = findViewById(R.id.examinationSpinner);
        ExaminationSpinnerAdapter examAdapter = new ExaminationSpinnerAdapter(this, R.layout.examination_spinner_row, new String[]{});
        examSpinner.setAdapter(examAdapter);

        AppCompatSpinner classSpinner = findViewById(R.id.classSpinner);
        ArrayAdapter<String> classAdapter = new ArrayAdapter<String>(this, R.layout.class_spinner_item, new String[]{"3COE 20 - 23", "3COE 7 - 9"});
        classAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        classSpinner.setAdapter(classAdapter);

        RecyclerView studentsEvaluationsRecyclerView = findViewById(R.id.studentsEvaluationsRecyclerView);
        studentsEvaluationsRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        final StudentsEvaluationsRecyclerViewAdapter adapter = new StudentsEvaluationsRecyclerViewAdapter();
        adapter.setCount(count);
        studentsEvaluationsRecyclerView.setAdapter(adapter);

        RelativeLayout showMoreRow = findViewById(R.id.showMoreRow);
        final TextView showMoreText = findViewById(R.id.showMoreText);
        showMoreRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (count == 100) {
                    count = 10;
                    showMoreText.setText("Show More");
                } else {
                    count = 100;
                    showMoreText.setText("Show Less");
                }
                adapter.setCount(count);
                adapter.notifyDataSetChanged();
            }
        });
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
