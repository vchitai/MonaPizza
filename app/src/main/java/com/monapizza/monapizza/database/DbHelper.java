package com.monapizza.monapizza.database;

import com.monapizza.monapizza.core.Category;
import com.monapizza.monapizza.core.Word;
import com.monapizza.monapizza.core.Lesson;

import java.util.ArrayList;

/**
 * Created by chita on 01/11/2017.
 */

public class DbHelper {

    // Lay danh sach cac tu thuoc level - khoi tao checkpoint
    public ArrayList<Word> getWordInLevel(int level) {
        return new ArrayList<Word>();
    }

    // Lay danh sach cac tu trong mot category (bai kiem tra)
    public ArrayList<Word> getWordInCategory(int category) {
        return new ArrayList<Word>();
    }

    // Lay danh sach cac tu trong mot bai hoc (khoi tao question)
    public ArrayList<Word> getWordInLesson(int category, int lesson) {
        return new ArrayList<Word>();
    }

    // Lay danh sach cac tu trong lesson cua mot category (dung de hien thi tren man hinh)
    public ArrayList<String> getWordEngWordsInLesson(int category, int level) {
        return new ArrayList<String>();
    }

    // Lay danh sach cac loai category (hien thi)
    // Xem dinh nghia trong file Category.java de biet cac thuoc tinh can luu tru
    public ArrayList<Category> getCategoryList() {
        return new ArrayList<Category>();
    }

    // Lay danh sach cac lesson (hien thi)
    // Xem dinh nghia trong file Lesson.java de biet cac thuoc tinh can luu tru
    public ArrayList<Lesson> getLessonList(int category) {
        return new ArrayList<Lesson>();
    }

    // Lay so level trong database
    public int getMaxLevel() {
        return 0;
    }

    // Lay so lesson trong mot category
    public int getNumberOfLesson(int category) {
        return 0;
    }
}
