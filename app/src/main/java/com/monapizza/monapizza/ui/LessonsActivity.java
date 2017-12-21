package com.monapizza.monapizza.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.monapizza.monapizza.R;
import com.monapizza.monapizza.ui_adapter.LessonsListAdapter;

public class LessonsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lessons);

        Toolbar toolbar = (Toolbar) findViewById(R.id.lessons_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        final int          categoryID         = getIntent().getIntExtra("categoryID", -1);
        LessonsListAdapter lessonsListAdapter = new LessonsListAdapter(categoryID);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        RecyclerView lessonList = (RecyclerView) findViewById(R.id.lessons_list);
        LinearSnapHelper snapHelper = new LinearSnapHelper();
        lessonList.setLayoutManager(layoutManager);
        lessonList.setAdapter(lessonsListAdapter);
        snapHelper.attachToRecyclerView(lessonList);
        Button lessonTestButton = (Button)findViewById(R.id.lessons_test_button);
        lessonTestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), QuizActivity.class);
                intent.putExtra("categoryID", categoryID);
                startActivity(intent);
            }
        });
    }
}
