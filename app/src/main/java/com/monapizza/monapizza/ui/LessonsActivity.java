package com.monapizza.monapizza.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.monapizza.monapizza.core.Word;
import com.monapizza.monapizza.R;

import java.util.ArrayList;

public class LessonsActivity extends AppCompatActivity {
    ArrayList<Word> mWords;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lessons);

        String s = getIntent().getStringExtra("words");
        importWords(s);

        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

        RecyclerView myList = (RecyclerView) findViewById(R.id.lessons_list);
        myList.setLayoutManager(layoutManager);
    }

    protected void importWords(String s) {

    }
}
