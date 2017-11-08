package com.monapizza.monapizza.Core;

import java.util.ArrayList;

/**
 * Created by chita on 01/11/2017.
 */

/*
    Danh sach cau hoi duoc khoi tao tuong ung cho moi Lesson cua mot category
    Question chua 4 cau hoi:
    - Cau 1 la cau hoi chinh (co dap an)
    - 3 cau hoi tiep theo nham muc dich tao ra cac loai cau hoi khac nhau

    - Ham getType tra ve loai cau hoi, UI se dua vao do de phat sinh.
 */

public class Question {

    // Loai cau hoi, dung de khoi tao UI tuong ung cho tung cau hoi
    int type;

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
