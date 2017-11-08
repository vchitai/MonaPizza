package com.monapizza.monapizza.Core;

import java.util.ArrayList;

/**
 * Created by chita on 01/11/2017.
 */

/*
    Exam chua danh sach cac tu can hoc lien quan den Lesson cua category do
    Ho den khi du n tu (tu yeu cau cua UI)
    Ham get se lay ra tu ma user chua hoc duoc (neu chua hoc day du) hoac random neu da hoc day du
    cac tu trong Lesson nay.

    Ham getQuestion se khoi tao cau hoi ung voi mot type nao do, chua tu dang hoc (chua xong hoac random
    tu list m_words), truyen cho UI
 */

public class Exam {
    ArrayList<Word> m_words;

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

