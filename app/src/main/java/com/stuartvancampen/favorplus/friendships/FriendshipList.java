package com.stuartvancampen.favorplus.friendships;

import android.app.Activity;
import android.util.JsonReader;

import com.stuartvancampen.favorplus.R;
import com.stuartvancampen.favorplus.util.MyList;

/**
 * Created by stuart on 1/18/16.
 */
public class FriendshipList extends MyList<Friendship> {
    private static final String JSON_ROOT = "friendships";

    @Override
    public Friendship createObject(JsonReader reader) {
        return new Friendship(reader);
    }

    @Override
    public String getRootJson() {
        return JSON_ROOT;
    }

    public static String getUrl(Activity context) {
        return context.getString(R.string.base_url) + context.getString(R.string.friendship_list_url);
    }
}
