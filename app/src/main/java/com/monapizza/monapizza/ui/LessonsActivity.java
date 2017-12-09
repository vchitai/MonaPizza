package com.monapizza.monapizza.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.monapizza.monapizza.R;
import com.monapizza.monapizza.core.Lesson;
import com.monapizza.monapizza.ui_adapter.LessonsListAdapter;

import java.util.ArrayList;

public class LessonsActivity extends AppCompatActivity {
    ArrayList<Lesson> mWords;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lessons);

        int categoryID = getIntent().getIntExtra("categoryID", -1);
        LessonsListAdapter lessonsListAdapter = new LessonsListAdapter(categoryID);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        RecyclerView lessonList = (RecyclerView) findViewById(R.id.lessons_list);
        lessonList.setLayoutManager(layoutManager);
        lessonList.setAdapter(lessonsListAdapter);
    }

    protected void importWords(String s) {

    }
}
