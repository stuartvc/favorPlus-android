package com.stuartvancampen.favorplus.friendships;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.stuartvancampen.favorplus.R;
import com.stuartvancampen.favorplus.user.User;
import com.stuartvancampen.favorplus.user.UserList;
import com.stuartvancampen.favorplus.util.MyAdapter;
import com.stuartvancampen.favorplus.util.MyFragment;

/**
 * Created by stuart on 1/17/16.
 */
public class FriendshipsAdapter extends MyAdapter<FriendshipVH, Friendship, FriendshipList> {
    private static final String TAG = FriendshipsAdapter.class.getSimpleName();

    public FriendshipsAdapter(MyFragment fragment) {
        super(fragment);
    }

    @Override
    public FriendshipVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_list_item, parent, false);

        FriendshipVH friendshipVH = new FriendshipVH(v);
        friendshipVH.setOnItemClickListener(mFragment);
        return friendshipVH;
    }

    @Override
    public void onBindViewHolder(FriendshipVH holder, int position) {
        Friendship friendship = getItem(position);
        if (friendship != null) {
            holder.mUserName.setText(friendship.getFriend().getFullName());
            holder.mTotalFavors.setText(String.valueOf(friendship.getTotalFavors()));
        }
        else {
            Log.e(TAG, "could not find post at position " + String.valueOf(position));
        }
    }
}
