package com.stuartvancampen.favorplus.transaction;

import android.content.Context;
import android.util.JsonReader;
import android.util.JsonToken;

import com.stuartvancampen.favorplus.R;
import com.stuartvancampen.favorplus.session.Session;
import com.stuartvancampen.favorplus.user.User;
import com.stuartvancampen.favorplus.util.MyObject;

import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by stuart on 1/16/16.
 */
public class Transaction extends MyObject {
    private static final String ROOT_JSON = "transaction";

    private static final String USER_ID = "user_id";
    private static final String FRIEND_ID = "friend_id";
    private static final String USER = "user";
    private static final String FRIEND = "friend";
    private static final String DESCRIPTION = "description";
    private static final String VALUE = "value";

    private Long mUserId;
    private Long mFriendId;
    private Long mValue;
    private String mDescription;
    private User mUser;
    private User mFriend;

    @SuppressWarnings("unused")
    public Transaction() {
    }

    public Transaction(String jsonString) {
        super(jsonString);
    }

    @SuppressWarnings("unused")
    public Transaction(JsonReader reader) {
        super(reader);
    }

    public Transaction(User user, User friend, Long value, String description) {
        mUserId = user.getId();
        mFriendId = friend.getId();
        mValue = value;
        mDescription = description;
    }

    public User getUser() {
        return mUser;
    }

    public User getFriend() {
        return mFriend;
    }

    public Long getValue() {
        return mValue;
    }

    public String getDescription() {
        return mDescription;
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
            else if (name.equals(VALUE)) {
                mValue = reader.nextLong();
            }
            else if (name.equals(DESCRIPTION)) {
                mDescription = reader.nextString();
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
        addIfNotNull(user, USER_ID, String.valueOf(mUserId));
        addIfNotNull(user, FRIEND_ID, String.valueOf(mFriendId));
        addIfNotNull(user, USER, mUser);
        addIfNotNull(user, FRIEND, mFriend);
        addIfNotNull(user, VALUE, String.valueOf(mValue));
        addIfNotNull(user, DESCRIPTION, String.valueOf(mDescription));
        return user;
    }

    @Override
    public String getRootJson() {
        return ROOT_JSON;
    }

    public static String getCreateUrl(Context context) {
        return context.getString(R.string.base_url) + context.getString(R.string.transaction_url);
    }

    public String getHeaderText() {
        User loggedInUser = Session.getInstance().getLoggedInUser();
        if (loggedInUser != null) {
            if (loggedInUser.getId() == mUser.getId()) {
                return "You owe " + mFriend.getFirstName() + " a favor";
            }
            else if (loggedInUser.getId() == mFriend.getId()) {
                return mUser.getFirstName() + " owes you a favor";
            }
            else {
                return mUser.getFirstName() + " owes "+ mFriend.getFirstName() +" a favor";
            }
        }
        return "";
    }
}
