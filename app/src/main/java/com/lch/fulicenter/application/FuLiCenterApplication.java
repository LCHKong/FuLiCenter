package com.lch.fulicenter.application;

import android.app.Application;

import com.lch.fulicenter.model.bean.CartBean;
import com.lch.fulicenter.model.bean.User;

import java.util.HashMap;

import cn.sharesdk.framework.ShareSDK;

/**
 * Created by LCH on 2017/1/10.
 */

public class FuLiCenterApplication extends Application {
    private static FuLiCenterApplication instance;

    public static FuLiCenterApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        ShareSDK.initSDK(this);
    }

    // 内存保存
    private static User user;

    public static User getUser() {
        return user;
    }

    public static void setUser(User user) {
        FuLiCenterApplication.user = user;
    }
}
