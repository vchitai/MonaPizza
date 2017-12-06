package com.monapizza.monapizza.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.monapizza.monapizza.R;
import com.monapizza.monapizza.core.Ultility;
import com.monapizza.monapizza.core.Word;
import com.monapizza.monapizza.database.DbHelper;

import java.util.ArrayList;

public class LecturesFragment extends Fragment {

    public LecturesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        DbHelper dbHelper = Ultility.getDbHelper();

        ArrayList<Word> aw = dbHelper.getWordInLevel(1);
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_lectures, container, false);
    }

}
