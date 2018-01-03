package com.monapizza.monapizza.ui_adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.monapizza.monapizza.MonaPizza;
import com.monapizza.monapizza.R;
import com.monapizza.monapizza.core.Friend;
import com.monapizza.monapizza.core.User;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by chita on 12/12/2017.
 */

public class FriendsListAdapter extends RecyclerView.Adapter<FriendsListAdapter.ViewHolder>  {
    private ArrayList<Friend> mFriends;
    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView mFriendImage;
        public TextView  mFriendName;
        public TextView  mFriendSummary;
        public View      mFriendView;
        public ViewHolder(View view) {
            super(view);
            mFriendImage = (ImageView) view.findViewById(R.id.friend_avatar);
            mFriendName = (TextView) view.findViewById(R.id.friend_name);
            mFriendSummary = (TextView) view.findViewById(R.id.friend_summary);
            mFriendView = view;
        }
    }
    public FriendsListAdapter(ArrayList<Friend> friends) {
        mFriends = friends;
    }
    @Override
    public FriendsListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context        context  = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View lectureView = inflater.inflate(R.layout.friend, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(lectureView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(FriendsListAdapter.ViewHolder holder, final int position) {
        Friend   currentFriend = mFriends.get(position);
        Drawable drawable      = null;
        try {
            drawable = Drawable.createFromStream(MonaPizza.getAppContext().getAssets().open(currentFriend.getAvatar()), null);
        } catch (IOException e) {
            e.printStackTrace();
        }
        holder.mFriendImage.setBackground(drawable);
        holder.mFriendName.setText(currentFriend.getName());
        String progress = "Progress : " + currentFriend.getProgress() + "%";
        holder.mFriendSummary.setText(progress);
    }

    @Override
    public int getItemCount() {
        return mFriends.size();
    }

    public void reload() {
        mFriends = User.getInstance().getFriendList();
    }
}
