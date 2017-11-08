package com.monapizza.monapizza.Database;

import com.monapizza.monapizza.Core.Category;
import com.monapizza.monapizza.Core.Word;

import java.util.ArrayList;

/**
 * Created by chita on 01/11/2017.
 */

public class DbHelper {

    // Lay danh sach cac tu trong mot bai hoc (khoi tao question)
    public ArrayList<Word> getWordInLesson(int category, int lesson) {
        return new ArrayList<Word>();
    }

    // Lay danh sach cac tu trong mot category (bai kiem tra)
    public ArrayList<Word> getWordInCategory(int category) {
        return new ArrayList<Word>();
    }

    // Lay 3 tu ngau nhien trong data base sao cho id cua 3 tu do khac voi id input
    public ArrayList<Word> getThreeRandomWords(int id) {
        return new ArrayList<Word>();
    }

    // Lay danh sach cac tu trong lesson cua mot category (dung de hien thi tren man hinh)
    public ArrayList<String> getWordInLevel(int category, int level) {
        return new ArrayList<String>();
    }

    // Lay danh sach cac loai category (hien thi)
    public ArrayList<Category> getCategoryList() {
        return new ArrayList<Category>();
    }

    public int getMaxLevel() {
        return 0;
    }

    // Lay so lesson trong mot category
    public int getNumberOfLesson(int category) {
        return 0;
    }
}
