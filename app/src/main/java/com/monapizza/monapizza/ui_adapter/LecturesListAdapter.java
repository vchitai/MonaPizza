package com.monapizza.monapizza.ui_adapter;

import android.content.Context;
import android.content.Intent;
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
import com.monapizza.monapizza.core.Category;
import com.monapizza.monapizza.core.Ultility;
import com.monapizza.monapizza.ui.LessonsActivity;
import com.monapizza.monapizza.ui.QuizActivity;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by chita on 07/12/2017.
 */

public class LecturesListAdapter extends RecyclerView.Adapter<LecturesListAdapter.ViewHolder>  {
    private ArrayList<Category> mCategoryList;
    private ArrayList<Integer>       mRealPos;

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView mLectureImage;
        public TextView mLectureName;
        public View mLectureView;
        public Button mCheckpointButton;
        public ViewHolder(View view) {
            super(view);
            mLectureImage = (ImageView) view.findViewById(R.id.lecture_icon);
            mLectureName = (TextView) view.findViewById(R.id.lecture_name);
            mCheckpointButton = (Button) view.findViewById(R.id.checkpoint_button);
            mLectureView = view;
        }
    }
    public LecturesListAdapter(ArrayList<Category> categories) {
        mCategoryList = categories;
        mRealPos = new ArrayList<>();
        for (int i = 0; i<mCategoryList.size(); i++) {
            mRealPos.add(i + mCategoryList.get(i).getLevel()-1);
        }
    }
    @Override
    public LecturesListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder viewHolder = null;
        if (viewType > TYPE_SEPARATOR) {
            Context        context  = parent.getContext();
            LayoutInflater inflater = LayoutInflater.from(context);

            // Inflate the custom layout
            View lectureView = inflater.inflate(R.layout.lecture, parent, false);

            // Return a new holder instance
            viewHolder = new ViewHolder(lectureView);
        } else {
            Context        context  = parent.getContext();
            LayoutInflater inflater = LayoutInflater.from(context);
            View checkpointView = inflater.inflate(R.layout.checkpoint, parent, false);
            viewHolder = new ViewHolder(checkpointView);

        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(LecturesListAdapter.ViewHolder holder, int position) {
        final int realPos = getItemViewType(position);
        if (realPos > TYPE_SEPARATOR) {
            String icon = mCategoryList.get(realPos).getIcon();
            //int id       = context.getResources().getIdentifier(icon, "drawable",  context.getPackageName());
            Drawable drawable = null;
            try {
                drawable = Drawable.createFromStream(MonaPizza.getAppContext().getAssets().open(mCategoryList.get(realPos).getIcon()), null);
            } catch (IOException e) {
                e.printStackTrace();
            }

            holder.mLectureImage.setBackground(drawable);
            holder.mLectureName.setText(mCategoryList.get(realPos).getName());
            holder.mLectureView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), LessonsActivity.class);
                    intent.putExtra("categoryID", mCategoryList.get(realPos).getId());

                    v.getContext().startActivity(intent);
                }
            });
        } else {
            holder.mCheckpointButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), QuizActivity.class);
                    intent.putExtra("levelID", -realPos);

                    v.getContext().startActivity(intent);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mCategoryList.size() + Ultility.getDbHelper().getMaxLevel();
    }

    public final static int TYPE_SEPARATOR = -1;

    @Override
    public int getItemViewType(int position) {
        int i;
        for (i = 0; i<mRealPos.size(); i++) {
            if (mRealPos.get(i) >= position)
                break;
        }
        if (i < mRealPos.size() && mRealPos.get(i) == position)
            return i;
        else
            return -mCategoryList.get(i-1).getLevel();
    }
}
