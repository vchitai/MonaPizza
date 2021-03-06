package com.monapizza.monapizza.ui_adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
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
import com.monapizza.monapizza.core.User;
import com.monapizza.monapizza.ui.LessonsActivity;
import com.monapizza.monapizza.ui.QuizActivity;

import java.util.ArrayList;

/**
 * Created by chita on 07/12/2017.
 */

public class LecturesListAdapter extends RecyclerView.Adapter<LecturesListAdapter.ViewHolder>  {
    private ArrayList<Category> mCategoryList;
    private ArrayList<Integer>  mRealPos;
    private ArrayList<Integer>  mSpanSize;

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
        mSpanSize = new ArrayList<>();
        ArrayList<Integer> categoryLocation = new ArrayList<>();
        int prev = 0;
        for (int i = 0; i<mCategoryList.size(); i++) {
            int now = i + mCategoryList.get(i).getLevel()-1;
            if (now - prev == 2) {
                categoryLocation.add(now-1);
            }
            mRealPos.add(now);
            prev = now;
        }

        categoryLocation.add(mRealPos.get(mRealPos.size()-1)+1);
        int j = 0;
        int k = 0;
        int l = 0;
        for (int i = 0; i<mRealPos.size(); i++) {
            if (categoryLocation.get(k) == mRealPos.get(i) - 1) {
                int x = l-1;
                int y = l-2;
                if (x > 0) {
                    if (y < 0 || mSpanSize.get(y) == 6)
                        mSpanSize.set(x, 4);
                    else if (mSpanSize.get(x) == 2 && mSpanSize.get(y) == 4) {
                        mSpanSize.set(x,2);
                        mSpanSize.set(y,2);
                    }
                }
                mSpanSize.add(6);
                j = 0;
                l++;
                k++;
            }
            if (j % 3 == 0) {
                mSpanSize.add(4);
            } else {
                mSpanSize.add(2);
            }
            j++;
            l++;
        }
        mSpanSize.add(6);
        j = 0;
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
            int flag = 0;
            flag = User.getInstance().getStatus(-1,realPos+1,-1);
            String icon = mCategoryList.get(realPos).getIcon();
            if (flag == User.getInstance().STT_PASS)
                icon = "pictures/default.png";
            else if (flag == User.getInstance().STT_LOCK) {
                icon = "pictures/default_lock.png";
                holder.mLectureView.setEnabled(false);
            }
            RoundedBitmapDrawable drawable = MonaPizza.getRoundedBitmapDrawable(icon);
            holder.mLectureImage.setImageDrawable(drawable);

            holder.mLectureName.setText(mCategoryList.get(realPos).getName());
            holder.mLectureView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), LessonsActivity.class);
                    intent.putExtra(MonaPizza.getResourceString(R.string.EA_CategoryID), mCategoryList.get(realPos).getId());

                    v.getContext().startActivity(intent);
                }
            });

        } else {
            int flag = 0;
            flag = User.getInstance().getStatus(-realPos,-1,-1);
            if (flag == User.getInstance().STT_PASS) {
                holder.mCheckpointButton.setText(MonaPizza.getResourceString(R.string.completed_checkpoint));
            }
            else if (flag == User.getInstance().STT_LOCK) {
                holder.mCheckpointButton.setBackground(MonaPizza.getAppContext().getDrawable(R.drawable.round_corner_button_l));
                holder.mCheckpointButton.setEnabled(false);
            }
            holder.mCheckpointButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), QuizActivity.class);
                    intent.putExtra(MonaPizza.getResourceString(R.string.EA_LevelID), -realPos);

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

    public int getSpanSize(int position) {
       return mSpanSize.get(position);
    }
}
