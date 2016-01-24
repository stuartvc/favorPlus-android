package com.stuartvancampen.favorplus.transaction;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.stuartvancampen.favorplus.R;
import com.stuartvancampen.favorplus.user.UserActivity;
import com.stuartvancampen.favorplus.util.BaseFragment;

/**
 * Created by stuart on 1/17/16.
 */
public class TransactionFragment extends BaseFragment {

    private Transaction mTransaction;

    public static Fragment create(Transaction transaction) {
        Fragment frag = new TransactionFragment();
        Bundle args = new Bundle();
        args.putString("transaction", transaction.toString());
        frag.setArguments(args);
        return frag;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        mTransaction = new Transaction(args.getString("transaction"));
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.transaction_fragment, container, false);

        TextView transactionsHeader = (TextView) view.findViewById(R.id.transactions_header);
        transactionsHeader.setText(mTransaction.getHeaderText());

        TextView transactionsDescription = (TextView) view.findViewById(R.id.transaction_description_text);
        transactionsDescription.setText(mTransaction.getDescription());

        TextView dateCreated = (TextView) view.findViewById(R.id.date_created_value);
        dateCreated.setText(mTransaction.getDateString());

        return view;
    }
}
