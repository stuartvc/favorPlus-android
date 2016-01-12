package com.stuartvancampen.favorplus.util;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;

import com.stuartvancampen.favorplus.R;

/**
 * Created by stuart on 1/11/16.
 */
public abstract class MyFragmentHostActivity extends MyActivity {

    private static final String FRAGMENT_TAG = "FRAGMENT_TAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        insertFragment();
    }

    private void insertFragment() {
        FragmentManager fragmentManager = getFragmentManager();

        Fragment fragment = fragmentManager.findFragmentByTag(FRAGMENT_TAG);

        if (fragment == null) {
            fragment = constructFragment();
        }

        fragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment, FRAGMENT_TAG)
                .commit();
    }

    protected abstract Fragment constructFragment();
}
