package com.monapizza.monapizza.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
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
        /*
        ArrayList<Category> categories = new ArrayList<Category>();
        categories.add(new Category(0,"LOL1",1,"ic_shortcut_account_balance.png"));
        categories.add(new Category(1,"LOL2",1,"ic_shortcut_account_balance.png"));
        categories.add(new Category(2,"LOL3",1,"ic_shortcut_account_balance.png"));
        categories.add(new Category(3,"LOL4",1,"ic_shortcut_account_balance.png"));
        categories.add(new Category(4,"LOL5",1,"ic_shortcut_account_balance.png"));*/

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
                if (position % 3 == 0 || lecturesListAdapter.getItemViewType(position) <= lecturesListAdapter.TYPE_SEPARATOR)
                    return 4;
                else
                return 2;
            }
        });
        recyclerView.setLayoutManager(mng_layout);
        //recyclerView.setLayoutManager(new LinearLayoutManager(MonaPizza.getAppContext()));
        // Inflate the layout for this fragment
        return rootView;
    }

}
