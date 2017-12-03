package com.monapizza.monapizza.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.monapizza.monapizza.R;
import com.monapizza.monapizza.core.Exam;

public class QuizActivity extends AppCompatActivity {
    int mCurrentLesson;
    int mCurrentCategory;
    int mCurrentLevel;
    Exam mCurrentExam;
    ImageView mImageQuestion;
    TextView mTextQuestion;
    ImageView mSoundQuestion;
    Button mAnswerButton1;
    Button mAnswerButton2;
    Button mAnswerButton3;
    Button mAnswerButton4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        assignVariable();
    }

    private void assignVariable() {
        mImageQuestion = (ImageView) findViewById(R.id.quiz_image_question);
        mTextQuestion = (TextView) findViewById(R.id.quiz_text_question);
        mSoundQuestion = (ImageView) findViewById(R.id.quiz_sound_player);
        mAnswerButton1 = (Button) findViewById(R.id.quiz_answers_button1);
        mAnswerButton2 = (Button) findViewById(R.id.quiz_answers_button2);
        mAnswerButton3 = (Button) findViewById(R.id.quiz_answers_button3);
        mAnswerButton4 = (Button) findViewById(R.id.quiz_answers_button4);
        Intent intent = getIntent();
        mCurrentLesson = intent.getIntExtra("lesson", -1);
        mCurrentCategory = intent.getIntExtra("category", -1);
        mCurrentLevel = intent.getIntExtra("level", -1);
        mCurrentExam = new Exam(mCurrentCategory,mCurrentLesson,mCurrentLevel);
    }
}
