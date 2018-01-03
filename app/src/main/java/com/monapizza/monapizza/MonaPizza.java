package com.monapizza.monapizza;

import android.app.Application;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;

import java.io.IOException;
import java.io.InputStream;

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

    public static Bitmap getBitmapFromAsset(Context context, String filePath) {
        AssetManager assetManager = context.getAssets();

        InputStream istr;
        Bitmap      bitmap = null;
        try {
            istr = assetManager.open(filePath);
            bitmap = BitmapFactory.decodeStream(istr);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return bitmap;
    }

    public static RoundedBitmapDrawable getRoundedBitmapDrawable(String filePath) {
        Bitmap bitmap = getBitmapFromAsset(context,filePath);
        RoundedBitmapDrawable res = RoundedBitmapDrawableFactory.create(context.getResources(),bitmap);
        if (bitmap!=null)
            res.setCornerRadius(Math.max(bitmap.getWidth(), bitmap.getHeight()) / 2.0f);

        return res;
    }
}
