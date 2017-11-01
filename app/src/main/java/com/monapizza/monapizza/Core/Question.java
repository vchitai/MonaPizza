package com.monapizza.monapizza.Core;

import java.util.ArrayList;

/**
 * Created by chita on 01/11/2017.
 */

public class Question {
    ArrayList<Word> m_words;

    Question(ArrayList<Word> words) {

    }

    String getQuestion() {
        return "";
    }

    ArrayList<String> getAnswers() {
        return new ArrayList<String>();
    }

    int getCorrectAnswer() {
        return 0;
    }

    int getTypeAnswer() {
        return 0;
    }

    int getTypeQuestion() {
        return 0;
    }
}
