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
    private int m_money;

    // Danh sach vat pham so huu
    private ArrayList<Integer> m_itemList;

    // Danh sach ban be
    private ArrayList<Friend> m_friends;

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
    private Boolean checkUserPass(String userName, String password) {
        return Ultility.getDbHelper().checkPassword(userName, password);
    }

    // Load thong tin cua user voi ten la userName tu database
    private void loadUserInfo(String userName) {
        DbHelper database = Ultility.getDbHelper();
        m_logined = true;

        m_name = userName;
        m_money = database.loadMoney(userName);
        m_checkList = database.loadCheckList(userName);
        m_level = database.loadLevel(userName);
        m_friends = database.loadFriend(userName);
        m_itemList = database.loadItemList(userName);

        database.updateSkipSignIn(userName);
    }

    // Cho level, dem xem co bao nhieu cat duoc hoan thanh
    private int getNumCatFromLevel(int level) {
        if (level == 1) return 3;
        if (level == 2) return 5;
        if (level == 3) return 8;
        return 12;
    }

    // Cap nhat level tu checkList
    private int getNewLevelFromCheckList(ArrayList<ArrayList<Boolean>> checkList) {
        int numCat = 0;
        for(numCat = 0; numCat < checkList.size(); ++numCat) {
            ArrayList<Boolean> temp = checkList.get(numCat);
            int cnt = 0;
            for(int i = 0; i < temp.size(); ++i)
                if (temp.get(i) == true) cnt = cnt + 1;
            if (cnt != temp.size()) break;
        }

        if (numCat >= 11) return 4;
        if (numCat >= 7) return 3;
        if (numCat >= 4) return 2;
        if (numCat >= 2) return 1;
        return 0;
    }


    // Ham cho phep bo qua dang nhap vi da dang nhap truoc do
    // Tra ve 1: Neu da dang nhap
    // Tra ve -1: Neu truoc do chua tung dang nhap
    public int skipSignIn() {
        DbHelper database = Ultility.getDbHelper();

        String usName = database.checkSkipSignIn();
        if (usName != null) {
            loadUserInfo(usName);
            return 1;
        }
        return -1;
    }

    // kiem tra dang nhap nguoi dung
    // Tra ve:
    //      < 0: loi, xem loi tuong ung trong file ErrorList.java
    //      1: login thanh cong, da load data tu database cua user vao cac truong luu tru phia tren.
    public int signIn(String userName, String password) {
        DbHelper database = Ultility.getDbHelper();
        if (database.isExistingUser(userName) == false)
            return ErrorList.USERNAME_NOT_EXISTED;

        if (checkUserPass(userName, password)) {
            loadUserInfo(userName);
            return 1;
        }
        else
            return ErrorList.WRONG_PASSWORD;
    }

    // Dang ky nguoi dung moi
    // Tra ve:
    //      < 0: loi, xem loi tuong ung trong file ErrorList.java
    //      1: login thanh cong, da load data tu database cua user vao cac truong luu tru phia tren.
    public int signUp(String userName, String password) {
        DbHelper database = Ultility.getDbHelper();
        int id = database.addUser(userName, password);
        if (id < 1) return id;
        signIn(userName, password);
        return 1;
    }

    // Dang xuat
    public void logOut() {
        DbHelper database = Ultility.getDbHelper();
        m_logined = false;

        database.updateAllInfo(m_name, m_level, m_checkList, m_money, m_itemList);
        database.deleteRecentSignIn();

        m_level = 0;
        m_checkList.clear();
        m_name = "";
        m_money = 0;
        m_friends.clear();
        m_itemList.clear();
    }

    // Cap nhat tien trinh hoc cua user
    // Cai nay con xung doi cho luu level la gi
    public void updateLearningProcess(int level, int category, int lesson) {
        DbHelper database = Ultility.getDbHelper();
        m_level = Math.max(m_level, level);
        if (level != -1) {
            int new_cat = getNumCatFromLevel(level);
            for(int i = 0; i < new_cat; ++i)
                for(int j = 0; j < m_checkList.get(i).size(); ++j)
                    m_checkList.get(i).set(j, true);

            m_level = level;
            database.updateLearningProcess(m_name, m_checkList);
            database.updateLevel(m_name, m_level);
            return;
        }
        if (level == -1 && category != -1 && lesson == -1) {
            category = category - 1;

            for(int i = 0; i < m_checkList.get(category).size(); ++i)
                m_checkList.get(category).set(i, true);

            m_level = getNewLevelFromCheckList(m_checkList);

            database.updateLearningProcess(m_name, m_checkList);
            database.updateLevel(m_name, m_level);
            return;
        }
        if (level == -1 && category != -1 && lesson != -1) {
            category = category - 1;
            lesson = lesson - 1;

            m_checkList.get(category).set(lesson, true);

            m_level = getNewLevelFromCheckList(m_checkList);

            database.updateLearningProcess(m_name, m_checkList);
            database.updateLevel(m_name, m_level);
            return;
        }
    }

    //ham cap nhat password cho user
    // Tra ve 1: thanh cong
    // Tra ve < 0: loi tuong ung trong file ErrorList.java
    public int updatePass(String username, String new_password) {
        DbHelper database = Ultility.getDbHelper();
        return database.updateUserPass(username, new_password);
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

    public ArrayList<Integer> getItemList() {
        return m_itemList;
    }

    // Ham lay danh sach ban be
    // Ham tam thoi, database chua ton tai nen van load friend tam
    public ArrayList<Friend> getFriendList() {
        ArrayList<Friend> friends = new ArrayList<Friend>();
        friends.add(new Friend("friend1","default-avatar.png",50));
        friends.add(new Friend("friend2","default-avatar.png",50));
        friends.add(new Friend("friend3","default-avatar.png",50));
        friends.add(new Friend("friend4","default-avatar.png",50));
        friends.add(new Friend("friend5","default-avatar.png",50));
        return friends;

        //return m_friends;
    }

    // Them ban be
    // Tra ve
    //      1: them thanh cong
    //      < 0: loi, loi xem trong file ErrorList.java
    public int addFriend(String currentUserName, String friendUsername) {
        DbHelper database = Ultility.getDbHelper();
        return database.addFriend(currentUserName, friendUsername);
    }

    // Mua vat pham
    // Tra ve
    //      false: mua that bai
    //      true: mua thanh cong, tu dong cap nhat database.
    public int buyItem(int itemId) {
        DbHelper database = Ultility.getDbHelper();
        if (m_logined == false) {
            // chua dang nhap
            return ErrorList.NOT_SIGN_IN;
        }
        Item t_item = database.getItem(itemId);

        // khong du tien
        if (m_money < t_item.getPrice()) {
            return ErrorList.NOT_ENOUGH_MONEY;
        }

        m_money = m_money - t_item.getPrice();
        m_itemList.set(itemId - 1, m_itemList.get(itemId - 1) + 1);

        database.updateMoney(m_name, m_money);
        database.updateItemList(m_name, m_itemList);

        return 1;
    }

    // Kiem tra tien trinh nguoi hoc
    // Tra ve true: neu nguoi do da hoan thanh
    // Tra ve false: neu nguoi do chua hoan thanh
    public Boolean checkProcess(int level, int category, int lesson) {
        if (level >= 0) {
            return m_level >= level;
        }
        if (lesson == -1) {
            category = category - 1;

            ArrayList<Boolean> temp = m_checkList.get(category);
            int numFinish = 0;
            for(int i = 0; i < temp.size(); ++i)
                if (temp.get(i) == true) numFinish = numFinish + 1;

            if (numFinish == temp.size())
                return true;
            return false;
        }
        return m_checkList.get(category - 1).get(lesson - 1);
    }
}
