package com.monapizza.monapizza.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.monapizza.monapizza.R;
import com.monapizza.monapizza.core.Category;
import com.monapizza.monapizza.core.Ultility;
import com.monapizza.monapizza.database.DbHelper;
import com.monapizza.monapizza.ui_adapter.LecturesListAdapter;

import java.util.ArrayList;

public class LecturesFragment extends Fragment {

    public LecturesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_lectures, container, false);
        DbHelper dbHelper = Ultility.getDbHelper();
        ArrayList<Category> categories = dbHelper.getCategoryList();

        Toolbar toolbar = (Toolbar) rootView.findViewById(R.id.lecture_toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        final LecturesListAdapter lecturesListAdapter = new LecturesListAdapter(categories);
        RecyclerView              recyclerView        = (RecyclerView) rootView.findViewById(R.id.lectures_list);
        recyclerView.setAdapter(lecturesListAdapter);
        final GridLayoutManager mng_layout = new GridLayoutManager(this.getActivity(), 4/*In your case 4*/);
        int lol = lecturesListAdapter.getItemCount();
        mng_layout.setSpanSizeLookup( new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (lecturesListAdapter.getSpanSize(position) >= 4) {
                    return 4;
                } else {
                    return 2;
                }
            }
        });
        recyclerView.setLayoutManager(mng_layout);
        setHasOptionsMenu(true);
        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.main_menu_account_mananger) {
            Intent intent = new Intent(getContext(), AccountManagerActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
