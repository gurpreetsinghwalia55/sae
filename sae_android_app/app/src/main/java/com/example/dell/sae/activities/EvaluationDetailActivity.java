package com.example.dell.sae.activities;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.example.dell.sae.R;
import com.example.dell.sae.adapters.ViewPagerAdapter;
import com.example.dell.sae.fragments.StudentsListFragment;

public class EvaluationDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluation_detail);

        Toolbar toolbar = findViewById(R.id.eval_detail_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        final TabLayout classesTabLayout = findViewById(R.id.classesTabs);
        final ViewPager classesViewPager = findViewById(R.id.classesViewPager);

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        for (int i = 0; i < 5; i++)
            viewPagerAdapter.addFragment(new StudentsListFragment(), "3COE " + (7 + (3 * i)) + " - " + (7 + (3 * i) + 2));
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
