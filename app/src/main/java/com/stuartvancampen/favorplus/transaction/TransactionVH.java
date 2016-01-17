package com.stuartvancampen.favorplus.transaction;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.stuartvancampen.favorplus.R;
import com.stuartvancampen.favorplus.util.OnItemClickListener;

/**
 * Created by stuart on 1/16/16.
 */
public class TransactionVH extends RecyclerView.ViewHolder implements View.OnClickListener{
    private static final String TAG = TransactionVH.class.getSimpleName();

    public TextView mHeader;
    public TextView mValue;
    public TextView mDescription;
    private OnItemClickListener mOnItemClickListener;


    public TransactionVH(View itemView) {
        super(itemView);
        mHeader = (TextView) itemView.findViewById(R.id.transation_header);
        mValue = (TextView) itemView.findViewById(R.id.transaction_value);
        mDescription = (TextView) itemView.findViewById(R.id.transaction_description);
        itemView.setOnClickListener(this);
    }

    public void setOnItemClickListener(final OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    @Override
    public void onClick(View v) {
        Log.d(TAG, "clicked " + getAdapterPosition());
        mOnItemClickListener.OnItemClickListener(v, getAdapterPosition());
    }
}
