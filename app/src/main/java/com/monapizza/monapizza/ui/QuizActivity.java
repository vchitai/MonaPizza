package com.monapizza.monapizza.ui;

import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.monapizza.monapizza.MonaPizza;
import com.monapizza.monapizza.R;
import com.monapizza.monapizza.core.Exam;
import com.monapizza.monapizza.core.Question;
import com.monapizza.monapizza.core.User;

import java.io.IOException;
import java.util.ArrayList;

public class QuizActivity extends AppCompatActivity {
    static final int NUMBER_OF_ANSWERS = 4;
    int                    mCurrentLesson;
    int                    mCurrentCategory;
    int                    mCurrentLevel;
    int                    mCurrentAns;
    Exam                   mCurrentExam;
    Question               mCurrentQuestion;
    ImageView              mImageQuestion;
    TextView               mTextQuestion;
    ImageView              mSoundQuestion;
    ArrayList<Button>      mAnswerButton;
    Button                 mNextButton;
    ProgressBar            mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        assignVariable();
        nextQuestion();
    }

    private void assignVariable() {
        mCurrentAns = -1;
        mImageQuestion = (ImageView) findViewById(R.id.quiz_image_question);
        mTextQuestion = (TextView) findViewById(R.id.quiz_text_question);
        mSoundQuestion = (ImageView) findViewById(R.id.quiz_sound_player);
        mAnswerButton = new ArrayList<Button>();
        mAnswerButton.add((Button) findViewById(R.id.quiz_answers_button1));
        mAnswerButton.add((Button) findViewById(R.id.quiz_answers_button2));
        mAnswerButton.add((Button) findViewById(R.id.quiz_answers_button3));
        mAnswerButton.add((Button) findViewById(R.id.quiz_answers_button4));
        Intent intent = getIntent();
        mCurrentLesson = intent.getIntExtra("lessonID", -2);
        mCurrentCategory = intent.getIntExtra("categoryID", -1);
        mCurrentLevel = intent.getIntExtra("levelID", -1);
        mCurrentExam = new Exam(mCurrentLevel, mCurrentCategory,mCurrentLesson + 1);
        for (int i = 0; i<NUMBER_OF_ANSWERS; i++) {
            final int ans = i;
            mAnswerButton.get(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mCurrentAns != -1)
                        mAnswerButton.get(mCurrentAns).setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                    mCurrentAns = ans;
                    mAnswerButton.get(mCurrentAns).setBackgroundColor(getResources().getColor(R.color.colorAccent));
                }
            });
        }
        ImageView closeButton = (ImageView) findViewById(R.id.quiz_close_button);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mNextButton = (Button) findViewById(R.id.quiz_next_button);
        mProgressBar = (ProgressBar) findViewById(R.id.quiz_progress_bar);
        mProgressBar.setMax(mCurrentExam.getNumberQuestionNeedToPass());
        mProgressBar.setProgress(0);
    }

    private void loadQuestion() {
        mCurrentQuestion  =  mCurrentExam.getQuestion();
        String ques = mCurrentQuestion.getQuestion();
        int quesType = mCurrentQuestion.getQuestionType();
        switch (quesType) {
            case Question.QUES_TYPE_STR: {
                mTextQuestion.setText(mCurrentQuestion.getQuestionDesc() + ques);
                mImageQuestion.setVisibility(View.GONE);
                mSoundQuestion.setVisibility(View.GONE);
                break;
            }
            case Question.QUES_TYPE_SOU: {
                mTextQuestion.setText(mCurrentQuestion.getQuestionDesc());
                MediaPlayer mp = null;
                try {
                    AssetFileDescriptor afd = getAssets().openFd(ques);
                    mp = new MediaPlayer();
                    mp.setDataSource(afd.getFileDescriptor(),afd.getStartOffset(),afd.getLength());
                    mp.prepare();
                    mp.start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                final MediaPlayer mpp = mp;
                mSoundQuestion.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            mpp.stop();
                            mpp.prepare();
                            mpp.start();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });

                mImageQuestion.setVisibility(View.GONE);
                mSoundQuestion.setVisibility(View.VISIBLE);
                break;
            }
            case Question.QUES_TYPE_PIC: {
                mTextQuestion.setText(mCurrentQuestion.getQuestionDesc());
                Drawable drawable = null;
                try {
                    drawable = Drawable.createFromStream(getAssets().open(ques), null);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                mImageQuestion.setBackground(drawable);

                mImageQuestion.setVisibility(View.VISIBLE);
                mSoundQuestion.setVisibility(View.GONE);
                break;
            }
            default:
                break;
        }
    }

    private void loadAnswer() {
        int ansType = mCurrentQuestion.getAnswerType();
        ArrayList<String> answers = mCurrentQuestion.getAnswer();
        switch (ansType) {
            case Question.ANS_TYPE_PIC: {
                for (int i = 0; i<NUMBER_OF_ANSWERS; i++) {
                    String pic = answers.get(i);
                    Drawable drawable = null;
                    try {
                        drawable = Drawable.createFromStream(MonaPizza.getAppContext().getAssets().open(pic), null);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    mAnswerButton.get(i).setBackground(drawable);
                }
                break;
            }
            case Question.ANS_TYPE_STR: {
                for (int i = 0; i<NUMBER_OF_ANSWERS; i++) {
                    String str = answers.get(i);
                    mAnswerButton.get(i).setText(str);
                }
                break;
            }
            default:
                break;
        }
    }

    private void processAnswer() {
        if (mCurrentAns == -1) {
            Toast.makeText(this,"Question Unanswered!",Toast.LENGTH_SHORT).show();
        } else {
            boolean res = mCurrentExam.setAnswer(mCurrentAns);
            if (res) {
                Toast.makeText(this, "Right!", Toast.LENGTH_SHORT).show();
                mProgressBar.incrementProgressBy(1);
            } else {
                Toast.makeText(this, "Wrong!", Toast.LENGTH_SHORT).show();
            }
            if (mCurrentExam.checkPassExam(User.getInstance())) {
                finish();
            } else {
                for (int i = 0; i < NUMBER_OF_ANSWERS; i++) {
                    mAnswerButton.get(i).setClickable(false);
                }
                mNextButton.setOnClickListener(null);
                mNextButton.setText("Next");
                mNextButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        nextQuestion();
                    }
                });
            }
        }
    }

    private void nextQuestion() {
        loadQuestion();
        loadAnswer();
        for (int i = 0; i<NUMBER_OF_ANSWERS; i++) {
            mAnswerButton.get(i).setClickable(true);
        }
        if (mCurrentAns != -1)
            mAnswerButton.get(mCurrentAns).setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
        mCurrentAns = -1;
        mNextButton.setText("Check");
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                processAnswer();
            }
        });
    }
}
