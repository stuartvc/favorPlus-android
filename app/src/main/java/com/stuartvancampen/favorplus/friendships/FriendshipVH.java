package com.stuartvancampen.favorplus.friendships;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.stuartvancampen.favorplus.R;
import com.stuartvancampen.favorplus.util.OnItemClickListener;

/**
 * Created by stuart on 1/17/16.
 */
public class FriendshipVH extends RecyclerView.ViewHolder implements View.OnClickListener {
    private static final String TAG = FriendshipVH.class.getSimpleName();

    public TextView mUserName;
    public TextView mTotalFavors;
    public ImageView mAddFavorIcon;
    private OnItemClickListener mOnItemClickListener;


    public FriendshipVH(View itemView) {
        super(itemView);
        mUserName = (TextView) itemView.findViewById(R.id.user_name);
        mTotalFavors = (TextView) itemView.findViewById(R.id.total_favors_text);
        mAddFavorIcon = (ImageView) itemView.findViewById(R.id.add_favor_icon);
        itemView.setOnClickListener(this);
    }

    public void setOnItemClickListener(final OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    @Override
    public void onClick(View v) {
        Log.d(TAG, "clicked " + getAdapterPosition());
        mOnItemClickListener.OnItemClickListener(v, getAdapterPosition());
    }
}
