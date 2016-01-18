package com.stuartvancampen.favorplus.transaction;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.stuartvancampen.favorplus.util.MyFragmentHostActivity;

/**
 * Created by stuart on 1/17/16.
 */
public class TransactionActivity extends MyFragmentHostActivity {

    private Transaction mTransaction;

    public static Intent create(Context context, Transaction transaction) {
        Intent startIntent = new Intent(context, TransactionActivity.class);
        Log.d("TRANSACTION", transaction.toString());
        startIntent.putExtra("transaction", transaction.toString());
        return startIntent;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("transaction", mTransaction.toString());
    }

    @Override
    protected void onLoadInstanceState(Bundle savedInstanceState) {
        super.onLoadInstanceState(savedInstanceState);
        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey("transaction")) {
                mTransaction = new Transaction(savedInstanceState.getString("transaction"));
            }
        }
        else {
            Intent intent = getIntent();
            if (intent.hasExtra("transaction")) {
                mTransaction = new Transaction(intent.getStringExtra("transaction"));
            }
        }
    }

    @Override
    protected Fragment constructFragment() {
        return TransactionFragment.create(mTransaction);
    }
}
