package com.stuartvancampen.favorplus.transaction;

import android.content.Context;
import android.text.format.DateUtils;
import android.util.JsonReader;
import android.util.JsonToken;
import android.util.Log;

import com.stuartvancampen.favorplus.R;
import com.stuartvancampen.favorplus.session.Session;
import com.stuartvancampen.favorplus.user.User;
import com.stuartvancampen.favorplus.util.MyObject;

import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by stuart on 1/16/16.
 */
public class Transaction extends MyObject {
    private static final String TAG = Transaction.class.getSimpleName();

    private static final String ROOT_JSON = "transaction";

    private static final String USER_ID = "user_id";
    private static final String FRIEND_ID = "friend_id";
    private static final String USER = "user";
    private static final String FRIEND = "friend";
    private static final String DESCRIPTION = "description";
    private static final String VALUE = "value";
    private static final String CREATED_AT = "created_at";

    private Long mUserId;
    private Long mFriendId;
    private Long mValue;
    private String mDescription;
    private User mUser;
    private User mFriend;
    private Date mDateCreated;
    private String mDateCreatedStr;

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
            else if (name.equals(CREATED_AT)) {
                mDateCreatedStr = reader.nextString();
                Log.d("Transaction", "created_at:" + mDateCreatedStr);

                SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                formater.setTimeZone(TimeZone.getTimeZone("UTC"));
                try{
                    mDateCreated = formater.parse(mDateCreatedStr);
                } catch(Exception e){
                    System.out.println(e.getMessage());
                }

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
        addIfNotNull(user, VALUE, mValue);
        addIfNotNull(user, DESCRIPTION, mDescription);
        addIfNotNull(user, CREATED_AT, mDateCreatedStr);
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

    public String getDateString() {
        return DateUtils.getRelativeTimeSpanString (mDateCreated.getTime(), System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS).toString();
    }
}
