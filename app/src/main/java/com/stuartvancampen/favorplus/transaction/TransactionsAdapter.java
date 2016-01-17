package com.stuartvancampen.favorplus.transaction;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.stuartvancampen.favorplus.R;
import com.stuartvancampen.favorplus.user.User;
import com.stuartvancampen.favorplus.util.MyAdapter;
import com.stuartvancampen.favorplus.util.MyFragment;
import com.stuartvancampen.favorplus.util.SerializableObject;

/**
 * Created by stuart on 1/16/16.
 */
public class TransactionsAdapter extends MyAdapter<TransactionVH, Transaction, TransactionList> {

    private static final String TAG = TransactionsAdapter.class.getSimpleName();

    public TransactionsAdapter(MyFragment frag) {
        super(frag);
    }

    @Override
    public TransactionVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.transaction_list_item, parent, false);

        TransactionVH transactionVH = new TransactionVH(v);
        transactionVH.setOnItemClickListener(mFragment);
        return transactionVH;
    }

    @Override
    public void onBindViewHolder(TransactionVH holder, int position) {
        Transaction transaction = getItem(position);
        if (transaction != null) {
            holder.mHeader.setText(transaction.getHeaderText());
            holder.mValue.setText(String.valueOf(transaction.getValue()));
            holder.mDescription.setText(transaction.getDescription());
        }
        else {
            Log.e(TAG, "could not find post at position " + String.valueOf(position));
        }
    }
}
