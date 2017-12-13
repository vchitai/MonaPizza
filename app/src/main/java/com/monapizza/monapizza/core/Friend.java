package com.monapizza.monapizza.core;

/**
 * Created by chita on 12/12/2017.
 */

public class Friend {
    private String mName;
    private String mAvatar;
    private int mProgress;
    public Friend(String name, String avatar, int progress) {
        mName = name;
        mAvatar = avatar;
        mProgress = progress;
    }

    public String getName() {
        return mName;
    }

    public String getAvatar() {
        return mAvatar;
    }

    public int getProgress() {
        return mProgress;
    }

}
