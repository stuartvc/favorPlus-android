package com.stuartvancampen.favorplus.session;

import android.app.Application;

/**
 * Created by Stuart on 15/11/2015.
 *
 * Overrides the Application class and provides static access to Context
 */
public class FavorPlusApplication extends Application {

    private static FavorPlusApplication sInstance;

    @Override
    public void onCreate() {
        super.onCreate();

        sInstance = this;
    }

    public static FavorPlusApplication getInstance() {
        return sInstance;
    }
}
