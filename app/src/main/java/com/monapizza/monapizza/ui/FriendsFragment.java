package com.monapizza.monapizza.ui;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.monapizza.monapizza.R;
import com.monapizza.monapizza.core.Ultility;
import com.monapizza.monapizza.core.User;
import com.monapizza.monapizza.core.Word;
import com.monapizza.monapizza.database.DbHelper;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class FriendsFragment extends Fragment {


    public FriendsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        User usr = User.getInstance();

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_friends, container, false);
    }

}
