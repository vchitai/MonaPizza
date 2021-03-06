package com.monapizza.monapizza.core;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

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
    private int m_type;

    // Id Word tuong ung dap an trong cau hoi
    private int m_idAnswer;

    // Danh sach cac tu trong cau hoi
    private ArrayList<Word> m_words;

    // Chi tao mot khoi tao duy nhat
    private Question() {}

    // Ham khoi tao mot cau hoi - xao tron cac dap an
    public Question(int type, ArrayList<Word> words) {
        m_type = type;
        m_words = (ArrayList<Word>)words.clone();

        m_idAnswer = 0;
        for(int i = 0; i <= 5; ++i) {
            int pos1 = ThreadLocalRandom.current().nextInt(0, 4);
            int pos2 = ThreadLocalRandom.current().nextInt(0, 4);
            if (pos1 == pos2) continue;

            if (m_idAnswer == pos1) {
                m_idAnswer = pos2;
            }
            else if (m_idAnswer == pos2) {
                m_idAnswer = pos1;
            }

            Word temp = m_words.get(pos1);
            m_words.set(pos1, m_words.get(pos2));
            m_words.set(pos2, temp);
        }
    }

    // Lay tu tuong ung trong cau hoi - chi so wordId
    public Word getWord(int wordId) {
        return m_words.get(wordId);
    }

    public final static int QUES_TYPE_STR = 0;
    public final static int QUES_TYPE_SOU = 1;
    public final static int QUES_TYPE_PIC = 2;
    // Loai cau hoi
    public int getQuestionType() {
        switch (m_type) {
            case 0:
            case 3:
                return QUES_TYPE_STR;
            case 1:
                return QUES_TYPE_SOU;
            case 2:
                return QUES_TYPE_PIC;
            default:
                return m_type;
        }
    }

    public String getQuestionDesc() {
        switch (m_type) {
            case 0:
                return "Tìm từ tiếng Việt đồng nghĩa với ";
            case 1:
                return "Nghe và chọn từ chính xác";
            case 2:
                return "Xem hình và chọn từ chính xác";
            case 3:
                return "Tìm từ tiếng Anh đồng nghĩa với ";
            default:
                return "";
        }
    }
    // Kiem tra ket qua tu nguoi dung
    // id: Id cua tu ma nguoi dung chon
    public Boolean checkAnswer(int id) {
        return id == m_idAnswer;
    }

    // Lay chi so cua dap an - hien thi
    public int getAnswerId() {
        return m_idAnswer;
    }

    // Lay chi tiet cau hoi
    /*
        Cac loai question ung voi type:
        0: Cho tu tieng anh, chon 4 dap an tieng viet.
        1: Cho file am thanh tieng anh, chon 4 dap an tieng anh.
        2: Cho file hinh anh, chon dap an tieng anh.
        3: Cho tu tieng viet, chon 4 dap an tieng anh.
    */
    public String getQuestion() {
        String result;
        switch (m_type) {
            case 0: {
                result =  m_words.get(m_idAnswer).getEnglish();
                break;
            }
            case 1: {
                result =  m_words.get(m_idAnswer).getSound();
                break;
            }
            case 2: {
                result =  m_words.get(m_idAnswer).getPicture();
                break;
            }
            case 3: {
                result =  m_words.get(m_idAnswer).getVietnamese();
                break;
            }
            default: {
                result =  "";
                break;
            }
        }
        return result;
    }

    // Lay chi tiet cau tra loi tuong ung voi tung loai cau hoi m_type
    public ArrayList<String> getAnswer() {
        ArrayList<String> res = new ArrayList<String>();
        switch (m_type) {
            case 0:
                for(int i = 0; i < 4; ++i)
                    res.add(m_words.get(i).getVietnamese());
                return res;

            case 1:
                for(int i = 0; i < 4; ++i)
                    res.add(m_words.get(i).getEnglish());
                return res;

            case 2:
                for(int i = 0; i < 4; ++i)
                    res.add(m_words.get(i).getEnglish());
                return res;

            case 3:
                for(int i = 0; i < 4; ++i)
                    res.add(m_words.get(i).getEnglish());
                return res;

            default:
                return res;
        }
    }

    /*
        Tra ve loai cau tra loi - hien thi
            0: cau tra loi dang text
            1: cau tra loi dang hinh anh
     */
    public final static int ANS_TYPE_STR = 0;
    public final static int ANS_TYPE_PIC = 1;
    public int getAnswerType() {
        switch (m_type) {
            case 0:
                return ANS_TYPE_STR;
            case 1:
                return ANS_TYPE_STR;
            case 2:
                return ANS_TYPE_STR;
            case 3:
                return ANS_TYPE_STR;

            default:
                return -1;
        }
    }


}
