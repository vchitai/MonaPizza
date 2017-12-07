package com.monapizza.monapizza.core;

import com.monapizza.monapizza.database.DbHelper;

/**
 * Created by chita on 01/11/2017.
 */

public class Category {
    // ten category
    private String m_name;

    // level cua category nay
    private int m_level;

    // link icon de hien thi
    private String m_icon;


    private Category() {}

    // Khoi tao lop Category voi id = category
    public Category(String name, int level, String icon) {
        m_name = name;
        m_level = level;
        m_icon = icon;
    }

    // cac ham get ho tro truy xuat

    public String getName() {
        return m_name;
    }

    public int getLevel() {
        return m_level;
    }

    public String getIcon() {
        return m_icon;
    }
}
