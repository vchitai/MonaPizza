package com.monapizza.monapizza.ui;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.monapizza.monapizza.R;
import com.monapizza.monapizza.core.User;
import com.monapizza.monapizza.ui_adapter.FriendsListAdapter;


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
        View rootView = inflater.inflate(R.layout.fragment_friends, container, false);

        FriendsListAdapter friendsListAdapter = new FriendsListAdapter(usr.getFriendList());
        RecyclerView       recyclerView        = (RecyclerView) rootView.findViewById(R.id.friend_list);
        recyclerView.setAdapter(friendsListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        // Inflate the layout for this fragment
        return rootView;
    }

}
