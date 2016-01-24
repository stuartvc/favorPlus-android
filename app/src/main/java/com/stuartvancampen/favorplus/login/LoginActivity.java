package com.stuartvancampen.favorplus.login;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;

import com.stuartvancampen.favorplus.util.MyFragmentHostActivity;


public class LoginActivity extends MyFragmentHostActivity {


    public static Intent create(Context context) {
        return new Intent(context, LoginActivity.class);
    }

    @Override
    protected Fragment constructFragment() {
        return new LoginFragment();
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return false;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == LoginFragment.SIGN_UP_REQUEST_CODE) {
            if (resultCode == 1) {
                finish();
            }
        }
    }
}
