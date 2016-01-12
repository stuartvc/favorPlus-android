package com.stuartvancampen.favorplus.login;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;

import com.stuartvancampen.favorplus.util.MyFragmentHostActivity;


public class LoginActivity extends MyFragmentHostActivity {


    public static Intent create(Context context) {
        return new Intent(context, LoginActivity.class);
    }

    @Override
    protected Fragment constructFragment() {
        return new LoginFragment();
    }
}
