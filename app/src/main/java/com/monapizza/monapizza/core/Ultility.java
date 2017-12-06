package com.monapizza.monapizza.core;

import android.content.Context;

import com.monapizza.monapizza.database.DbHelper;

/**
 * Created by chita on 06/12/2017.
 */

public class Ultility {
    private static Ultility instance = null;

    private static DbHelper mDbHelper;

    private Ultility(Context context) {
        mDbHelper = new DbHelper(context);
        mDbHelper.open();
    }

    public  static  void init(Context context) {
        if (instance == null) {
            instance = new Ultility(context);
        }
    }

    public static Ultility getInstance(Context context) {
        if (instance == null) {
            instance = new Ultility(context);
        }
        return instance;
    }

    public static DbHelper getDbHelper() {
        return  mDbHelper;
    }
}
