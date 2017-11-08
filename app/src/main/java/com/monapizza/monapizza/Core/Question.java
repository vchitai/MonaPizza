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

/*
    Cac loai question ung voi type:
    0: Cho tu tieng anh, chon 4 dap an tieng viet.
    1: Cho file am thanh tieng anh, chon 4 dap an tieng anh.
    2: Cho file hinh anh, chon dap an tieng anh.
    3: Cho tu tieng viet, chon 4 dap an tieng anh.
 */

public class Question {

    public static final int numberOfTypeQuestion = 4;

    // Loai cau hoi, dung de khoi tao UI tuong ung cho tung cau hoi
    int m_type;

    ArrayList<Word> m_words;

    // Chi tao mot khoi tao duy nhat
    private Question() {}

    Question(int type, ArrayList<Word> words) {
        m_type = type;
        m_words = (ArrayList<Word>)words.clone();
    }

    /*
        Cac ham get duoi day phuc vu cho viec hien thi
     */

    // Lay tu tieng anh tu o thu thu i
    String getEnglish(int i) {
        return m_words.get(i).getEnglish();
    }

    // Lay tu tieng viet tu word i
    String getVietnamese(int i) {
        return m_words.get(i).getVietnamese();
    }

    // lay picture tu word i
    String getPicture(int i) {
        return m_words.get(i).getPicture();
    }

    // Lay file am thanh tu word i
    String getSound(int i) {
        return m_words.get(i).getSound();
    }

    // Loai cau hoi
    int getTypeQuestion() {
        return m_type;
    }

    // Kiem tra ket qua tu nguoi dung
    // id: Id cua tu ma nguoi dung chon
    Boolean checkAnswer(int id) {
        if (id == m_words.get(0).getId())
            return true;
        else
            return false;
    }
}
