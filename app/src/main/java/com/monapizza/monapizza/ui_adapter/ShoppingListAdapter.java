package com.monapizza.monapizza.ui_adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.monapizza.monapizza.MonaPizza;
import com.monapizza.monapizza.R;
import com.monapizza.monapizza.core.ErrorList;
import com.monapizza.monapizza.core.Item;
import com.monapizza.monapizza.core.Ultility;
import com.monapizza.monapizza.core.User;

import java.util.ArrayList;

/**
 * Created by chita on 12/12/2017.
 */

public class ShoppingListAdapter extends RecyclerView.Adapter<ShoppingListAdapter.ViewHolder>  {
    ArrayList<Item> mItemList;
    Context mContext;
    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView mItemImage;
        public TextView  mItemName;
        public TextView  mItemSummary;
        public View      mItemView;
        public Button    mItemBuy;
        public TextView  mItemOwned;
        public ViewHolder(View view) {
            super(view);
            mItemImage = (ImageView) view.findViewById(R.id.shop_item_icon);
            mItemName = (TextView) view.findViewById(R.id.shop_item_name);
            mItemSummary = (TextView) view.findViewById(R.id.shop_item_summary);
            mItemBuy = (Button) view.findViewById(R.id.shop_item_buy_btn);
            mItemOwned = (TextView) view.findViewById(R.id.shop_item_owned);
            mItemView = view;
        }
    }
    public ShoppingListAdapter(Context context, ArrayList<Item> itemList) {
        mContext = context;
        mItemList = itemList;
    }
    @Override
    public ShoppingListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

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
                            +" " +MonaPizza.getResourceString(R.string.currency);
        holder.mItemBuy.setText(price);
        String owned = MonaPizza.getResourceString(R.string.owned) + " " + User.getInstance().getItemList().get(position);
        holder.mItemOwned.setText(owned);
        holder.mItemBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE: {
                                if (User.getInstance().buyItem(currentItem.getId()) == 1) {
                                    ((AppCompatActivity) mContext).supportInvalidateOptionsMenu();
                                    reload();
                                    notifyDataSetChanged();
                                } else {
                                    MonaPizza.toastShowText(ErrorList.getMessage(ErrorList.getExitCode()));
                                }
                                break;
                            }

                            case DialogInterface.BUTTON_NEGATIVE: {
                                dialog.dismiss();
                                break;
                            }
                        }
                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setMessage(MonaPizza.getResourceString(R.string.buy_confirm_ques) + " " + currentItem.getName() + " ?").setPositiveButton(R.string.yes_choice, dialogClickListener)
                        .setNegativeButton(R.string.no_choice, dialogClickListener).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mItemList.size();
    }

    private void reload() {
        mItemList = Ultility.getDbHelper().getItemList();
    }
}
