package com.monapizza.monapizza.database;

import android.content.Context;
import android.content.res.AssetManager;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import com.monapizza.monapizza.core.Category;
import com.monapizza.monapizza.core.Word;
import com.monapizza.monapizza.core.Lesson;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

/**
 * Created by chita on 01/11/2017.
 */

public class DbHelper extends SQLiteOpenHelper {

    private Context context;
    private String DB_PATH = "data/data/com.monapizza.monapizza";
    private static String DB_NAME = "database.sqlite";
    private SQLiteDatabase myDatabase;

    public DbHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    private boolean checkDatabase() {
        boolean checkDb = false;
        try {
            String myPath = DB_PATH + DB_NAME;
            File dbFile = new File(myPath);
            checkDb = dbFile.exists();
        }
        catch (SQLiteException e) {
            System.out.print("Database doesn't exist");
        }
        return checkDb;
    }

    private void copyDatabase() throws IOException {
        AssetManager  dirPath = context.getAssets();
        InputStream myInput = context.getAssets().open(DB_NAME);
        String outFileName = DB_PATH + DB_NAME;
        OutputStream myOutput = new FileOutputStream("data/data/com.monapizza.monapizza/database.sqlite");
        byte[] buffer = new byte[1024];
        int len;
        while ((len = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, len);
        }
        myOutput.flush();
        myOutput.close();
        myInput.close();
    }

    public void createDatabase() {
        this.getReadableDatabase();
        try {
            copyDatabase();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }


    public DbHelper(Context context) {
        super(context, DB_NAME, null, 1);
        this.context = context;
        boolean dbExist = checkDatabase();
        if(!dbExist)
        {
            System.out.println("Database doesn't exist!");
            createDatabase();
        }
    }

    public void open() {
        String myPath = DB_PATH + DB_NAME;
        myDatabase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
    }

    public synchronized void close() {
        myDatabase.close();
        super.close();
    }

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
