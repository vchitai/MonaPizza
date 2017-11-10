package com.monapizza.monapizza.UI;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.design.widget.TabLayout;

import com.monapizza.monapizza.R;

public class MainActivity extends AppCompatActivity {
    private ViewPager mMainViewPager;
    private MainPagerAdapter mMainPagerAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMainPagerAdapter = new MainPagerAdapter(this,getSupportFragmentManager());

        mMainViewPager = (ViewPager) findViewById(R.id.main_view_pager);
        mMainViewPager.setAdapter(mMainPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.main_tab_layout);
        tabLayout.setupWithViewPager(mMainViewPager);
    }
}
