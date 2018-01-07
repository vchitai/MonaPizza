package com.monapizza.monapizza.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.monapizza.monapizza.R;
import com.monapizza.monapizza.core.Ultility;
import com.monapizza.monapizza.core.User;
import com.monapizza.monapizza.ui_adapter.MainPagerAdapter;

public class MainActivity extends AppCompatActivity {
    private ViewPager        mMainViewPager;
    private MainPagerAdapter mMainPagerAdapter;
    private Menu             mMenu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);
        mMainPagerAdapter = new MainPagerAdapter(this,getSupportFragmentManager());

        mMainViewPager = (ViewPager) findViewById(R.id.main_view_pager);
        mMainViewPager.setAdapter(mMainPagerAdapter);
        mMainViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                getSupportActionBar().setTitle(((TabLayout)findViewById(R.id.main_tab_layout)).getTabAt(position).getText());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        TabLayout tabLayout = (TabLayout) findViewById(R.id.main_tab_layout);
        tabLayout.setupWithViewPager(mMainViewPager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        mMenu = menu;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.main_menu_account_mananger) {
            Intent intent = new Intent(this, AccountManagerActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        String money = User.getInstance().getMoney() + " " + getString(R.string.currency);
        menu.findItem(R.id.main_menu_account_money).setTitle(money);
        int progress = (int) (User.getInstance().getProcess() * 100.0 / Ultility.getNumberOfLesson());
        String process = progress + " %";
        menu.findItem(R.id.main_menu_account_process).setTitle(process);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public void onResume() {
        supportInvalidateOptionsMenu();
        super.onResume();
    }

    @Override
    public void onRestart() {
        supportInvalidateOptionsMenu();
        super.onRestart();
    }
}
