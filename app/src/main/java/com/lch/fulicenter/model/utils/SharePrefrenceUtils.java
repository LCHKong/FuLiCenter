package com.lch.fulicenter.model.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.lch.fulicenter.view.SlideAutoLoopView;

/**
 * Created by LCH on 2017/1/16.
 */

public class SharePrefrenceUtils {
    private static final String SHARE_PREFERENCE_NAME = "cn.ucai.fulicenter_user";
    private static final String SHARE_PREFERENCE_NAME_USERNAME = "cn.ucai.fulicenter_user_username";
    private static SharePrefrenceUtils instance;
    private static SharedPreferences preferences;
    private SharedPreferences.Editor mEditor;
    public static final String SHARE_KEY_USER_NAME = "share_key_user_name";

    public SharePrefrenceUtils(Context context) {
        preferences = context.getSharedPreferences(SHARE_PREFERENCE_NAME, Context.MODE_PRIVATE);
        mEditor = preferences.edit();
    }

    public static SharePrefrenceUtils getInstance(Context context) {
        if (instance == null) {
            instance = new SharePrefrenceUtils(context);
        }
        return instance;
    }

    public void saveUser(String username) {
        mEditor.putString(SHARE_KEY_USER_NAME, username);
        mEditor.commit();

    }

    public String getUser() {
        return preferences.getString(SHARE_KEY_USER_NAME, null);
    }

    public void removeUser() {
        mEditor.remove(SHARE_KEY_USER_NAME);
        mEditor.commit();
    }

}
