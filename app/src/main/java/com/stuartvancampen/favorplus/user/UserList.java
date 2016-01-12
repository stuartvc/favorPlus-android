package com.stuartvancampen.favorplus.user;

import android.app.Activity;
import android.util.JsonReader;

import com.stuartvancampen.favorplus.R;
import com.stuartvancampen.favorplus.maindrawer.MainDrawerActivity;
import com.stuartvancampen.favorplus.util.MyList;

/**
 * Created by stuart on 1/11/16.
 */
public class UserList extends MyList<User> {
    private static final String JSON_ROOT = "users";

    @Override
    public User createObject(JsonReader reader){
        return new User(reader);
    }

    @Override
    public String getRootJson() {
        return JSON_ROOT;
    }

    public static String getUrl(Activity context) {
        return context.getString(R.string.base_url) + context.getString(R.string.user_list_url);
    }
}
