package com.example.dell.sae.activities;

import android.os.Bundle;
import android.os.Environment;
import android.support.design.button.MaterialButton;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dell.sae.Constants;
import com.example.dell.sae.Persistence;
import com.example.dell.sae.R;
import com.example.dell.sae.Utils;
import com.example.dell.sae.adapters.ClassesSpinnerAdapter;
import com.example.dell.sae.adapters.StudentsListRecyclerViewAdapter;
import com.example.dell.sae.adapters.ViewPagerAdapter;
import com.example.dell.sae.callbacks.IEvaluationClassesListCallback;
import com.example.dell.sae.callbacks.IEvaluationsListCallback;
import com.example.dell.sae.callbacks.IFileDownloadCallback;
import com.example.dell.sae.models.Evaluation;
import com.example.dell.sae.models.EvaluationClass;
import com.example.dell.sae.models.Examination;
import com.example.dell.sae.services.EvaluationsService;
import com.example.dell.sae.services.FileDownloaderService;
import com.example.dell.sae.services.TeachersService;

import org.parceler.Parcels;

import java.io.File;
import java.io.InputStream;
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
    private RecyclerView studentsListRecyclerView;
    private TextView refSheet;
    private MaterialButton openRefSheet;

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
        int resId = Utils.getSubjectIcon(examination.getCourse().getCourseCode());
        subjectCodeIcon.setImageDrawable(VectorDrawableCompat.create(getResources(), resId, getTheme()));
        examType.setText(examination.getExaminationType());
        date.setText(new SimpleDateFormat("dd MMM, yyyy").format(examination.getDateTime()));
        time.setText(new SimpleDateFormat("h:mm a").format(examination.getDateTime()));
        final String fileName = examination.getReferenceAnswerSheet().substring(examination.getReferenceAnswerSheet().lastIndexOf("/") + 1);
        refSheet.setText(fileName);

        openRefSheet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (examination.getReferenceAnswerSheet() != null & !examination.getReferenceAnswerSheet().isEmpty()) {
                    File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + fileName);
                    if (!file.exists()) {
                        FileDownloaderService service = new FileDownloaderService();
                        service.downloadFile(Constants.API_IP_ADDRESS + examination.getReferenceAnswerSheet(), new IFileDownloadCallback() {
                            @Override
                            public void onFile(InputStream fileStream) {
                                File file = Utils.writeFileToDisk(EvaluationDetailActivity.this, fileName, fileStream);
                                Utils.openFile(EvaluationDetailActivity.this, file);
                            }

                            @Override
                            public void onError(Exception e) {
                                Toast.makeText(EvaluationDetailActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else {
                        Utils.openFile(EvaluationDetailActivity.this, file);
                    }
                }
            }
        });

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
                        EvaluationsService service = new EvaluationsService();
                        service.getClassEvaluationDetail(classes.get(i).getTeacherClass().getId(), examination.getId(), new IEvaluationsListCallback() {
                            @Override
                            public void onEvaluationsList(List<Evaluation> evaluationsList) {
                                studentsListRecyclerView.setLayoutManager(new LinearLayoutManager(EvaluationDetailActivity.this, LinearLayoutManager.VERTICAL, false));
                                StudentsListRecyclerViewAdapter adapter = new StudentsListRecyclerViewAdapter(evaluationsList);
                                studentsListRecyclerView.setAdapter(adapter);
                            }

                            @Override
                            public void onError(Exception e) {
                                Toast.makeText(EvaluationDetailActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
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
                Toast.makeText(EvaluationDetailActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void prepareViews() {
        evaluationDetailProgressBar = findViewById(R.id.evaluationDetailProgressBar);
        nestedScrollView = findViewById(R.id.eval_detail_nsv);
        subjectCode = findViewById(R.id.subjectCode);
        examType = findViewById(R.id.examType);
        date = findViewById(R.id.dateTime);
        time = findViewById(R.id.time);
        subjectCodeIcon = findViewById(R.id.subjectCodeIcon);
        status = findViewById(R.id.status);
        classSpinner = findViewById(R.id.classSpinner);
        studentsListRecyclerView = findViewById(R.id.studentsListRecyclerView);
        refSheet = findViewById(R.id.refSheet);
        openRefSheet = findViewById(R.id.openRefSheet);
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
