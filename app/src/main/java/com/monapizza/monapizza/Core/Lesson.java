package com.monapizza.monapizza.core;

import java.util.ArrayList;

/**
 * Created by chita on 11/11/2017.
 */

public class Lesson {
    // category id
    int m_category;

    // lesson id
    int m_lesson;

    // lesson name
    String m_name;

    // list of words in lesson
    ArrayList<String> m_wordsList;

    private Lesson() {}

    public Lesson(int category, int lesson) {
        m_category = category;
        m_lesson = lesson;

        // Chua co database
        /*
        m_wordsList = database.getWordInLevel(category, lesson);

        int numberLessonInCategory = database.getNumberOfLesson(category);

        m_name = Integer.toString(lesson) + "/" + Integer.toString(numberLessonInCategory);
        */

        m_name = "Chua co ten";
    }

    ArrayList<String> getWordsList() {
        return m_wordsList;
    }

    String getName() {
        return m_name;
    }
}
