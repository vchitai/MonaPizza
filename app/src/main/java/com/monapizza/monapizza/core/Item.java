package com.monapizza.monapizza.core;

/**
 * Created by Vinh Ton That on 12/22/2017.
 */

public class Item {
    private int m_id;
    private String m_name;
    private int m_price;
    private String m_image;
    private String m_effect;

    public Item(int id, String name, int price, String image, String effect) {
        m_id = id;
        m_name = name;
        m_price = price;
        m_image = image;
        m_effect = effect;
    }

    public int getId() {
        return m_id;
    }

    public String getName() {
        return m_name;
    }

    public int getPrice() {
        return m_price;
    }

    public String getImage() {
        return m_image;
    }

    public String getEffect() {
        return m_effect;
    }
}
