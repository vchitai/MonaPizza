package com.monapizza.monapizza.database;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import com.monapizza.monapizza.core.Category;
import com.monapizza.monapizza.core.ErrorList;
import com.monapizza.monapizza.core.Lesson;
import com.monapizza.monapizza.core.Word;

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
    private String DB_PATH = "data/data/com.monapizza.monapizza/";
    private static String DB_NAME = "database.db";
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
        OutputStream myOutput = new FileOutputStream("data/data/com.monapizza.monapizza/database.db");
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

    public SQLiteDatabase getDatabase() {
        return myDatabase;
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
        ArrayList<Word> words = new ArrayList<Word>();
        SQLiteDatabase db = getDatabase();
        String query = "select w.WordID, w.CategoryID, w.Lesson, w.Eng, w.Vi, w.ImageLocation, w.SoundLocation" +
                " from Word w join Category c " +
                "on w.CategoryID = c.CategoryID where c.Level = " + String.valueOf(level);

        Cursor c = db.rawQuery(query, null);
        c.moveToFirst();

        while (!c.isAfterLast()) {
            Word word = new Word(c.getInt(c.getColumnIndex("WordID")), c.getString(c.getColumnIndex("Eng")),
                    c.getString(c.getColumnIndex("Vi")), c.getString(c.getColumnIndex("ImageLocation")),
                    c.getString(c.getColumnIndex("SoundLocation")), c.getInt(c.getColumnIndex("CategoryID")),
                    c.getInt(c.getColumnIndex("Lesson")));
            words.add(word);
            c.moveToNext();
        }
        c.close();
        db.close();
        return words;
    }

    // Lay danh sach cac tu trong mot category (bai kiem tra)
    public ArrayList<Word> getWordInCategory(int category) {
        ArrayList<Word> words = new ArrayList<Word>();
        SQLiteDatabase db = getDatabase();
        String query = "select w.WordID, w.CategoryID, w.Lesson, w.Eng, w.Vi, w.ImageLocation, w.SoundLocation" +
                " from Word w where w.CategoryID = " + String.valueOf(category);

        Cursor c = db.rawQuery(query, null);
        c.moveToFirst();

        while (!c.isAfterLast()) {
            Word word = new Word(c.getInt(c.getColumnIndex("WordID")), c.getString(c.getColumnIndex("Eng")),
                    c.getString(c.getColumnIndex("Vi")), c.getString(c.getColumnIndex("ImageLocation")),
                    c.getString(c.getColumnIndex("SoundLocation")), c.getInt(c.getColumnIndex("CategoryID")),
                    c.getInt(c.getColumnIndex("Lesson")));
            words.add(word);
            c.moveToNext();
        }
        c.close();
        db.close();
        return words;
    }

    // Lay danh sach cac tu trong mot bai hoc (khoi tao question)
    public ArrayList<Word> getWordInLesson(int category, int lesson) {
        ArrayList<Word> words = new ArrayList<Word>();
        SQLiteDatabase db = getDatabase();
        String query = "select w.WordID, w.CategoryID, w.Lesson, w.Eng, w.Vi, w.ImageLocation, w.SoundLocation" +
                " from Word w where w.Lesson = " + String.valueOf(lesson);

        Cursor c = db.rawQuery(query, null);
        c.moveToFirst();

        while (!c.isAfterLast()) {
            Word word = new Word(c.getInt(c.getColumnIndex("WordID")), c.getString(c.getColumnIndex("Eng")),
                    c.getString(c.getColumnIndex("Vi")), c.getString(c.getColumnIndex("ImageLocation")),
                    c.getString(c.getColumnIndex("SoundLocation")), c.getInt(c.getColumnIndex("CategoryID")),
                    c.getInt(c.getColumnIndex("Lesson")));
            words.add(word);
            c.moveToNext();
        }
        c.close();
        db.close();
        return words;
    }

    // Lay danh sach cac tu trong lesson cua mot category (dung de hien thi tren man hinh)
    public ArrayList<String> getWordEngWordsInLesson(int category, int level) {
        return new ArrayList<String>();
    }

    // Lay danh sach cac loai category (hien thi)
    // Xem dinh nghia trong file Category.java de biet cac thuoc tinh can luu tru
    public ArrayList<Category> getCategoryList() {
        ArrayList<Category> cats = new ArrayList<Category>();
        SQLiteDatabase db = getDatabase();
        String query = "select c.CategoryID, c.CategoryName, c.Level, c.IconLocation" +
                " from Category c";
        Cursor c = db.rawQuery(query, null);
        c.moveToFirst();

        while (!c.isAfterLast()) {
            Category cat = new Category(c.getString(c.getColumnIndex("CategoryName")), c.getInt(c.getColumnIndex("Level")),
                    c.getString(c.getColumnIndex("IconLocation")));
            cats.add(cat);
            c.moveToNext();
        }
        c.close();
        db.close();
        return cats;
    }

    // Lay danh sach cac lesson (hien thi)
    // Xem dinh nghia trong file Lesson.java de biet cac thuoc tinh can luu tru
    public ArrayList<Lesson> getLessonList(int category) {
        return new ArrayList<Lesson>();
    }

    // Lay so level trong database
    public int getMaxLevel() {
        SQLiteDatabase db = getDatabase();
        String query = "select c.Level" +
                " from Category c Order by DESC";
        Cursor c = db.rawQuery(query, null);
        c.moveToFirst();
        int maxLevel = c.getInt(c.getColumnIndex("Level"));
        c.close();
        db.close();
        return maxLevel;
    }

    // Lay so luong danh sach category
    public int getNumberOfCategory() {
        SQLiteDatabase db = getDatabase();
        String query = "select COUNT(c.Level) as catNum" +
                " from Category c";
        Cursor c = db.rawQuery(query, null);
        c.moveToFirst();
        int catNum = c.getInt(c.getColumnIndex("catNum"));
        c.close();
        db.close();
        return catNum;
    }

    // Lay so lesson trong mot category
    public int getNumberOfLesson(int category) {
        return 0;
    }


    /*
        Các hàm hỗ trợ lớp User.
     */

    // Kiểm tra password có thỏa điều kiện là các chữ cái latin A, B, C... hoặc là số 0, 1, 2, 3...
    private Boolean isValidPassword(String pass) {
        int nValid = 0;
        for(char ch: pass.toCharArray())
            if ((ch >= 'A' && ch <= 'Z') || (ch >= '0' && ch <= '9')) nValid = nValid + 1;
        if (nValid == pass.length()) return true;
        else return false;
    }

    // Chuyen tu checkList thanh String de luu tru trong database.
    private String convertCheckList2String(ArrayList<ArrayList<Boolean>> checkList) {
        String res = "";

        for(ArrayList<Boolean> l: checkList)
            for(Boolean b: l)
                if (b == true)
                    res += '1';
                else
                    res += '0';

        return res;
    }

    // Chuyen tu String sang CheckList de luu tru trong database.
    private ArrayList<ArrayList<Boolean>> convertString2CheckList(String s) {
        ArrayList<ArrayList<Boolean>> result = new ArrayList<ArrayList<Boolean>>();
        result.clear();

        int numCat = getNumberOfCategory();
        int j = 0;
        for(int i = 0; i < numCat; ++i) {
            int numLess = getNumberOfLesson(i);
            ArrayList<Boolean> curCat = new ArrayList<Boolean>();
            curCat.clear();
            while (numLess > 0) {
                if (s.charAt(j) == '0')
                    curCat.add(false);
                else
                    curCat.add(true);
                --numLess;
                ++j;
            }
            result.add(curCat);
        }

        return result;
    }

    // Hàm khởi tạo checkList rỗng, dùng cho user mới khởi tạo
    private String getEmptyCheckList() {
        String result = "";
        int numCat = getNumberOfCategory();
        for(int i = 0; i < numCat; ++i) {
            int numLess = getNumberOfLesson(i);
            while (numLess > 0) {
                result += '0';
                numLess--;
            }
        }
        return result;
    }

    // Kiem tra trong database co ton tai user.
    // Neu ton tai: tra ve true:
    // Ko ton tai: tra ve false
    public Boolean isExistingUser(String user) {
        // Kiem tra trong db co ton tai user.
        boolean exist = false;
        SQLiteDatabase db = getDatabase();
        String query = "select u.Username" +
                " from UserAccount u";
        Cursor c = db.rawQuery(query, null);
        c.moveToFirst();

        while (!c.isAfterLast()) {
            if (user.equals(c.getString(c.getColumnIndex("Username")))) {
                exist = true;
                break;
            }
            c.moveToNext();
        }
        c.close();
        db.close();
        return false;
    }

    // Lay mat khau nguoi dung (private)
    private String getPassword(String userName) {
        // lay password cua userName tu database
        SQLiteDatabase db = getDatabase();
        String query = "select u.Username" +
                " from UserAccount u";
        Cursor c = db.rawQuery(query, null);
        c.moveToFirst();
        String password = "";
        while (!c.isAfterLast()) {
            if (userName.equals(c.getString(c.getColumnIndex("Username")))) {
                password = c.getString(c.getColumnIndex("Password"));
                break;
            }
            c.moveToNext();
        }
        c.close();
        db.close();
        return password;
    }

    // Ham private: them mot userName moi vao db
    private void addNewUser(String userName, String password) {
        int level = 0;
        int money = 0;
        String checkLists = getEmptyCheckList();
        // them user vao database

        ContentValues values = new ContentValues();
        values.put("Username", userName);
        values.put("Password", password);
        SQLiteDatabase db = getDatabase();
        db.insert("UserAccount", null, values);
        db.close();
    }

    public Boolean checkPassword(String username, String password) {
        return (password.equals(getPassword(username)));
    }

    // Thêm một user mới vào database với
    // userName: là tên user (khóa chính).
    // password: mật mã.
    // Tra ve 1: thanh cong
    // Tra ve so am: That bai, loi tuong ung trong file ErrorList.java
    public int addUser(String userName, String password) {
        if (!isValidPassword(password))
            return ErrorList.PASSWORD_ERROR_FORMAT;

        if (!isExistingUser(userName))
            return ErrorList.USERNAME_EXISTED;

        addNewUser(userName, password);

        return 1;
    }

    // Cập nhật password mới cho user
    // userName: là tên user (khóa chính).
    // password: mật mã.
    // Tra ve 1: thanh cong
    // Tra ve so am: That bai, loi tuong ung trong file ErrorList.java
    public int updateUserPass(String userName, String newPassword) {
        if (!isValidPassword(newPassword))
            return ErrorList.PASSWORD_ERROR_FORMAT;

        if (newPassword.equals(getPassword(userName)))
            return ErrorList.SAME_PASSWORD;

        // cap nhat password vao database
        SQLiteDatabase db = getDatabase();
        String query = "select u.Username" +
                " from UserAccount u";
        Cursor c = db.rawQuery(query, null);
        c.moveToFirst();
        String password = "";
        while (!c.isAfterLast()) {
            if (userName.equals(c.getString(c.getColumnIndex("Username")))) {
                ContentValues v = new ContentValues();
                v.put("Password", password);
                int id = c.getPosition();
                db.update("UserAccount", v, "_id=" + id, null);
                break;
            }
            c.moveToNext();
        }
        c.close();
        db.close();
        return 1;
    }

    // Cap nhat qua trinh hoc cua user
    // userName: là tên user (khóa chính).
    // Tra ve 1: thanh cong
    // Tra ve so am: That bai, loi tuong ung trong file ErrorList.java
    public int updateLearningProcess(String username, ArrayList<ArrayList<Boolean>> checkList) {
        String s = convertCheckList2String(checkList);
        //cap nhat checkList database
        SQLiteDatabase db = getDatabase();
        String query = "select u.Username" +
                " from UserAccount u";
        Cursor c = db.rawQuery(query, null);
        c.moveToFirst();
        String password = "";
        while (!c.isAfterLast()) {
            if (username.equals(c.getString(c.getColumnIndex("Username")))) {
                ContentValues v = new ContentValues();
                v.put("CheckList", s);
                int id = c.getPosition();
                db.update("UserAccount", v, "_id=" + id, null);
                break;
            }
            c.moveToNext();
        }
        c.close();
        db.close();
        return 1;
    }

    // Cap nhat tien cua user
    // userName: là tên user (khóa chính).
    // Tra ve 1: thanh cong
    // Tra ve so am: That bai, loi tuong ung trong file ErrorList.java
    public int updateMoney(String userName, int newMoney) {
        // cap nhat money trong database
        SQLiteDatabase db = getDatabase();
        String query = "select u.Username" +
                " from UserAccount u";
        Cursor c = db.rawQuery(query, null);
        c.moveToFirst();
        String password = "";
        while (!c.isAfterLast()) {
            if (userName.equals(c.getString(c.getColumnIndex("Username")))) {
                ContentValues v = new ContentValues();
                v.put("Money", newMoney);
                int id = c.getPosition();
                db.update("UserAccount", v, "_id=" + id, null);
                break;
            }
            c.moveToNext();
        }
        c.close();
        db.close();
        return 1;
    }

    // Cap nhat level cua user
    // userName: là tên user (khóa chính).
    // Tra ve 1: thanh cong
    // Tra ve so am: That bai, loi tuong ung trong file ErrorList.java
    public int updateLevel(String userName, int level) {
        // cap nhat level trong database
        SQLiteDatabase db = getDatabase();
        String query = "select u.Username" +
                " from UserAccount u";
        Cursor c = db.rawQuery(query, null);
        c.moveToFirst();
        String password = "";
        while (!c.isAfterLast()) {
            if (userName.equals(c.getString(c.getColumnIndex("Username")))) {
                ContentValues v = new ContentValues();
                v.put("Level", level);
                int id = c.getPosition();
                db.update("UserAccount", v, "_id=" + id, null);
                break;
            }
            c.moveToNext();
        }
        c.close();
        db.close();
        return 1;
    }

    // Cap nhat tat ca thong tin cua user
    // userName: là tên user (khóa chính).
    // Tra ve 1: thanh cong
    // Tra ve so am: That bai, loi tuong ung trong file ErrorList.java
    public int updateAllInfo(String userName, int level, ArrayList<ArrayList<Boolean>> checkList, int money) {
        updateLearningProcess(userName, checkList);
        updateLevel(userName, level);
        updateMoney(userName, money);
        return 1;
    }

    // Lay level nguoi dung trong database
    public int loadLevel(String username) {
        SQLiteDatabase db = getDatabase();
        String query = "select u.Username" +
                " from UserAccount u";
        Cursor c = db.rawQuery(query, null);
        c.moveToFirst();
        int level = -1;
        while (!c.isAfterLast()) {
            if (username.equals(c.getString(c.getColumnIndex("Username")))) {
                level = c.getInt(c.getColumnIndex("Level"));
                break;
            }
            c.moveToNext();
        }
        c.close();
        db.close();
        return level;
    }

    // Lay thong tin checklist cua user trong database
    public ArrayList<ArrayList<Boolean>> loadCheckList(String username) {
        SQLiteDatabase db = getDatabase();
        String query = "select u.Username" +
                " from UserAccount u";
        Cursor c = db.rawQuery(query, null);
        c.moveToFirst();
        String checkList = "";
        while (!c.isAfterLast()) {
            if (username.equals(c.getString(c.getColumnIndex("Username")))) {
                checkList = c.getString(c.getColumnIndex("CheckList"));
                break;
            }
            c.moveToNext();
        }
        c.close();
        db.close();

        //convert tu string checkList -> ArrayList<ArrayList<Boolean>

        return new ArrayList<ArrayList<Boolean>>();
    }

    // Lay thong tin tien cua user trong database
    public int loadMoney(String username) {
        SQLiteDatabase db = getDatabase();
        String query = "select u.Username" +
                " from UserAccount u";
        Cursor c = db.rawQuery(query, null);
        c.moveToFirst();
        int money = -1;
        while (!c.isAfterLast()) {
            if (username.equals(c.getString(c.getColumnIndex("Username")))) {
                money = (int)c.getFloat(c.getColumnIndex("UserMoney"));
                break;
            }
            c.moveToNext();
        }
        c.close();
        db.close();
        return money;
    }
}
