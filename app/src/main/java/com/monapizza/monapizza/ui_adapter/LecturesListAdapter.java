package com.monapizza.monapizza.ui_adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.monapizza.monapizza.MonaPizza;
import com.monapizza.monapizza.R;
import com.monapizza.monapizza.core.Category;
import com.monapizza.monapizza.ui.LessonsActivity;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by chita on 07/12/2017.
 */

public class LecturesListAdapter extends RecyclerView.Adapter<LecturesListAdapter.ViewHolder>  {
    private ArrayList<Category> mCategoryList;

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView mLectureImage;
        public TextView mLectureName;
        public View mLectureView;
        public ViewHolder(View view) {
            super(view);
            mLectureImage = (ImageView) view.findViewById(R.id.lecture_icon);
            mLectureName = (TextView) view.findViewById(R.id.lecture_name);
            mLectureView = view;
        }
    }
    public LecturesListAdapter(ArrayList<Category> categories) {
        mCategoryList = categories;
    }
    @Override
    public LecturesListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context        context  = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View lectureView = inflater.inflate(R.layout.lecture, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(lectureView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(LecturesListAdapter.ViewHolder holder, final int position) {
        String icon = mCategoryList.get(position).getIcon();
        //int id       = context.getResources().getIdentifier(icon, "drawable",  context.getPackageName());
        Drawable drawable = null;
        try {
            drawable = Drawable.createFromStream(MonaPizza.getAppContext().getAssets().open(mCategoryList.get(position).getIcon()), null);
        } catch (IOException e) {
            e.printStackTrace();
        }

        holder.mLectureImage.setBackground(drawable);
        holder.mLectureName.setText(mCategoryList.get(position).getName());
        holder.mLectureView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), LessonsActivity.class);
                intent.putExtra("categoryID", mCategoryList.get(position).getId());

                v.getContext().startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mCategoryList.size();
    }
}
