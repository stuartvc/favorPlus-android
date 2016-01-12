package com.stuartvancampen.favorplus.maindrawer;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.stuartvancampen.favorplus.R;
import com.stuartvancampen.favorplus.util.BaseFragment;

/**
 * Created by stuart on 1/6/16.
 */
public class MainDrawerFragment extends BaseFragment{

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_drawer, null, false);

        View getStartedButton = view.findViewById(R.id.get_started_button);
        getStartedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("MainDrawerFragment", "getStartedButton");
            }
        });

        View loginButton = view.findViewById(R.id.login_button);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("MainDrawerFragment", "loginButton");
            }
        });

        return view;
    }
}
