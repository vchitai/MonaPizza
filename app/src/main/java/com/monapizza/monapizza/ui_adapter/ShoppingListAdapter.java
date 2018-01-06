package com.monapizza.monapizza.ui_adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.monapizza.monapizza.MonaPizza;
import com.monapizza.monapizza.R;
import com.monapizza.monapizza.core.Item;
import com.monapizza.monapizza.core.User;

import java.util.ArrayList;

/**
 * Created by chita on 12/12/2017.
 */

public class ShoppingListAdapter extends RecyclerView.Adapter<ShoppingListAdapter.ViewHolder>  {
    ArrayList<Item> mItemList;
    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView mItemImage;
        public TextView  mItemName;
        public TextView  mItemSummary;
        public View      mItemView;
        public Button    mItemBuy;
        public ViewHolder(View view) {
            super(view);
            mItemImage = (ImageView) view.findViewById(R.id.shop_item_icon);
            mItemName = (TextView) view.findViewById(R.id.shop_item_name);
            mItemSummary = (TextView) view.findViewById(R.id.shop_item_summary);
            mItemBuy = (Button) view.findViewById(R.id.shop_item_buy_btn);
            mItemView = view;
        }
    }
    public ShoppingListAdapter(ArrayList<Item> itemList) {
        mItemList = itemList;
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
        final Item currentItem = mItemList.get(position);
        Drawable   drawable    = MonaPizza.getRoundedBitmapDrawable(currentItem.getImage());

        holder.mItemImage.setBackground(drawable);
        holder.mItemName.setText(currentItem.getName());
        holder.mItemSummary.setText(currentItem.getEffect());
        String price = currentItem.getPrice()
                            +" " +MonaPizza.getAppContext().getResources().getString(R.string.currency);
        holder.mItemBuy.setText(price);
        holder.mItemBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User.getInstance().buyItem(currentItem.getId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return mItemList.size();
    }
}
