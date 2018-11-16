package com.example.dell.sae.activities;

import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.dell.sae.R;
import com.example.dell.sae.fragments.CoursesFragment;
import com.example.dell.sae.fragments.ExamsFragment;
import com.example.dell.sae.fragments.TeacherProfileFragment;

public class TeacherActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private FragmentManager fragmentManager;
    private Handler handler = new Handler();
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher);

        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        drawerLayout = findViewById(R.id.drawerLayout);

        fragmentManager = getSupportFragmentManager();

        if (savedInstanceState == null) {
            changeFragments(new TeacherProfileFragment());
            navigationView.setCheckedItem(R.id.nav_profile);
        }
    }

    private void changeFragments(final Fragment fragment) {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.teacher_fragment_container, fragment, fragment.getClass().toString());
                transaction.addToBackStack(fragment.getClass().toString());
                transaction.commit();
            }
        }, 500);
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.teacher, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        int id = menuItem.getItemId();

        if (id == R.id.nav_profile) {
            changeFragments(new TeacherProfileFragment());
        } else if (id == R.id.nav_exams) {
            changeFragments(new ExamsFragment());
        } else if (id == R.id.nav_subjects) {
            changeFragments(new CoursesFragment());
        } else if (id == R.id.nav_eval) {
            startActivity(new Intent(this, EvaluateExamActivity.class));
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }


}
