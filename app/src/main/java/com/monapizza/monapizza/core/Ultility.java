package com.monapizza.monapizza.core;

import com.monapizza.monapizza.MonaPizza;
import com.monapizza.monapizza.database.DbHelper;

/**
 * Created by chita on 06/12/2017.
 */

public class Ultility {
    private static Ultility instance = null;

    private static DbHelper mDbHelper;
    private static int numberOfLesson;
    private Ultility() {
        mDbHelper = new DbHelper(MonaPizza.getAppContext());
        mDbHelper.open();
        numberOfLesson = 0;
        int cat = mDbHelper.getNumberOfCategory();
        for (int i = 0; i<cat; i++)
            numberOfLesson += mDbHelper.getNumberOfLesson(i+1);
    }

    public static DbHelper getDbHelper() {
        if (instance == null) {
            instance = new Ultility();
        }
        return  mDbHelper;
    }

    public static int getNumberOfLesson() {
        if (instance == null) {
            instance = new Ultility();
        }
        return numberOfLesson;
    }
}
