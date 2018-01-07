package com.monapizza.monapizza.ui;


import android.app.Dialog;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.monapizza.monapizza.MonaPizza;
import com.monapizza.monapizza.R;
import com.monapizza.monapizza.core.ErrorList;
import com.monapizza.monapizza.core.User;
import com.monapizza.monapizza.ui_adapter.FriendsListAdapter;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class FriendsFragment extends Fragment {
    RecyclerView       mFriendList;
    FriendsListAdapter mFriendsListAdapter;
    ConstraintLayout   mFriendEmptyView;

    public FriendsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        User usr = User.getInstance();
        View rootView = inflater.inflate(R.layout.fragment_friends, container, false);
        mFriendEmptyView = (ConstraintLayout)rootView.findViewById(R.id.friend_empty_view);
        mFriendsListAdapter = new FriendsListAdapter(usr.getFriendList());
        mFriendList        = (RecyclerView) rootView.findViewById(R.id.friend_list);
        mFriendList.setAdapter(mFriendsListAdapter);
        mFriendList.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.friend_add_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addFriend();
            }
        });
        notifyChange();
        // Inflate the layout for this fragment
        getActivity().supportInvalidateOptionsMenu();
        return rootView;
    }

    public void notifyChange() {
        ArrayList res = User.getInstance().getFriendList();
        if (User.getInstance().getFriendList().size() == 0) {
            mFriendEmptyView.setVisibility(View.VISIBLE);
            mFriendList.setVisibility(View.GONE);
        } else {
            mFriendEmptyView.setVisibility(View.GONE);
            mFriendList.setVisibility(View.VISIBLE);
        }
    }

    public void addFriend() {
        final Dialog addFriendDialog = new Dialog(getContext());
        addFriendDialog.setContentView(R.layout.add_friend_dialog);
        addFriendDialog.setTitle(getResources().getString(R.string.add_friend_dialog_title));
        final EditText friendNameInput = (EditText) addFriendDialog.findViewById(R.id.add_friend_dialog_friend_name);

        Button         okBtn           = (Button) addFriendDialog.findViewById(R.id.add_friend_dialog_ok_button);
        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (User.getInstance().addFriend(User.getInstance().getUserName(), friendNameInput.getText().toString()) == 1) {
                    MonaPizza.toastShowText(getResources().getString(R.string.IS_AddFriendSuccess));
                    notifyChange();
                    mFriendsListAdapter.reload();
                    mFriendList.getAdapter().notifyDataSetChanged();
                } else {
                    MonaPizza.toastShowText(ErrorList.getMessage(ErrorList.getExitCode()));
                }
                addFriendDialog.dismiss();
            }
        });
        Button cancelBtn = (Button) addFriendDialog.findViewById(R.id.add_friend_dialog_cancel_button);
        cancelBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                addFriendDialog.dismiss();
            }
        });

        addFriendDialog.show();
    }
}
