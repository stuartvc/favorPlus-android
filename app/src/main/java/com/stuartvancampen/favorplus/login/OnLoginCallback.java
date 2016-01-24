package com.stuartvancampen.favorplus.login;

/**
 * Created by Stuart on 15/11/2015.
 */
public interface OnLoginCallback {
    public void onLoginComplete(boolean success, boolean userInitiated);
}
