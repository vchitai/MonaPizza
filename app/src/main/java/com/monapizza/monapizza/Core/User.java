package com.monapizza.monapizza.core;

import java.util.ArrayList;

/**
 * Created by chita on 01/11/2017.
 */

public class User {

    // Username
    String    m_name;

    // Cap do
    int       m_level;

    // Ket qua qua trinh hoc tap
    ArrayList<ArrayList<Boolean>> m_checkList;

    User(String name){

    }

    void loadUser() {

    }

    void getPath() {

    }

    void pass(int category, int lesson) {
        ArrayList<Boolean> new_category = m_checkList.get(category);
        new_category.set(lesson, true);
    }

}
