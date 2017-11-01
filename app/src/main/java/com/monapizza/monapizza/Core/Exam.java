package com.monapizza.monapizza.Core;

import java.util.ArrayList;

/**
 * Created by chita on 01/11/2017.
 */

public class Exam {
    ArrayList<Question> m_questions;
    ArrayList<Integer> m_answer;
    int m_category;
    int m_lesson;
    int m_level;

    Exam(int numberOfCorrectAnswer, int category, int lesson, int level) {

    }

    void setAnswer(int index, int answer) {

    }

    Question getQuestion(int index) {
        return new Question(new ArrayList<Word>());
    }

    boolean getResult() {
        return false;
    }
}

