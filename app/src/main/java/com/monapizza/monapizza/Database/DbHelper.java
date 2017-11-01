package com.monapizza.monapizza.Database;

import com.monapizza.monapizza.Core.Category;
import com.monapizza.monapizza.Core.Word;

import java.util.ArrayList;

/**
 * Created by chita on 01/11/2017.
 */

public class DbHelper {
    public ArrayList<Word> getWordInLesson(int category, int lesson) {
        return new ArrayList<Word>();
    }

    public ArrayList<Word> getWordInCategory(int category) {
        return new ArrayList<Word>();
    }

    public ArrayList<Word> getWordInLevel(int level) {
        return new ArrayList<Word>();
    }

    public ArrayList<Category> getCategoryList() {
        return new ArrayList<Category>();
    }

    public int getMaxLevel() {
        return 0;
    }

    public int getNumberOfLesson(int category) {
        return 0;
    }
}
