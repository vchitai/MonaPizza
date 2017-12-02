package com.monapizza.monapizza.core;

import com.monapizza.monapizza.database.DbHelper;
import com.monapizza.monapizza.core.ErrorList;

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
    private int m_money;

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
        return database.checkPassword(userName, password);
    }

    // Load thong tin cua user voi ten la userName tu database
    private void loadUserInfo(String userName, DbHelper database) {
        m_logined = true;

        m_name = userName;
        m_money = database.loadMoney(userName);
        m_checkList = database.loadCheckList(userName);
        m_level = database.loadLevel(userName);
    }

    // kiem tra dang nhap nguoi dung
    // Tra ve:
    //      < 0: loi, xem loi tuong ung trong file ErrorList.java
    //      1: login thanh cong, da load data tu database cua user vao cac truong luu tru phia tren.
    public int signIn(String userName, String password, DbHelper database) {
        if (database.isExistingUser(userName) == false)
            return ErrorList.USERNAME_NOT_EXISTED;

        if (checkUserPass(userName, password, database)) {
            loadUserInfo(userName, database);
            return 1;
        }
        else
            return ErrorList.WRONG_PASSWORD;
    }

    // Dang ky nguoi dung moi
    // Tra ve:
    //      < 0: loi, xem loi tuong ung trong file ErrorList.java
    //      1: login thanh cong, da load data tu database cua user vao cac truong luu tru phia tren.
    private int signUp(String userName, String password, DbHelper database) {
        int id = database.addUser(userName, password);
        if (id < 1) return id;
        signIn(userName, password, database);
        return 1;
    }

    // Dang xuat
    private void logOut(DbHelper database) {
        m_logined = false;

        database.updateAllInfo(m_name, m_level, m_checkList, m_money);

        m_level = 0;
        m_checkList.clear();
        m_name = "";
        m_money = 0;

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
    public void updateLearningProcess(int level, int category, int lesson, DbHelper database) {
        m_level = Math.max(m_level, level);
        if (level != -1) {
            for(int i = 0; i < level - 1; ++i)
                for(int j = 0; j < m_checkList.get(i).size(); ++j)
                    m_checkList.get(i).set(j, true);

            database.updateLearningProcess(m_name, m_checkList);

            return;
        }
        if (level == -1 && category != -1 && lesson == -1) {
            for(int i = 0; i < m_checkList.get(category).size(); ++i)
                m_checkList.get(category).set(i, true);

            database.updateLearningProcess(m_name, m_checkList);
            return;
        }
        if (level == -1 && category != -1 && lesson != -1) {
            m_checkList.get(category).set(lesson, true);

            database.updateLearningProcess(m_name, m_checkList);
            return;
        }
    }


    // Cac ham get user info
    public String getUserName() {
        return m_name;
    }

    public int getMoney() {
        return m_money;
    }

    public ArrayList<ArrayList<Boolean>> getCheckList() {
        return m_checkList;
    }

    public int getLevel() {
        return m_level;
    }


}
