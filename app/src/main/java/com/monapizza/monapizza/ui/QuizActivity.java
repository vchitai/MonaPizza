package com.monapizza.monapizza.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.monapizza.monapizza.R;
import com.monapizza.monapizza.core.Exam;
import com.monapizza.monapizza.core.Question;
import com.monapizza.monapizza.core.User;

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

        while (!mCurrentExam.checkPassExam(User.getInstance())) {
            Question currentQues  =  mCurrentExam.getQuestion();
            String ques = currentQues.getQuestion();
            int quesType = currentQues.getQuestionType();
            switch (quesType) {
                case Question.QUES_TYPE_STR: {
                    mTextQuestion.setText(ques);
                    mTextQuestion.setVisibility(View.VISIBLE);
                    mImageQuestion.setVisibility(View.GONE);
                    mSoundQuestion.setVisibility(View.GONE);
                    break;
                }
                case Question.QUES_TYPE_SOU: {
                    mTextQuestion.setText("Hear and choose answer");
                    mSoundQuestion.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    });
                    mTextQuestion.setVisibility(View.VISIBLE);
                    mImageQuestion.setVisibility(View.GONE);
                    mSoundQuestion.setVisibility(View.VISIBLE);
                    break;
                }
                case Question.QUES_TYPE_PIC: {
                    //mImageQuestion.setBackground();
                    mTextQuestion.setVisibility(View.GONE);
                    mImageQuestion.setVisibility(View.VISIBLE);
                    mSoundQuestion.setVisibility(View.GONE);
                    break;
                }
                default:
                    break;
            }
        }
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
        mCurrentLesson = intent.getIntExtra("lessonID", -1);
        mCurrentCategory = intent.getIntExtra("categoryID", -1);
        mCurrentLevel = intent.getIntExtra("levelID", -1);
<<<<<<< HEAD
        mCurrentExam = new Exam(mCurrentLevel,mCurrentCategory,mCurrentLesson);
=======
        mCurrentExam = new Exam(mCurrentLevel, mCurrentCategory,mCurrentLesson + 1);
>>>>>>> 71221d5c38b7974d7426e45d541fde8ed4e8de0d
    }
}
