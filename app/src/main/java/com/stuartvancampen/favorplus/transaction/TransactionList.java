package com.stuartvancampen.favorplus.transaction;

import android.app.Activity;
import android.util.JsonReader;

import com.stuartvancampen.favorplus.R;
import com.stuartvancampen.favorplus.util.MyList;

/**
 * Created by stuart on 1/16/16.
 */
public class TransactionList extends MyList<Transaction> {
    private static final String JSON_ROOT = "transactions";


    @Override
    public Transaction createObject(JsonReader reader) {
        return new Transaction(reader);
    }

    @Override
    public String getRootJson() {
        return JSON_ROOT;
    }

    public static String getUrl(Activity context) {
        return context.getString(R.string.base_url) + context.getString(R.string.transaction_url);
    }
}
