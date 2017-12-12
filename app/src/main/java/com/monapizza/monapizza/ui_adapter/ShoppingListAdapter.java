package com.monapizza.monapizza.ui_adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.monapizza.monapizza.R;

/**
 * Created by chita on 12/12/2017.
 */

public class ShoppingListAdapter extends RecyclerView.Adapter<ShoppingListAdapter.ViewHolder>  {
    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView mItemImage;
        public TextView  mItemName;
        public TextView  mItemSummary;
        public View      mItemView;
        public ViewHolder(View view) {
            super(view);
            mItemImage = (ImageView) view.findViewById(R.id.shop_item_icon);
            mItemName = (TextView) view.findViewById(R.id.shop_item_name);
            mItemSummary = (TextView) view.findViewById(R.id.shop_item_summary);
            mItemView = view;
        }
    }
    public ShoppingListAdapter() {
    }
    @Override
    public ShoppingListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context        context  = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View lectureView = inflater.inflate(R.layout.shop_item, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(lectureView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ShoppingListAdapter.ViewHolder holder, final int position) {
        //Initialize View
    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
