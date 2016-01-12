package com.stuartvancampen.favorplus.user;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.stuartvancampen.favorplus.R;
import com.stuartvancampen.favorplus.util.BaseFragment;

/**
 * Created by stuart on 1/11/16.
 */
public class FriendFragment extends BaseFragment {
    public static final String TAG = FriendFragment.class.getSimpleName();

    private static final String EXTRA_USER = "user";
    private User mUser;

    public static Fragment create(User user) {
        FriendFragment fragment = new FriendFragment();
        Bundle args = new Bundle();
        args.putString(EXTRA_USER, user.toString());
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        if (args != null) {
            if (args.containsKey(EXTRA_USER)) {
                mUser = new User(args.getString(EXTRA_USER));
            }
            else {
                Log.e(TAG, "user object not found in arguments, finishing");
                getActivity().finish();
            }
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.friend_profile_layout, container, false);

        TextView name = (TextView) view.findViewById(R.id.user_name);
        name.setText(mUser.getName());

        TextView oweOneButton = (TextView) view.findViewById(R.id.owe_one_button);
        oweOneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO you owe me one
                Toast.makeText(FriendFragment.this.getContext(), mUser.getName() + " owes you one!", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}
