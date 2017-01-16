package com.lch.fulicenter.model.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by LCH on 2017/1/16.
 */

public class SharePrefrenceUtils {
    private static final String SHARE_PREFRENCE_NAME = "cn.LCH.fulicenter_user";
    private static final String SHARE_PREFRENCE_NAME_USERNAME = "cn.LCH.fulicenter_user_username";
    private static SharePrefrenceUtils instance;
    private static SharedPreferences prefrences;

    public SharePrefrenceUtils(Context context) {
        prefrences = context.getSharedPreferences(SHARE_PREFRENCE_NAME, Context.MODE_PRIVATE);
    }

    public static SharePrefrenceUtils getInstance(Context context) {
        if (instance == null) {
            instance = new SharePrefrenceUtils(context);
        }
        return instance;
    }

    public static void saveUser(String userName) {
        prefrences.edit().putString(SHARE_PREFRENCE_NAME_USERNAME, userName).commit();
    }

    public static void getUser(String userName) {
        prefrences.edit().putString(SHARE_PREFRENCE_NAME_USERNAME, null);
    }

}
