package com.lch.fulicenter.application;

import android.app.Application;

import com.lch.fulicenter.model.bean.CartBean;
import com.lch.fulicenter.model.bean.User;

import java.util.HashMap;

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
    }

    // 内存保存
    private static User user;
    private static HashMap<Integer, CartBean> myCartList = new HashMap<>();

    public static HashMap<Integer, CartBean> getMyCartList() {
        return myCartList;
    }

    public static void setMyCartList(HashMap<Integer, CartBean> myCartList) {
        FuLiCenterApplication.myCartList = myCartList;
    }

    public static User getUser() {
        return user;
    }

    public static void setUser(User user) {
        FuLiCenterApplication.user = user;
    }
}
