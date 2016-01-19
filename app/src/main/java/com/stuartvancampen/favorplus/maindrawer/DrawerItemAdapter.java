package com.stuartvancampen.favorplus.maindrawer;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.stuartvancampen.favorplus.R;
import com.stuartvancampen.favorplus.user.User;
import com.stuartvancampen.favorplus.user.UserList;

import java.security.InvalidParameterException;

/**
 * Created by stuart on 1/19/16.
 */
public class DrawerItemAdapter extends RecyclerView.Adapter<DrawerItemVH> {

    private static final String TAG = DrawerItemAdapter.class.getSimpleName();
    private final MainDrawerActivity mMainDrawerActivity;

    private UserList mList;

    public DrawerItemAdapter(MainDrawerActivity mainDrawerActivity) {
        super();
        mMainDrawerActivity = mainDrawerActivity;
        mList = null;
    }

    public void addFriends(UserList friendList) {
        mList = friendList;
        notifyDataSetChanged();
    }

    @Override
    public DrawerItemVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = null;
        if (viewType == DrawerItemVH.ITEM_TYPE_HEADER) {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.drawer_list_header, parent, false);
        }
        else if (viewType == DrawerItemVH.ITEM_TYPE_HOME) {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.drawer_list_item, parent, false);
        }
        else if (viewType == DrawerItemVH.ITEM_TYPE_ADD_CONTACT) {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.drawer_list_item, parent, false);
        }
        else if (viewType == DrawerItemVH.ITEM_TYPE_NEW_TRANSACTION) {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.drawer_list_item, parent, false);
        }
        else if (viewType == DrawerItemVH.ITEM_TYPE_SUB_HEADER) {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.drawer_list_subheader, parent, false);
        }
        else if (viewType == DrawerItemVH.ITEM_TYPE_FRIEND) {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.drawer_list_item, parent, false);
        }
        else {
            throw new InvalidParameterException("unsupported type");
        }

        DrawerItemVH drawerItemVH = new DrawerItemVH(v, viewType);
        drawerItemVH.setOnItemClickListener(mMainDrawerActivity);
        return drawerItemVH;
    }

    @Override
    public void onBindViewHolder(DrawerItemVH holder, int position) {
        if (position == DrawerItemVH.ITEM_TYPE_HEADER) {
        }
        else if (position == DrawerItemVH.ITEM_TYPE_HOME) {
            holder.mRowName.setText("Home");
            holder.mImageView.setImageResource(R.drawable.ic_home_white_24dp);
        }
        else if (position == DrawerItemVH.ITEM_TYPE_ADD_CONTACT) {
            holder.mRowName.setText("Add Contact");
            holder.mImageView.setImageResource(R.drawable.ic_person_add_white_24dp);
        }
        else if (position == DrawerItemVH.ITEM_TYPE_NEW_TRANSACTION) {
            holder.mRowName.setText("New Transaction");
            holder.mImageView.setImageResource(R.drawable.ic_favorite_white_24dp);
        }
        else if (position == DrawerItemVH.ITEM_TYPE_SUB_HEADER) {
            holder.mRowName.setText("Friends");
        }
        else if (position >= DrawerItemVH.ITEM_TYPE_FRIEND) {
            User user = getItem(position - DrawerItemVH.ITEM_TYPE_FRIEND);
            if (user != null) {
                holder.mRowName.setText(user.getFirstName());
                holder.mImageView.setImageResource(R.drawable.ic_person_white_24dp);
            }
            else {
                Log.e(TAG, "could not find post at position " + String.valueOf(position - DrawerItemVH.ITEM_TYPE_FRIEND));
            }
        }
        else {
            throw new InvalidParameterException("unsupported type");
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position >= DrawerItemVH.ITEM_TYPE_FRIEND) {
            return DrawerItemVH.ITEM_TYPE_FRIEND;
        }
        else {
            return position; //position corresponds to DrawerItemVH.ITEM_TYPE_<TYPE>
        }
    }

    public User getItem(int position) {
        return (mList == null) ? null : mList.get(position);
    }

    @Override
    public int getItemCount() {
        return DrawerItemVH.ITEM_TYPE_FRIEND + ((mList == null) ? 0 : mList.size());
    }

    /*@Override
    public void OnItemClickListener(View view, int position) {
        if (position == DrawerItemVH.ITEM_TYPE_HEADER) {
        }
        else if (position == DrawerItemVH.ITEM_TYPE_HOME) {

        }
        else if (position == DrawerItemVH.ITEM_TYPE_ADD_CONTACT) {
        }
        else if (position == DrawerItemVH.ITEM_TYPE_NEW_TRANSACTION) {
        }
        else if (position == DrawerItemVH.ITEM_TYPE_SUB_HEADER) {
        }
        else if (position == DrawerItemVH.ITEM_TYPE_FRIEND) {
            User user = getItem(position - DrawerItemVH.ITEM_TYPE_FRIEND);
            if (user != null) {
                mMainDrawerActivity.selectItem(position);
            }
            else {
                Log.e(TAG, "could not find post at position " + String.valueOf(position - DrawerItemVH.ITEM_TYPE_FRIEND));
            }
        }
        else {
            throw new InvalidParameterException("unsupported type");
        }
    }*/
}
