package com.stuartvancampen.favorplus.login;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;

import com.stuartvancampen.favorplus.util.MyFragmentHostActivity;

/**
 * Created by Stuart on 17/10/2015.
 */
public class SignUpActivity extends MyFragmentHostActivity {

    public static Intent create(Context context) {
        return new Intent(context, SignUpActivity.class);
    }

    @Override
    protected Fragment constructFragment() {
        return new SignUpFragment();
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return false;
    }
}
