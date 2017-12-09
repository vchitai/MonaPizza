package com.monapizza.monapizza.ui_adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.monapizza.monapizza.R;
import com.monapizza.monapizza.core.Ultility;
import com.monapizza.monapizza.core.Word;
import com.monapizza.monapizza.ui.QuizActivity;

import java.util.ArrayList;

/**
 * Created by chita on 11/11/2017.
 */

public class LessonsListAdapter extends RecyclerView.Adapter<LessonsListAdapter.ViewHolder> {
    int mCategoryID;
    int mNumberOfLessons;

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
    public LessonsListAdapter(int categoryID) {
        mCategoryID = categoryID;
        if (categoryID > -1)
            mNumberOfLessons = Ultility.getDbHelper().getNumberOfLesson(categoryID);
    }
    @Override
    public LessonsListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context        context  = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View lessonView = inflater.inflate(R.layout.lesson, parent, false);

        // Return a new holder instance
        LessonsListAdapter.ViewHolder viewHolder = new LessonsListAdapter.ViewHolder(lessonView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(LessonsListAdapter.ViewHolder holder, final int position) {
        holder.mLessonTitleTextView.setText("Lesson " + (position + 1) + "/" + mNumberOfLessons);
        ArrayList<Word> lessonWords = Ultility.getDbHelper().getWordInLesson(mCategoryID,position);
        String wordsList = "";
        for (int i = 0; i<lessonWords.size(); i++)
            wordsList += lessonWords.get(i).getEnglish() + ",";
        holder.mLessonWordsTextView.setText(wordsList);
        holder.mLessonStartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), QuizActivity.class);
                intent.putExtra("categoryID", mCategoryID);
                intent.putExtra("lessonID", position);
                
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mNumberOfLessons;
    }
}
