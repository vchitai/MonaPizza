package com.monapizza.monapizza.core;

import java.util.ArrayList;

/**
 * Created by chita on 12/12/2017.
 */

public class Friend {
    private String mName;
    private String mAvatar;
    private int mProgress;
    private ArrayList<ArrayList<Boolean>> m_checkList;
    public Friend(String name, String avatar, int progress) {
        mName = name;
        mAvatar = avatar;
        m_checkList = Ultility.getDbHelper().loadCheckList(name);

        mProgress = getProcess();
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

    public int getProcess() {
        int res = 0;
        for (int i =0; i<m_checkList.size(); i++)
            for (int j = 0; j <m_checkList.get(i).size(); j++)
                if (m_checkList.get(i).get(j))
                    res++;
        return res;
    }

}
