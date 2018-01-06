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
import com.monapizza.monapizza.core.Friend;
import com.monapizza.monapizza.core.Item;
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
    private static String DB_NAME = "databasev4_1.db";
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
        OutputStream myOutput = new FileOutputStream("data/data/com.monapizza.monapizza/databasev4_1.db");
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
        //db.close();
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
        //db.close();
        return words;
    }

    // Lay danh sach cac tu trong mot bai hoc (khoi tao question)
    public ArrayList<Word> getWordInLesson(int category, int lesson) {
        ArrayList<Word> words = new ArrayList<Word>();
        SQLiteDatabase db = getDatabase();
        String query = "select w.WordID, w.CategoryID, w.Lesson, w.Eng, w.Vi, w.ImageLocation, w.SoundLocation" +
                " from Word w where w.Lesson = " + String.valueOf(lesson) + " and w.CategoryID = " + String.valueOf(category);

        Cursor c = db.rawQuery(query, null);
        c.moveToFirst();

        //Log.d("check0", "0 cat: " + category + " les " + lesson);
        while (!c.isAfterLast()) {
            //Log.d("check1", "1");
            Word word = new Word(c.getInt(c.getColumnIndex("WordID")), c.getString(c.getColumnIndex("Eng")),
                    c.getString(c.getColumnIndex("Vi")), c.getString(c.getColumnIndex("ImageLocation")),
                    c.getString(c.getColumnIndex("SoundLocation")), c.getInt(c.getColumnIndex("CategoryID")),
                    c.getInt(c.getColumnIndex("Lesson")));
            words.add(word);
            c.moveToNext();
        }
        c.close();
        //db.close();
        return words;
    }

    // Lay danh sach cac tu trong lesson cua mot category (dung de hien thi tren man hinh)
    public ArrayList<String> getWordEngWordsInLesson(int category, int lesson) {
        ArrayList<String> engWords = new ArrayList<String>();
        SQLiteDatabase db = getDatabase();
        String query = "select w.Eng as engWords" +
                " from Word w join Category c on w.CategoryID = c.CategoryID" +
                " where c.CategoryID = " + String.valueOf(category) + " and " + "w.Lesson = " + String.valueOf(lesson);

        Cursor c = db.rawQuery(query, null);
        c.moveToFirst();

        while (!c.isAfterLast()) {
            String engWord = c.getString(c.getColumnIndex("engWords"));
            engWords.add(engWord);
            c.moveToNext();
        }
        c.close();
        //db.close();
        return engWords;
    }

    // Lay danh sach cac loai category (hien thi)
    // Xem dinh nghia trong file Category.java de biet cac thuoc tinh can luu tru
    public ArrayList<Category> getCategoryList() {
        ArrayList<Category> cats = new ArrayList<Category>();
        SQLiteDatabase db = getDatabase();
        String query = "select c.CategoryID, c.CategoryName, c.Level, c.IconLocation" +
                " from Category c order by c.Level ASC";
        Cursor c = db.rawQuery(query, null);
        c.moveToFirst();

        while (!c.isAfterLast()) {
            Category cat = new Category(
                    c.getInt(c.getColumnIndex("CategoryID")),
                    c.getString(c.getColumnIndex("CategoryName")),
                    c.getInt(c.getColumnIndex("Level")),
                    c.getString(c.getColumnIndex("IconLocation")));
            cats.add(cat);
            c.moveToNext();
        }
        c.close();
        //db.close();
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
                " from Category c Order by c.Level DESC";
        Cursor c = db.rawQuery(query, null);
        c.moveToFirst();
        int maxLevel = c.getInt(c.getColumnIndex("Level"));
        c.close();
        //db.close();
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
        //db.close();
        return catNum;
    }

    // Lay so lesson trong mot category
    public int getNumberOfLesson(int category) {
        SQLiteDatabase db = getDatabase();
        String query = "select COUNT(distinct w.Lesson) as lessonNum" +
                " from Word w join Category c on w.CategoryID = c.CategoryID where c.CategoryID = " + String.valueOf(category);
        Cursor c = db.rawQuery(query, null);
        c.moveToFirst();
        int lessonNum = c.getInt(c.getColumnIndex("lessonNum"));
        c.close();
        //db.close();
        return lessonNum;
    }


    /*
        Các hàm hỗ trợ lớp User.
     */

    // Kiểm tra password có thỏa điều kiện độ dài >= 6 không
    private Boolean isValidPassword(String pass) {
        int cntNum = 0;
        int cntChr = 0;

        for(char ch: pass.toCharArray()) {
            if ((ch >= 'A' && ch <= 'Z') || (ch >= 'a' && ch <= 'z')) cntChr = cntChr + 1;
            if (ch >= '0' && ch <= '9') cntNum = cntNum + 1;
        }

        if (cntNum == 0 || cntChr == 0) return false;

        if (pass.length() >= 6) return true;
        else return false;
    }

    //Kiểm tra username có thỏa điều kiện
    private Boolean isValidUsername(String username) {
        if (username.length() == 0)
            return false;
        int nValid = 0;
        for(char ch: username.toCharArray())
            if ((ch >= 'A' && ch <= 'Z') || (ch >= 'a' && ch <= 'z') || (ch >= '0' && ch <= '9')) nValid = nValid + 1;
        if (nValid == username.length()) return true;
        else return false;
    }

    // Chuyen tu checkList thanh String de luu tru trong database.
    private String convertCheckList2String(ArrayList<ArrayList<Boolean>> checkList) {
        String res = "";
        //Log.d("myTag", "checkList's length " + checkList.size());

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
            int numLess = getNumberOfLesson(i+1);
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

    // ham chuyen doi ItemList thanh string
    private String convertItemList2String(ArrayList<Integer> item) {
        String res = "";
        res = item.get(0).toString();
        for(int i = 1; i < item.size(); ++i)
            res = res + ',' + item.get(i).toString();
        return res;
    }

    // ham chuyen doi string thanh ItemList
    private ArrayList<Integer> convertString2ItemList(String s) {
        ArrayList<Integer> res = new ArrayList<Integer>();

        int num = 0;
        for(int i = 0; i < s.length(); ++i) {
            if (s.charAt(i) == ',') {
                res.add(num);
                num = 0;
                continue;
            }
            num = num * 10 + s.charAt(i) - '0';
        }
        res.add(num);

        return res;
    }

    private String getEmptyItemList() {
        String res = "";
        int numItem = getNumberItem();
        res = "0";
        for(int i = 1; i < numItem; ++i)
            res = res + "," + "0";
        return res;
    }

    // Hàm khởi tạo checkList rỗng, dùng cho user mới khởi tạo
    private String getEmptyCheckList() {
        String result = "";
        int numCat = getNumberOfCategory();
        for(int i = 0; i < numCat; ++i) {
            int numLess = getNumberOfLesson(i+1);
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
        //db.close();
        return exist;
    }

    // Lay mat khau nguoi dung (private)
    private String getPassword(String userName) {
        // lay password cua userName tu database
        SQLiteDatabase db = getDatabase();
        String query = "select u.Username, u.Password" +
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
        //db.close();
        return password;
    }

    // Ham private: them mot userName moi vao db
    private void addNewUser(String userName, String password) {
        int level = 0;
        int money = 100;
        String checkLists = getEmptyCheckList();
        String itemLists = getEmptyItemList();
        // them user vao database

        ContentValues values = new ContentValues();
        values.put("Username", userName);
        values.put("Password", password);
        values.put("CheckList", checkLists);
        values.put("Level", level);
        values.put("UserMoney", money);
        SQLiteDatabase db = getDatabase();
        db.insert("UserAccount", null, values);
        //db.close();
    }

    // Ham kiem tra password nhap vao co trung voi password trong database hay khong
    public Boolean checkPassword(String username, String password) {
        return (password.equals(getPassword(username)));
    }

    // Thêm một user mới vào database với
    // userName: là tên user (khóa chính).
    // password: mật mã.
    // Tra ve 1: thanh cong
    // Tra ve so am: That bai, loi tuong ung trong file ErrorList.java
    public int addUser(String userName, String password) {
        if (!isValidUsername(userName)) {
            ErrorList.setExitCode(ErrorList.USERNAME_ERROR_FORMAT);
            return ErrorList.USERNAME_ERROR_FORMAT;
        }

        if (!isValidPassword(password)) {
            ErrorList.setExitCode(ErrorList.PASSWORD_ERROR_FORMAT);
            return ErrorList.PASSWORD_ERROR_FORMAT;
        }

        if (isExistingUser(userName)) {
            ErrorList.setExitCode(ErrorList.USERNAME_EXISTED);
            return ErrorList.USERNAME_EXISTED;
        }

        addNewUser(userName, password);

        return 1;
    }

    // Cập nhật password mới cho user
    // userName: là tên user (khóa chính).
    // password: mật mã.
    // Tra ve 1: thanh cong
    // Tra ve so am: That bai, loi tuong ung trong file ErrorList.java
    public int updateUserPass(String userName, String newPassword) {
        if (!isValidPassword(newPassword)) {
            ErrorList.setExitCode(ErrorList.PASSWORD_ERROR_FORMAT);
            return ErrorList.PASSWORD_ERROR_FORMAT;
        }

        if (newPassword.equals(getPassword(userName))) {
            ErrorList.setExitCode(ErrorList.SAME_PASSWORD);
            return ErrorList.SAME_PASSWORD;
        }

        // cap nhat password vao database
        SQLiteDatabase db = getDatabase();
        String query = "select u.Username, u.UserID" +
                " from UserAccount u";
        Cursor c = db.rawQuery(query, null);
        c.moveToFirst();
        String password = "";
        while (!c.isAfterLast()) {
            if (userName.equals(c.getString(c.getColumnIndex("Username")))) {
                ContentValues v = new ContentValues();
                v.put("Password", password);
                int id = c.getInt(c.getColumnIndex("UserID"));
                db.update("UserAccount", v, "UserID=" + id, null);
                break;
            }
            c.moveToNext();
        }
        c.close();
        //db.close();
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
        String query = "select u.Username, u.UserID" +
                " from UserAccount u";
        Cursor c = db.rawQuery(query, null);
        c.moveToFirst();
        String password = "";
        while (!c.isAfterLast()) {
            if (username.equals(c.getString(c.getColumnIndex("Username")))) {
                ContentValues v = new ContentValues();
                v.put("CheckList", s);
                int id = c.getInt(c.getColumnIndex("UserID"));
                db.update("UserAccount", v, "UserID=" + id, null);
                break;
            }
            c.moveToNext();
        }
        c.close();
        //db.close();
        return 1;
    }

    // Cap nhat tien cua user
    // userName: là tên user (khóa chính).
    // Tra ve 1: thanh cong
    // Tra ve so am: That bai, loi tuong ung trong file ErrorList.java
    public int updateMoney(String userName, int newMoney) {
        // cap nhat money trong database
        SQLiteDatabase db = getDatabase();
        String query = "select u.Username, u.UserID" +
                " from UserAccount u";
        Cursor c = db.rawQuery(query, null);
        c.moveToFirst();
        String password = "";
        while (!c.isAfterLast()) {
            if (userName.equals(c.getString(c.getColumnIndex("Username")))) {
                ContentValues v = new ContentValues();
                v.put("UserMoney", newMoney);
                int id = c.getInt(c.getColumnIndex("UserID"));
                db.update("UserAccount", v, "UserID=" + id, null);
                break;
            }
            c.moveToNext();
        }
        c.close();
        //db.close();
        return 1;
    }

    // Cap nhat level cua user
    // userName: là tên user (khóa chính).
    // Tra ve 1: thanh cong
    // Tra ve so am: That bai, loi tuong ung trong file ErrorList.java
    public int updateLevel(String userName, int level) {
        // cap nhat level trong database
        SQLiteDatabase db = getDatabase();
        String query = "select u.Username, u.UserID" +
                " from UserAccount u";
        Cursor c = db.rawQuery(query, null);
        c.moveToFirst();
        String password = "";
        while (!c.isAfterLast()) {
            if (userName.equals(c.getString(c.getColumnIndex("Username")))) {
                ContentValues v = new ContentValues();
                v.put("Level", level);
                int id = c.getInt(c.getColumnIndex("UserID"));
                db.update("UserAccount", v, "UserID=" + id, null);
                break;
            }
            c.moveToNext();
        }
        c.close();
        //db.close();
        return 1;
    }

    // Cap nhat tat ca thong tin cua user
    // userName: là tên user (khóa chính).
    // Tra ve 1: thanh cong
    // Tra ve so am: That bai, loi tuong ung trong file ErrorList.java
    public int updateAllInfo(String userName, int level, ArrayList<ArrayList<Boolean>> checkList, int money, ArrayList<Integer> itemList) {
        updateLearningProcess(userName, checkList);
        updateLevel(userName, level);
        updateMoney(userName, money);
        //updateItemList(userName, itemList);
        return 1;
    }

    // Lay level nguoi dung trong database
    public int loadLevel(String username) {
        SQLiteDatabase db = getDatabase();
        String query = "select u.Username, u.Level" +
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
        //db.close();
        return level;
    }

    // Lay thong tin checklist cua user trong database
    public ArrayList<ArrayList<Boolean>> loadCheckList(String username) {
        SQLiteDatabase db = getDatabase();
        String query = "select u.Username, u.CheckList" +
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
        //db.close();

        //convert tu string checkList -> ArrayList<ArrayList<Boolean>
        return convertString2CheckList(checkList);
    }

    // Lay thong tin tien cua user trong database
    public int loadMoney(String username) {
        SQLiteDatabase db = getDatabase();
        String query = "select u.Username, u.UserMoney" +
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
        //db.close();
        return money;
    }



    // Ham lay danh sach ban be cua username tuong ung
    public ArrayList<Friend> loadFriend(String username) {
        return getFriend(username);
    }


    // Cac ham moi them


    // Ham lay danh sach ban be cua username tuong ung
    // Cau truc của Frine xem ở class Friend.java
    // Lớp này lưu trữ 3 thông tin:
    // String mName;
    // String mAvatar; (Nếu ko có thuộc tính này thì có thể gán "default-avatar.png")
    // int mProgress;
    public ArrayList<Friend> getFriend(String username) {
        ArrayList<Friend> friends = new ArrayList<Friend>();
        SQLiteDatabase db = getDatabase();
        String query = "select f.Friendname" +
                " from Friend f where f.Username = '" + username + "'";
        Cursor c = db.rawQuery(query, null);
        c.moveToFirst();
        while (!c.isAfterLast()) {
            Friend friend = new Friend(c.getString(c.getColumnIndex("Friendname")), "default-avatar.png", 0);
            friends.add(friend);
            c.moveToNext();
        }
        c.close();
        return friends;
    }

    // Kiem tra xem 2 username co phai la ban be hay khong
    // Tra ve true: 2 username la ban be
    // Tra ve false: 2 username khong la ban be
    // 2 username nay chac chan nam trong database nen chi can kiem tra xem co phai ban be hay khong
    public Boolean checkFriend(String username1, String username2) {
        SQLiteDatabase db = getDatabase();
        String query = "select f.Friendname" +
                " from Friend f where f.Username = '" + username1 + "'";
        Cursor c = db.rawQuery(query, null);
        c.moveToFirst();
        while (!c.isAfterLast()) {
            if (username2 == c.getString(c.getColumnIndex("Friendname"))) {
                c.close();
                return true;
            }
            c.moveToNext();
        }
        c.close();

        query = "select f.Friendname" +
                " from Friend f where f.Username = '" + username2 + "'";
        c = db.rawQuery(query, null);
        c.moveToFirst();
        while (!c.isAfterLast()) {
            if (username1 == c.getString(c.getColumnIndex("Friendname"))) {
                c.close();
                return true;
            }
            c.moveToNext();
        }
        c.close();
        return false;
    }

    // Ham them ban be
    // Tra ve 1: thanh cong
    // Tra ve < 0: loi, tuong ung trong file ErrorList.java
    // Add friend nay la add friend mot chieu
    // Chi them friendUsername vao danh sach currentUserName.
    public int addFriend(String currentUserName, String friendUsername) {
        if (isExistingUser(friendUsername) == false)
            return ErrorList.FRIEND_NOT_EXIST;
        if (checkFriend(currentUserName, friendUsername))
            return ErrorList.ALREADY_FRIEND;

        // them ban be vao database
        SQLiteDatabase db = getDatabase();
        ContentValues values = new ContentValues();
        values.put("Username", currentUserName);
        values.put("Friendname", friendUsername);
        db.insert("Friend", null, values);

        return 1;
    }

    // Ham cap nhat Username vao bang SkipSignIn
    public void updateSkipSignIn(String username) {
        SQLiteDatabase db = getDatabase();
        ContentValues values = new ContentValues();
        values.put("Username", username);
        db.insert("SkipSignIn", null, values);
    }

    // Ham kiem tra xem co username da dang nhap truoc do hay chua
    // Thong tin luu trong bang SkipSignIn
    // Tra ve: String: ten username da dang nhap truoc do
    // Tra ve: null: neu chua tung co username dang nhap
    public String checkSkipSignIn() {
        SQLiteDatabase db = getDatabase();
        String countQuery = "select COUNT(*) as count from SkipSignIn";
        String query = "select Username" +
                " from SkipSignIn";
        Cursor c = db.rawQuery(countQuery, null);
        c.moveToFirst();
        int count = c.getInt(c.getColumnIndex("count"));
        c.close();
        if (count > 0) {
            c = db.rawQuery(query, null);
            c.moveToFirst();
            String username = c.getString(c.getColumnIndex("Username"));
            return username;
        }
        return null;
    }

    // Ham nay xoa userName trong bang SkipSignIn khi username logout ra khoi he thong
    public void deleteRecentSignIn() {
        SQLiteDatabase db = getDatabase();
        String query = "delete from SkipSignIn";
        db.execSQL(query);
    }

    // ham lay so luong item trong bang Item
    public int getNumberItem() {
        SQLiteDatabase db = getDatabase();
        String countQuery = "select COUNT(*) as count from Item";
        Cursor c = db.rawQuery(countQuery, null);
        c.moveToFirst();
        int count = c.getInt(c.getColumnIndex("count"));
        c.close();
        return count;
    }

    // ham tra ve danh sach Item trong database
    // cau truc Item xem class Item.java
    // Thuc hien tren bang Item
    public ArrayList<Item> getItemList() {
        ArrayList<Item> items = new ArrayList<Item>();
        SQLiteDatabase db = getDatabase();
        String query = "select i.ItemID, i.ItemName, i.ImageLocation, i.Effect, i.Cost" +
                " from Item i";
        Cursor c = db.rawQuery(query, null);
        c.moveToFirst();
        while (!c.isAfterLast()) {
            Item item = new Item(c.getInt(c.getColumnIndex("ItemID")), c.getString(c.getColumnIndex("ItemName")),
                    c.getInt(c.getColumnIndex("Cost")), c.getString(c.getColumnIndex("ImageLocation")),
                    c.getString(c.getColumnIndex("Effect")));
            items.add(item);
            c.moveToNext();
        }
        c.close();
        return items;
    }

    // Ham load thong tin itemList cua User
    // Luu thong tin ItemList trong database vao String s
    public ArrayList<Integer> loadItemList(String username) {
        ArrayList<Item> items = new ArrayList<Item>();
        SQLiteDatabase db = getDatabase();
        String query = "select ItemList" +
                " from UserAccount where Username = '" + username + "'";
        Cursor c = db.rawQuery(query, null);
        c.moveToFirst();
        String s = c.getString(c.getColumnIndex("ItemList"));
        c.close();
        return convertString2ItemList(s);
    }

    // Ham cap nhat danh sach ItemList cua User vao bang User
    // bien s da duoc bien doi dung format
    // chi can cap nhat vao database
    public void updateItemList(String userName, ArrayList<Integer> itemList) {
        String s = convertItemList2String(itemList);
        SQLiteDatabase db = getDatabase();
        String query = "select u.Username" +
                " from UserAccount u";
        Cursor c = db.rawQuery(query, null);
        c.moveToFirst();
        String password = "";
        while (!c.isAfterLast()) {
            if (userName.equals(c.getString(c.getColumnIndex("Username")))) {
                ContentValues v = new ContentValues();
                v.put("ItemList", s);
                int id = c.getPosition();
                db.update("UserAccount", v, "_id=" + id, null);
                break;
            }
            c.moveToNext();
        }
        c.close();
        // cap nhat s vao database
    }

    // Ham lay item co id la ind trong bang Item
    public Item getItem(int ind) {
        SQLiteDatabase db = getDatabase();
        String query = "select i.ItemID, i.ItemName, i.ImageLocation, i.Effect, i.Cost" +
                " from Item i where i.ItemID = '" + String.valueOf(ind) + "'";
        Cursor c = db.rawQuery(query, null);
        c.moveToFirst();
        Item item = new Item(
                c.getInt(c.getColumnIndex("ItemID")),
                c.getString(c.getColumnIndex("ItemName")),
                c.getInt(c.getColumnIndex("Cost")),
                c.getString(c.getColumnIndex("ImageLocation")),
                c.getString(c.getColumnIndex("Effect")));
        c.close();
        return item;
    }
}

