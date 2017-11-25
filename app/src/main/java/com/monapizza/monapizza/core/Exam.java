package com.monapizza.monapizza.core;

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
    // Danh sach cac tu trong bai hoc lesson cua category
    ArrayList<Word> m_words;

    // level cua Exam
    int m_level;

    // loai category
    int m_category;

    // loai lesson
    int m_lesson;

    // so cau da tra loi dung (bien nay de chon ra tu tiep theo de hoc
    // chon tu chua hoc hoac random chon mot tu khi da hoc het
    int m_numWords;

    // tao duy nhat mot loai khoi tao
    private Exam() {}

    Exam(int level, int category, int lesson) {
        m_level = level;
        m_category = category;
        m_lesson = lesson;
        m_numWords = 0;

        // Khoi tao m_words
        // ********** chua co database
        // m_words = database.getWordInLesson(category, lesson)
    }

    // tra ve mot question hien thi
    Question getQuestion(int index) {
        int type = ThreadLocalRandom.current().nextInt(0, Question.numberOfTypeQuestion + 1);

        if (m_numWords < m_words.size()) {
            ArrayList<Word> new_list = new ArrayList<Word>();
            new_list.add(m_words.get(m_numWords));

            // Ham nay lay 3 tu bat ki trong data base ma khac voi id cua tu hien tai
            //ArrayList<Word> otherThreeWords = database.getThreeRandomWords(m_words.get(m_numWords).getId());

            //new_list.addAll(otherThreeWords);

            Question q = new Question(type, new_list);

            ++m_numWords;

            return q;
        }
        else {
            int pos = ThreadLocalRandom.current().nextInt(0, m_words.size());
            ArrayList<Word> new_list = new ArrayList<Word>();
            new_list.add(m_words.get(pos));

            // Ham nay lay 3 tu bat ki trong data base ma khac voi id cua tu hien tai
            //ArrayList<Word> otherThreeWords = database.getThreeRandomWords(m_words.get(pos).getId());

            //new_list.addAll(otherThreeWords);

            Question q = new Question(type, new_list);

            return q;
        }
    }



}

