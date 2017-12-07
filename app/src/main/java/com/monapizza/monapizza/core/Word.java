package com.monapizza.monapizza.core;

/**
 * Created by chita on 01/11/2017.
 */

public class Word {
    // Id in database
    private int m_wordId;

    // English word
    private String m_english;

    // Vietnamese word
    private String m_vietnamese;

    // Picture link
    private String m_picture;

    // Sound link
    private String m_sound;

    // Category id
    private int m_category;

    // Lesson id
    private int m_lesson;

    // Chi khoi tao bang mot cach
    private Word() {}

    public Word(int id, String eng, String vie, String pic, String sou, int cat, int les) {
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

    public int getId() {
        return m_wordId;
    }

    public String getEnglish() {
        return m_english;
    }

    public String getVietnamese() {
        return m_vietnamese;
    }

    public String getPicture() {
        return m_picture;
    }

    public String getSound() {
        return m_sound;
    }

    public int getCategory() {
        return m_category;
    }

    public int getLesson() {
        return m_lesson;
    }
}
