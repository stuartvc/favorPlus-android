package com.stuartvancampen.favorplus.user;

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
import com.stuartvancampen.favorplus.transaction.CreateTransactionDialogFragment;
import com.stuartvancampen.favorplus.transaction.Transaction;
import com.stuartvancampen.favorplus.transaction.TransactionActivity;
import com.stuartvancampen.favorplus.transaction.TransactionList;
import com.stuartvancampen.favorplus.transaction.TransactionsAdapter;
import com.stuartvancampen.favorplus.util.MyAdapter;
import com.stuartvancampen.favorplus.util.MyFragment;
import com.stuartvancampen.favorplus.util.MyLoader;

/**
 * Created by stuart on 1/11/16.
 */
public class FriendFragment extends MyFragment<Transaction, TransactionList> {
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
        name.setText(mUser.getFirstName());

        TextView oweOneButton = (TextView) view.findViewById(R.id.owe_one_button);
        oweOneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new CreateTransactionDialogFragment().show(getFragmentManager(), mUser);
            }
        });

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.transaction_list_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(mAdapter);

        return view;
    }

    @Override
    public MyLoader createLoader() {
        return new MyLoader<>(getActivity(), mAdapter, TransactionList.class);
    }

    @Override
    public MyAdapter createAdapter() {
        return new TransactionsAdapter(this);
    }

    @Override
    public String getUrlPath() {
        return getString(R.string.users_url) + mUser.getId() + "/" + getString(R.string.transaction_url);
    }

    @Override
    public void OnItemClickListener(View view, int position) {
        Log.d(TAG, "onClick, position:" + position);
        getActivity().startActivity(TransactionActivity.create(getActivity(), ((TransactionsAdapter) mAdapter).getItem(position)));
    }
}
