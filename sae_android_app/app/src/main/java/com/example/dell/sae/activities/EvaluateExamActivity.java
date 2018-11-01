package com.example.dell.sae.activities;

import android.os.Bundle;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;

import com.example.dell.sae.R;
import com.example.dell.sae.adapters.ExaminationSpinnerAdapter;

public class EvaluateExamActivity extends AppCompatActivity {

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
