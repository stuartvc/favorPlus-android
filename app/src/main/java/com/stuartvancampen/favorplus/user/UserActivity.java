package com.stuartvancampen.favorplus.user;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.stuartvancampen.favorplus.util.MyFragmentHostActivity;

/**
 * Created by stuart on 1/17/16.
 */
public class UserActivity extends MyFragmentHostActivity {

    private User mUser;


    public static Intent create(Context context, User user) {
        Intent startIntent = new Intent(context, UserActivity.class);
        Log.d("USER", user.toString());
        startIntent.putExtra("user", user.toString());
        return startIntent;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("user", mUser.toString());
    }

    @Override
    protected void onLoadInstanceState(Bundle savedInstanceState) {
        super.onLoadInstanceState(savedInstanceState);
        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey("user")) {
                mUser = new User(savedInstanceState.getString("user"));
            }
        }
        else {
            Intent intent = getIntent();
            if (intent.hasExtra("user")) {
                mUser = new User(intent.getStringExtra("user"));
            }
        }
    }

    @Override
    protected Fragment constructFragment() {
        return FriendFragment.create(mUser);
    }
}
