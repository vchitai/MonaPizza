package com.monapizza.monapizza.ui;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.monapizza.monapizza.core.Lesson;
import com.monapizza.monapizza.R;

import java.util.ArrayList;

/**
 * Created by chita on 11/11/2017.
 */

public class LessonsListAdapter extends RecyclerView.Adapter<LessonsListAdapter.ViewHolder> {
    private ArrayList<Lesson> mLessons;

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mLessonTitleTextView;
        public TextView mLessonWordsTextView;
        public Button   mLessonStartButton;

        public ViewHolder(View view) {
            super(view);
            mLessonTitleTextView = (TextView) view.findViewById(R.id.lesson_title);
            mLessonWordsTextView = (TextView) view.findViewById(R.id.lesson_words);
            mLessonStartButton = (Button) view.findViewById(R.id.lesson_start_button);
        }
    }
    public LessonsListAdapter(ArrayList<Lesson> lessons) {
        mLessons = lessons;
    }
    @Override
    public LessonsListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(LessonsListAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
