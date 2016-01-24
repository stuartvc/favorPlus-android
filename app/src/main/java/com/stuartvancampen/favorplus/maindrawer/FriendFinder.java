package com.stuartvancampen.favorplus.maindrawer;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.stuartvancampen.favorplus.R;
import com.stuartvancampen.favorplus.util.BaseFragment;

/**
 * Created by stuart on 1/20/16.
 */
public class FriendFinder extends BaseFragment {


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sign_up_fragment, container, false);

        /*final EditText name = (EditText) view.findViewById(R.id.sign_up_name);
        final EditText email = (EditText) view.findViewById(R.id.sign_up_email);
        final EditText password = (EditText) view.findViewById(R.id.sign_up_password);*/


        return view;
    }
}
