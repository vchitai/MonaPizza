package com.monapizza.monapizza.core;

import android.util.Log;

import com.monapizza.monapizza.database.DbHelper;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

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

/*
    Cac loai question ung voi type:
    0: Cho tu tieng anh, chon 4 dap an tieng viet.
    1: Cho file am thanh tieng anh, chon 4 dap an tieng anh.
    2: Cho file hinh anh, chon dap an tieng anh.
    3: Cho tu tieng viet, chon 4 dap an tieng anh.
 */

public class Exam {
    private static final int numberQuestionHaveToPass = 10;

    // Danh sach cac tu trong bai hoc lesson cua category
    private ArrayList<Word> m_words;

    // level cua Exam
    private int m_level;

    // loai category
    private int m_category;

    // loai lesson
    private int m_lesson;

    // so cau da tra loi dung (bien nay de chon ra tu tiep theo de hoc
    // chon tu chua hoc hoac random chon mot tu khi da hoc het
    private int m_numWords;

    // cau hoi hien tai dang duoc tao ra
    private Question currentQuestion;

    // tao duy nhat mot loai khoi tao
    private Exam() {}

    // Word ngay phia sau word pos
    private int nextPos(int pos) {
        if (pos < m_words.size() - 1) {
            pos = pos + 1;
        }
        else
            pos = 0;
        return pos;
    }

    // Lay 3 tu khac bat ky trong list word de tao cau hoi
    private ArrayList<Word> getOtherThreeWords(int id) {
        ArrayList<Word> res = new ArrayList<Word>();

        int pos = ThreadLocalRandom.current().nextInt(0, m_words.size());
        if (m_words.get(pos).getId() == id) pos = nextPos(pos);
        Log.d("checkOther", "vo day roi");
        int cnt = 0;
        while (cnt < 3) {
            if (m_words.get(pos).getId() == id) {
                pos = nextPos(pos);
                continue;
            }
            res.add(m_words.get(pos));
            pos = nextPos(pos);
            cnt = cnt + 1;
        }

        return res;
    }

    // Khoi tao exam tuong ung voi loai cho truoc
    public Exam(int level, int category, int lesson) {
        DbHelper database = Ultility.getDbHelper();
        m_level = level;
        m_category = category;
        m_lesson = lesson;
        m_numWords = 0;

        //Log.d("myTag", "level: " + level + " cat " + category + " lesson " + lesson);

        // checkpoint
        if (m_level != -1 && m_category == -1 && m_lesson == -1) {
            m_words = database.getWordInLevel(m_level);
            return;
        }

        // bai kiem tra cat
        if (m_level == -1 && m_category != -1 && m_lesson == -1) {
            m_words = database.getWordInCategory(category);
            return;
        }

        // bai hoc
        if (m_level == -1 && m_category != -1 && m_lesson != -1) {
            m_words = database.getWordInLesson(category, lesson);
            //Log.d("kichThuocWord", "Kich thuoc m_words: " + m_words.size());
            return;
        }
    }

    // tra ve mot question hien thi
    public Question getQuestion() {
        int type = ThreadLocalRandom.current().nextInt(0, Question.numberOfTypeQuestion);

        if (m_numWords < m_words.size()) {
            ArrayList<Word> new_list = new ArrayList<Word>();
            new_list.add(m_words.get(m_numWords));

            ArrayList<Word> otherThreeWords = getOtherThreeWords(m_words.get(m_numWords).getId());

            new_list.addAll(otherThreeWords);

            Question q = new Question(type, new_list);

            currentQuestion = q;

            return q;
        }
        else {
            int pos = ThreadLocalRandom.current().nextInt(0, m_words.size());
            ArrayList<Word> new_list = new ArrayList<Word>();
            new_list.add(m_words.get(pos));

            ArrayList<Word> otherThreeWords = getOtherThreeWords(m_words.get(pos).getId());

            new_list.addAll(otherThreeWords);

            Question q = new Question(type, new_list);

            currentQuestion = q;

            return q;
        }
    }

    // Kiem tra dap an nguoi dung
    //  Tra ve false: nguoi dung sai, 1: nguoi dung dung
    public Boolean setAnswer(int id) {
        Boolean pass = currentQuestion.checkAnswer(id);
        if (pass) {
            m_numWords = m_numWords + 1;
        }
        return pass;
    }

    // Kiem tra xem da pass exam chua
    public Boolean checkPassExam(User user) {
        if (m_numWords >= Exam.numberQuestionHaveToPass) {
            // cap nhat tien do hoc cua nguoi dung
            user.updateLearningProcess(m_lesson, m_category, m_lesson);
            return true;
        }
        return false;
    }

    public int getNumberQuestionNeedToPass() {
        return numberQuestionHaveToPass;
    }
}

