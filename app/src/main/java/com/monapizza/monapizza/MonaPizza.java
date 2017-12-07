package com.monapizza.monapizza;

import android.app.Application;
import android.content.Context;

/**
 * Created by chita on 07/12/2017.
 */

public class MonaPizza extends Application {
    private static Context context;

    public void onCreate() {
        super.onCreate();
        MonaPizza.context = getApplicationContext();
    }

    public static Context getAppContext() {
        return MonaPizza.context;
    }
}
