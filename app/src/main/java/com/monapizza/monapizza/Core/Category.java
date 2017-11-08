package com.monapizza.monapizza.Core;

/**
 * Created by chita on 01/11/2017.
 */

public class Category {
    String m_name;
    String m_level;
    String m_icon;

    Category(String name, String level, String icon) {
        m_name = name;
        m_level = level;
        m_icon = icon;
    }

    String getName() {
        return m_name;
    }

    String getLevel() {
        return m_level;
    }

    String getIcon() {
        return m_icon;
    }
}
