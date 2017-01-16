package com.lch.fulicenter.model.net;

import android.content.Context;

import com.lch.fulicenter.model.bean.User;

/**
 * Created by LCH on 2017/1/16.
 */

public interface IModelUser {
    void login(Context context, String userName, String passWord, OnCompleteListener<String> listener);

    void register(Context context, String userName, String userNick, String passWord, OnCompleteListener<String> listener);

}
