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

import com.monapizza.monapizza.MonaPizza;
import com.monapizza.monapizza.R;
import com.monapizza.monapizza.core.Ultility;
import com.monapizza.monapizza.core.User;
import com.monapizza.monapizza.ui_adapter.LessonsListAdapter;

public class LessonsActivity extends AppCompatActivity {
    private Button mLessonTestButton;
    private RecyclerView mLessonList;
    private LessonsListAdapter mLessonListAdapter;
    private int mCategoryID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lessons);
        mCategoryID         = getIntent().getIntExtra(getResources().getString(R.string.EA_CategoryID), -1);

        Toolbar toolbar = (Toolbar) findViewById(R.id.lessons_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setTitle(Ultility.getDbHelper().getCategoryList().get(mCategoryID-1).getName());
        mLessonListAdapter = new LessonsListAdapter(mCategoryID);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mLessonList = (RecyclerView) findViewById(R.id.lessons_list);

        mLessonList.setLayoutManager(layoutManager);
        mLessonList.setAdapter(mLessonListAdapter);

        LinearSnapHelper snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(mLessonList);

        mLessonTestButton = (Button)findViewById(R.id.lessons_test_button);
        mLessonTestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), QuizActivity.class);
                intent.putExtra(getResources().getString(R.string.EA_CategoryID), mCategoryID);
                startActivity(intent);
            }
        });

        if (User.getInstance().getStatus(-1, mCategoryID, -1) == User.getInstance().STT_PASS) {
            mLessonTestButton.setText(MonaPizza.getAppContext().getResources().getString(R.string.completed_checkpoint));
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mLessonList.setAdapter(mLessonListAdapter);
        if (User.getInstance().getStatus(-1, mCategoryID, -1) == User.getInstance().STT_PASS) {
            mLessonTestButton.setText(MonaPizza.getAppContext().getResources().getString(R.string.completed_checkpoint));
        }
    }
}
