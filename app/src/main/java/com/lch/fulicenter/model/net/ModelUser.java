package com.lch.fulicenter.model.net;

import android.content.Context;

import com.lch.fulicenter.application.I;
import com.lch.fulicenter.model.utils.MD5;
import com.lch.fulicenter.model.utils.OkHttpUtils;

/**
 * Created by LCH on 2017/1/16.
 */

public class ModelUser implements IModelUser {
    @Override
    public void login(Context context, String userName, String passWord, OnCompleteListener<String> listener) {
        OkHttpUtils<String> utils = new OkHttpUtils<>(context);
        utils.setRequestUrl(I.REQUEST_LOGIN)
                .addParam(I.User.USER_NAME, userName)
                .addParam(I.User.PASSWORD, MD5.getMessageDigest(passWord))
                .targetClass(String.class)
                .execute(listener);
    }

    @Override
    public void register(Context context, String userName, String userNick, String passWord, OnCompleteListener<String> listener) {
        OkHttpUtils<String> utils = new OkHttpUtils<>(context);
        utils.setRequestUrl(I.REQUEST_REGISTER)
                .addParam(I.User.USER_NAME, userName)
                .addParam(I.User.NICK, userNick)
                .addParam(I.User.PASSWORD, MD5.getMessageDigest(passWord))
                .post()
                .targetClass(String.class)
                .execute(listener);
    }

    @Override
    public void update(Context context, String userName, String userNick, OnCompleteListener<String> listener) {
        OkHttpUtils<String> utils = new OkHttpUtils<>(context);
        utils.setRequestUrl(I.REQUEST_UPDATE_USER_NICK)
                .addParam(I.User.USER_NAME, userName)
                .addParam(I.User.NICK, userNick)
                .targetClass(String.class)
                .execute(listener);
    }
}
