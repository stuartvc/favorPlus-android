package com.stuartvancampen.favorplus.maindrawer;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.stuartvancampen.favorplus.R;
import com.stuartvancampen.favorplus.friendships.Friendship;
import com.stuartvancampen.favorplus.friendships.FriendshipList;
import com.stuartvancampen.favorplus.friendships.FriendshipsAdapter;
import com.stuartvancampen.favorplus.session.Session;
import com.stuartvancampen.favorplus.user.UserActivity;
import com.stuartvancampen.favorplus.util.MyAdapter;
import com.stuartvancampen.favorplus.util.MyFragment;
import com.stuartvancampen.favorplus.util.MyLoader;

/**
 * Created by stuart on 1/17/16.
 */
public class HomeFragment extends MyFragment<Friendship, FriendshipList> {

    private static final String TAG = HomeFragment.class.getSimpleName();

    public static Fragment create() {
        return new HomeFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_layout, container, false);

        TextView name = (TextView) view.findViewById(R.id.user_name);
        name.setText(Session.getInstance().getLoggedInUser().getFirstName());

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.transaction_list_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(mAdapter);

        return view;
    }
    @Override
    public MyLoader createLoader() {
        return new MyLoader<>(getActivity(), mAdapter, FriendshipList.class);
    }

    @Override
    public MyAdapter createAdapter() {
        return new FriendshipsAdapter(this);
    }

    @Override
    public String getUrlPath() {
        return getString(R.string.home_url);
    }

    @Override
    public void OnItemClickListener(View view, int position) {
        Log.d(TAG, "onClick, position:" + position);
        getActivity().startActivity(UserActivity.create(getActivity(), ((FriendshipsAdapter) mAdapter).getItem(position).getFriend()));
    }
}
