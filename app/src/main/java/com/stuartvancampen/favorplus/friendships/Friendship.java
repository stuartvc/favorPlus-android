package com.stuartvancampen.favorplus.friendships;

import android.util.JsonReader;
import android.util.JsonToken;

import com.stuartvancampen.favorplus.user.User;
import com.stuartvancampen.favorplus.util.MyObject;

import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by stuart on 1/18/16.
 */
public class Friendship extends MyObject {
    private static final String ROOT_JSON = "friendship";

    private static final String USER_ID = "user_id";
    private static final String FRIEND_ID = "friend_id";
    private static final String USER = "user";
    private static final String FRIEND = "friend";
    private static final String TRANSACTION_TOTAL = "transaction_total";
    private static final String ID = "id";

    private Long mUserId;
    private Long mFriendId;
    private User mUser;
    private User mFriend;
    private Long mTransactionTotal;
    private Long mId;

    @SuppressWarnings("unused")
    public Friendship() {
    }

    public Friendship(String jsonString) {
        super(jsonString);
    }

    @SuppressWarnings("unused")
    public Friendship(JsonReader reader) {
        super(reader);
    }


    public Friendship(User friend) {
        mFriendId = friend.getId();
    }

    @Override
    public void loadFromJson(JsonReader reader) throws IOException {
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name == null) {
                reader.skipValue();
            }
            else if (reader.peek() == JsonToken.NULL) {
                reader.skipValue();
            }
            else if (name.equals(USER_ID)) {
                mUserId = reader.nextLong();
            }
            else if (name.equals(FRIEND_ID)) {
                mFriendId = reader.nextLong();
            }
            else if (name.equals(USER)) {
                mUser = new User(reader);
            }
            else if (name.equals(FRIEND)) {
                mFriend = new User(reader);
            }
            else if (name.equals(TRANSACTION_TOTAL)) {
                mTransactionTotal = reader.nextLong();
            }
            else if (name.equals(ID)) {
                mId = reader.nextLong();
            }
            else {
                reader.skipValue();
            }
        }
        reader.endObject();
    }

    @Override
    public JSONObject loadToJson() {
        JSONObject user = new JSONObject();
        addIfNotNull(user, USER_ID, mUserId);
        addIfNotNull(user, FRIEND_ID, mFriendId);
        addIfNotNull(user, USER, mUser);
        addIfNotNull(user, FRIEND, mFriend);
        addIfNotNull(user, TRANSACTION_TOTAL, mTransactionTotal);
        addIfNotNull(user, ID, mId);
        return user;
    }

    @Override
    public String getRootJson() {
        return ROOT_JSON;
    }

    public User getUser() {
        return mUser;
    }

    public User getFriend() {
        return mFriend;
    }

    public long getTotalFavors() {
        return (mTransactionTotal != null) ? mTransactionTotal : 0;
    }
}
