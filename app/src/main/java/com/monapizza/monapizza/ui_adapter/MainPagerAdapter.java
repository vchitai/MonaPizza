package com.monapizza.monapizza.ui_adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.monapizza.monapizza.R;
import com.monapizza.monapizza.ui.FriendsFragment;
import com.monapizza.monapizza.ui.LecturesFragment;
import com.monapizza.monapizza.ui.ShoppingFragment;

/**
 * Created by chita on 10/11/2017.
 */

public class MainPagerAdapter extends FragmentPagerAdapter {

    private Context mContext;
    public MainPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new LecturesFragment();
                //break;
            case 1:
                return new FriendsFragment();
                //break;
            case 2:
                return new ShoppingFragment();
                //break;
            default:
                return null;
                //break;
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return mContext.getString(R.string.lectures_fragment_title);
            case 1:
                return mContext.getString(R.string.friends_fragment_title);
            case 2:
                return mContext.getString(R.string.shopping_fragment_title);
        }
        return null;
    }
}
