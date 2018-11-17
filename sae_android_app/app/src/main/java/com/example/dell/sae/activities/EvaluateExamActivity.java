package com.example.dell.sae.activities;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.OpenableColumns;
import android.support.annotation.Nullable;
import android.support.design.button.MaterialButton;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dell.sae.Constants;
import com.example.dell.sae.Persistence;
import com.example.dell.sae.R;
import com.example.dell.sae.Utils;
import com.example.dell.sae.adapters.ExaminationSpinnerAdapter;
import com.example.dell.sae.adapters.StudentsEvaluationsRecyclerViewAdapter;
import com.example.dell.sae.callbacks.IEvaluationClassesListCallback;
import com.example.dell.sae.callbacks.IEvaluationsListCallback;
import com.example.dell.sae.callbacks.IExaminationsListCallback;
import com.example.dell.sae.callbacks.IFileDownloadCallback;
import com.example.dell.sae.callbacks.IReferenceAnswerSheetCallback;
import com.example.dell.sae.models.Evaluation;
import com.example.dell.sae.models.EvaluationClass;
import com.example.dell.sae.models.Examination;
import com.example.dell.sae.services.EvaluationsService;
import com.example.dell.sae.services.ExaminationsService;
import com.example.dell.sae.services.FileDownloaderService;
import com.example.dell.sae.services.TeachersService;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.BreakIterator;
import java.util.Calendar;
import java.util.List;

public class EvaluateExamActivity extends AppCompatActivity {

    private int count = 10;
    private AppCompatSpinner examSpinner;
    private AppCompatSpinner classSpinner;
    private RecyclerView studentsEvaluationsRecyclerView;
    private ExaminationSpinnerAdapter examAdapter;
    private ArrayAdapter<EvaluationClass> classAdapter;
    private NestedScrollView nestedScrollView;
    private ProgressBar evaluateExamProgressBar;
    private List<Examination> exams;
    private List<EvaluationClass> classes;
    private TextView refSheet;
    private MaterialButton uploadRefSheet;
    private Examination selectedExam;

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
                        selectedExam = exams.get(i);
                        final String fileName = selectedExam.getReferenceAnswerSheet().substring(selectedExam.getReferenceAnswerSheet().lastIndexOf("/") + 1);
                        SpannableString spannableString = new SpannableString(fileName);
                        spannableString.setSpan(new UnderlineSpan(), 0, spannableString.length(), 0);
                        if (!fileName.isEmpty()) {
                            refSheet.setText(spannableString);
                        } else {
                            refSheet.setText("Not Uploaded");
                        }

                        refSheet.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if (selectedExam.getReferenceAnswerSheet() != null & !selectedExam.getReferenceAnswerSheet().isEmpty()) {
                                    File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + fileName);
                                    if (!file.exists()) {
                                        FileDownloaderService service = new FileDownloaderService();
                                        service.downloadFile(Constants.API_IP_ADDRESS + selectedExam.getReferenceAnswerSheet(), new IFileDownloadCallback() {
                                            @Override
                                            public void onFile(InputStream fileStream) {
                                                File file = Utils.writeFileToDisk(EvaluateExamActivity.this, fileName, fileStream);
                                                Utils.openFile(EvaluateExamActivity.this, file);
                                            }

                                            @Override
                                            public void onError(Exception e) {
                                                Toast.makeText(EvaluateExamActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                    } else {
                                        Utils.openFile(EvaluateExamActivity.this, file);
                                    }
                                }
                            }
                        });

                        uploadRefSheet.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent();
                                intent.setAction(Intent.ACTION_GET_CONTENT);
                                intent.setType("application/pdf");
                                startActivityForResult(intent, Constants.CHOOSE_REF_SHEET_REQUEST_CODE);
                            }
                        });

                        TeachersService teachersService = new TeachersService();
                        teachersService.getPendingEvaluationClassesByTeacherAndExam(Persistence.teacher.getId(), exams.get(i).getId(), new IEvaluationClassesListCallback() {
                            @Override
                            public void onEvaluationClassesList(List<EvaluationClass> classesList) {
                                EvaluateExamActivity.this.classes = classesList;
                                classAdapter = new ArrayAdapter<EvaluationClass>(EvaluateExamActivity.this, android.R.layout.simple_spinner_item, classes);
                                classAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                classSpinner.setAdapter(classAdapter);

                                classSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                        EvaluationsService evaluationsService = new EvaluationsService();
                                        evaluationsService.getUnevaluatedStudents(classes.get(i).getTeacherClass().getId(), selectedExam.getId(), new IEvaluationsListCallback() {
                                            @Override
                                            public void onEvaluationsList(List<Evaluation> evaluationsList) {
                                                studentsEvaluationsRecyclerView.setLayoutManager(new LinearLayoutManager(EvaluateExamActivity.this, LinearLayoutManager.VERTICAL, false));
                                                StudentsEvaluationsRecyclerViewAdapter adapter = new StudentsEvaluationsRecyclerViewAdapter(evaluationsList);
                                                studentsEvaluationsRecyclerView.setAdapter(adapter);
                                            }

                                            @Override
                                            public void onError(Exception e) {

                                            }
                                        });
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> adapterView) {

                                    }
                                });

                                classSpinner.setSelection(0);
                            }

                            @Override
                            public void onError(Exception e) {

                            }
                        });
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
                examSpinner.setSelection(0);

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
        refSheet = findViewById(R.id.refSheet);
        uploadRefSheet = findViewById(R.id.uploadRefSheet);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == Constants.CHOOSE_REF_SHEET_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Uri uri = data.getData();
                String uriString = uri.toString();
                File file = new File(uriString);
                String fileName = "ReferenceAnswerKey_" + selectedExam.getExaminationType() + "_" + selectedExam.getCourse().getCourseCode() + "_";
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(selectedExam.getDateTime());
                fileName += calendar.get(Calendar.DAY_OF_MONTH) + "_" + calendar.get(Calendar.MONTH) + "_" + calendar.get(Calendar.YEAR) + ".pdf";

                if (uriString.startsWith("content")) {
                    try {
                        InputStream inputStream = getContentResolver().openInputStream(uri);
                        file = Utils.writeFileToDisk(EvaluateExamActivity.this, fileName, inputStream);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                ExaminationsService service = new ExaminationsService();
                service.uploadReferenceAnswerSheet(selectedExam.getId(), fileName, file, new IReferenceAnswerSheetCallback() {
                    @Override
                    public void onSheet(String sheet) {
                        refSheet.setText(sheet);
                    }

                    @Override
                    public void onError(Exception e) {
                        Toast.makeText(EvaluateExamActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
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
