package com.monapizza.monapizza.Core;

/**
 * Created by chita on 01/11/2017.
 */

public class Word {
    // Id in database
    int m_wordId;

    // English word
    String m_english;

    // Vietnamese word
    String m_vietnamese;

    // Picture link
    String m_picture;

    // Sound link
    String m_sound;

    // Category id
    int m_category;

    // Lesson id
    int m_lesson;

    // Chi khoi tao bang mot cach
    private Word() {}

    Word(int id, String eng, String vie, String pic, String sou, int cat, int les) {
        m_wordId = id;
        m_english = eng;
        m_vietnamese = vie;
        m_picture = pic;
        m_sound = sou;
        m_category = cat;
        m_lesson = les;
    }

    /*
        Cac ham get thuoc tinh
     */

    int getId() {
        return m_wordId;
    }

    String getEnglish() {
        return m_english;
    }

    String getVietnamese() {
        return m_vietnamese;
    }

    String getPicture() {
        return m_picture;
    }

    String getSound() {
        return m_sound;
    }

    int getCategory() {
        return m_category;
    }

    int getLesson() {
        return m_lesson;
    }
}
