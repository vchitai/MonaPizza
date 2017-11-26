package com.monapizza.monapizza.core;

import com.monapizza.monapizza.database.DbHelper;

import java.util.ArrayList;

/**
 * Created by chita on 01/11/2017.
 */

// Lop quan ly User dang ton tai trong database

// Thiet ke theo Singleton

public class User {
    private static User instance = null;

    // Bien kiem tra da co nguoi dung login chua
    private Boolean m_logined;

    // Username
    private String m_name;

    // Cap do
    private int m_level;

    // Ket qua qua trinh hoc tap
    private ArrayList<ArrayList<Boolean>> m_checkList;

    // Tien
    private int money;

    // Danh sach ban be
    //private ArrayList<> friends;

    private User() {}

    // Khoi tao User voi username tuong ung trong database
    // Chi goi ham nay de tao ra mot User, ham static
    public static User getInstance() {
        if (instance == null) {
            instance = new User();
        }
        return instance;
    }

    // Kiem tra tinh dung dan cua Username va Password trong database
    private Boolean checkUserPass(String userName, String password, DbHelper database) {
        return false;
    }

    // Load thong tin cua user voi ten la userName tu database
    private void loadUserInfo(String userName, DbHelper database) {
        m_logined = true;
        return;
    }

    // kiem tra dang nhap nguoi dung
    // Tra ve:
    //      false: login that bai, (sai tai khoan, mat khau).
    //      true: login thanh cong, da load data tu database cua user vao cac truong luu tru phia tren.
    public boolean signIn(String userName, String password, DbHelper database) {
        if (checkUserPass(userName, password, database)) {
            loadUserInfo(userName, database);
            return true;
        }
        return false;
    }

    // Dang ky nguoi dung moi
    private void signUp(String userName, String password, DbHelper database) {
        // khoi tao user moi
        // cap nhat user vao database.
    }

    // Dang xuat
    private void logOut() {
        // Ham dang xuat

        // Luu tru thong tin user xuong database.
        //
        m_logined = false;
    }

    // Mua vat pham
    // Tra ve
    //      false: mua that bai
    //      true: mua thanh cong, tu dong cap nhat database.
    public Boolean buyItem(int itemId, DbHelper database) {
        if (m_logined == false) {
            // chua dang nhap
            return false;
        }
        // Kiem tra viec mua item
        // Mua thanh cong, cap nhat database
        return true;
    }

    // Them ban be
    // Tra ve
    //      false: them thanh cong
    //      true: them thanh cong, tu dong cap nhat database.
    public Boolean addFriend(String friendName, DbHelper database) {
        return false;
    }

    // Cap nhat tien trinh hoc cua user
    // Cai nay con xung doi cho luu level la gi
    public void updateLearningProcess(int level, int category, int lesson) {
        m_level = Math.max(m_level, level);
        if (level != -1) {
            for(int i = 0; i < level - 1; ++i)
                for(int j = 0; j < m_checkList.get(i).size(); ++j)
                    m_checkList.get(i).set(j, true);

            // goi ham cap nhat database cho user
            return;
        }
        if (level == -1 && category != -1 && lesson == -1) {
            for(int i = 0; i < m_checkList.get(category).size(); ++i)
                m_checkList.get(category).set(i, true);

            // goi ham cap nhat database cho user
            return;
        }
        if (level == -1 && category != -1 && lesson != -1) {
            m_checkList.get(category).set(lesson, true);

            // goi ham cap nhat database cho user
            return;
        }
    }


    // Cac ham get user info
    public String getUserName() {
        return m_name;
    }

    public int getMoney() {
        return money;
    }

    public ArrayList<ArrayList<Boolean>> getCheckList() {
        return m_checkList;
    }

    public int getLevel() {
        return m_level;
    }


}
