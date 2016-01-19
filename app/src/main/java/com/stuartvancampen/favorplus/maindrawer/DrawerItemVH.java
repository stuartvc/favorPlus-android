package com.stuartvancampen.favorplus.maindrawer;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.stuartvancampen.favorplus.R;
import com.stuartvancampen.favorplus.util.OnItemClickListener;

import java.security.InvalidParameterException;

/**
 * Created by stuart on 1/19/16.
 */
public class DrawerItemVH extends RecyclerView.ViewHolder implements View.OnClickListener {
    private static final String TAG = DrawerItemVH.class.getSimpleName();

    public static final int ITEM_TYPE_HEADER = 0;
    public static final int ITEM_TYPE_HOME = 1;
    public static final int ITEM_TYPE_ADD_CONTACT = 2;
    public static final int ITEM_TYPE_NEW_TRANSACTION = 3;
    public static final int ITEM_TYPE_SUB_HEADER = 4;
    public static final int ITEM_TYPE_FRIEND = 5;

    public TextView mRowName;
    public ImageView mImageView;
    public int mViewType;
    private OnItemClickListener mOnItemClickListener;


    public DrawerItemVH(View itemView, int viewType) {
        super(itemView);
        if (viewType == ITEM_TYPE_HEADER) {

        }
        else if (viewType == ITEM_TYPE_HOME) {
            mRowName = (TextView) itemView.findViewById(R.id.row_name);
            mImageView = (ImageView) itemView.findViewById(R.id.row_icon);
        }
        else if (viewType == ITEM_TYPE_ADD_CONTACT) {
            mRowName = (TextView) itemView.findViewById(R.id.row_name);
            mImageView = (ImageView) itemView.findViewById(R.id.row_icon);
        }
        else if (viewType == ITEM_TYPE_NEW_TRANSACTION) {
            mRowName = (TextView) itemView.findViewById(R.id.row_name);
            mImageView = (ImageView) itemView.findViewById(R.id.row_icon);
        }
        else if (viewType == ITEM_TYPE_SUB_HEADER) {
            mRowName = (TextView) itemView.findViewById(R.id.row_name);
        }
        else if (viewType == ITEM_TYPE_FRIEND) {
            mRowName = (TextView) itemView.findViewById(R.id.row_name);
            mImageView = (ImageView) itemView.findViewById(R.id.row_icon);
            mViewType = viewType;
        }
        else {
            throw new InvalidParameterException("unsupported type");
        }
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
