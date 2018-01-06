package com.monapizza.monapizza.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
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
    private RecyclerView mLectureList;
    private LecturesListAdapter mLectureListAdapter;

    public LecturesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_lectures, container, false);
        getActivity().supportInvalidateOptionsMenu();
        DbHelper dbHelper = Ultility.getDbHelper();
        ArrayList<Category> categories = dbHelper.getCategoryList();

        mLectureListAdapter = new LecturesListAdapter(categories);
        mLectureList        = (RecyclerView) rootView.findViewById(R.id.lectures_list);
        mLectureList.setAdapter(mLectureListAdapter);
        final GridLayoutManager mng_layout = new GridLayoutManager(this.getActivity(), 4/*In your case 4*/);

        mng_layout.setSpanSizeLookup( new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (mLectureListAdapter.getSpanSize(position) >= 4) {
                    return 4;
                } else {
                    return 2;
                }
            }
        });
        mLectureList.setLayoutManager(mng_layout);

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        mLectureList.setAdapter(mLectureListAdapter);
    }
}
