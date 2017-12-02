package com.monapizza.monapizza.database;

import android.widget.ArrayAdapter;

import com.monapizza.monapizza.core.Category;
import com.monapizza.monapizza.core.Word;
import com.monapizza.monapizza.core.Lesson;
import com.monapizza.monapizza.core.ErrorList;

import java.util.ArrayList;

/**
 * Created by chita on 01/11/2017.
 */

public class DbHelper {
    /*
        Cac hàm hỗ trợ lớp câu hỏi
     */

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

    // Lay so luong danh sach category
    public int getNumberOfCategory() {
        return 0;
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
        return false;
    }

    // Lay mat khau nguoi dung (private)
    private String getPassword(String userName) {
        // lay password cua userName tu database

        return "";
    }

    // Ham private: them mot userName moi vao db
    private void addNewUser(String userName, String password) {
        int level = 0;
        int money = 0;
        String checkLists = getEmptyCheckList();

        // them user vao database
    }

    public Boolean checkPassword(String username, String password) {
        if (password == getPassword(username))
            return true;
        else
            return false;
    }

    // Thêm một user mới vào database với
    // userName: là tên user (khóa chính).
    // password: mật mã.
    // Tra ve 1: thanh cong
    // Tra ve so am: That bai, loi tuong ung trong file ErrorList.java
    public int addUser(String userName, String password) {
        if (isValidPassword(password) == false)
            return ErrorList.PASSWORD_ERROR_FORMAT;

        if (isExistingUser(userName) == true)
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
        if (isValidPassword(newPassword) == false)
            return ErrorList.PASSWORD_ERROR_FORMAT;

        if (newPassword == getPassword(userName))
            return ErrorList.SAME_PASSWORD;

        // cap nhat password vao database

        return 1;
    }

    // Cap nhat qua trinh hoc cua user
    // userName: là tên user (khóa chính).
    // Tra ve 1: thanh cong
    // Tra ve so am: That bai, loi tuong ung trong file ErrorList.java
    public int updateLearningProcess(String username, ArrayList<ArrayList<Boolean>> checkList) {
        String s = convertCheckList2String(checkList);


        //cap nhat checkList database

        return 1;
    }

    // Cap nhat tien cua user
    // userName: là tên user (khóa chính).
    // Tra ve 1: thanh cong
    // Tra ve so am: That bai, loi tuong ung trong file ErrorList.java
    public int updateMoney(String userName, int newMoney) {
        // cap nhat money trong database

        return 1;
    }

    // Cap nhat level cua user
    // userName: là tên user (khóa chính).
    // Tra ve 1: thanh cong
    // Tra ve so am: That bai, loi tuong ung trong file ErrorList.java
    public int updateLevel(String userName, int level) {
        // cap nhat level trong database

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

        return 0;
    }

    // Lay thong tin checklist cua user trong database
    public ArrayList<ArrayList<Boolean>> loadCheckList(String username) {

        return new ArrayList<ArrayList<Boolean>>();
    }

    // Lay thong tin tien cua user trong database
    public int loadMoney(String username) {
        return 0;
    }
}
