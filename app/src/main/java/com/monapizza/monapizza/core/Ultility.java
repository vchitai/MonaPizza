package com.monapizza.monapizza.core;

import com.monapizza.monapizza.MonaPizza;
import com.monapizza.monapizza.database.DbHelper;

/**
 * Created by chita on 06/12/2017.
 */

public class Ultility {
    private static Ultility instance = null;

    private static DbHelper mDbHelper;

    private Ultility() {
        mDbHelper = new DbHelper(MonaPizza.getAppContext());
        mDbHelper.open();
    }

    public static DbHelper getDbHelper() {
        if (instance == null) {
            instance = new Ultility();
        }
        return  mDbHelper;
    }
}
