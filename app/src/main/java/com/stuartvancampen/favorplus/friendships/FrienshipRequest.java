package com.stuartvancampen.favorplus.friendships;

import android.app.Activity;
import android.content.Context;
import android.util.JsonReader;
import android.util.JsonToken;

import com.stuartvancampen.favorplus.R;
import com.stuartvancampen.favorplus.util.MyObject;

import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by stuart on 1/20/16.
 */
public class FrienshipRequest extends MyObject{
    private static final String ROOT_JSON = "friendship";

    private static final String EMAIL = "email";

    private String mEmail;

    @SuppressWarnings("unused")
    public FrienshipRequest() {
    }

    public FrienshipRequest(String jsonString) {
        super(jsonString);
    }

    @SuppressWarnings("unused")
    public FrienshipRequest(JsonReader reader) {
        super(reader);
    }


    public FrienshipRequest(String jsonString, String email) {
        mEmail = email;
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
            else if (name.equals(EMAIL)) {
                mEmail = reader.nextString();
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
        addIfNotNull(user, EMAIL, mEmail);
        return user;
    }

    @Override
    public String getRootJson() {
        return ROOT_JSON;
    }

    public static String getCreateUrl(Context context) {
        return context.getString(R.string.base_url) + context.getString(R.string.create_friendship_url);
    }
}
