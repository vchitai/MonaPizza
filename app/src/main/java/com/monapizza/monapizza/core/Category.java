package com.monapizza.monapizza.core;

/**
 * Created by chita on 01/11/2017.
 */

public class Category {
    //id category
    private int m_id;

    // ten category
    private String m_name;

    // level cua category nay
    private int m_level;

    // link icon de hien thi
    private String m_icon;


    private Category() {}

    // Khoi tao lop Category voi id = category
    public Category(int id, String name, int level, String icon) {
        m_id = id;
        m_name = name;
        m_level = level;
        m_icon = icon;
    }

    // cac ham get ho tro truy xuat

    public int getId() {
        return m_id;
    }

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
