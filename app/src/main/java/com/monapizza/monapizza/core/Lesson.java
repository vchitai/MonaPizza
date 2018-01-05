package com.monapizza.monapizza.core;

import com.monapizza.monapizza.MonaPizza;
import com.monapizza.monapizza.R;

import java.util.ArrayList;

/**
 * Created by chita on 11/11/2017.
 */

// Lop lesson, phuc vu muc dich hien thi

public class Lesson {
    // category id
    private int m_category;

    // lesson id
    private int m_lesson;

    // lesson name
    private String m_name;

    // list of words in lesson
    private ArrayList<String> m_wordsList;

    private Lesson() {}

    public Lesson(int category, int lesson, ArrayList<String> wordsList) {
        m_category = category;
        m_lesson = lesson;
        m_name = MonaPizza.getAppContext().getResources().getString(R.string.lessons_title) + Integer.toString(lesson);
        m_wordsList = wordsList;
    }

    public ArrayList<String> getWordsList() {
        return m_wordsList;
    }

    public String getName() {
        return m_name;
    }
}
