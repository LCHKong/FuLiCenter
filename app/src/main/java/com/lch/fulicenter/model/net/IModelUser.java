package com.lch.fulicenter.model.net;

import android.content.Context;

import com.lch.fulicenter.model.bean.MessageBean;
import com.lch.fulicenter.model.bean.User;
import com.lch.fulicenter.model.utils.OkHttpUtils;

import java.io.File;

/**
 * Created by LCH on 2017/1/16.
 */

public interface IModelUser {
    void login(Context context, String userName, String passWord, OnCompleteListener<String> listener);

    void register(Context context, String userName, String userNick, String passWord, OnCompleteListener<String> listener);

    void update(Context context, String userName, String userNick, OnCompleteListener<String> listener);

    void uploadAvatar(Context context, String userName, File file, OnCompleteListener<String> listener);

    void collecCount(Context context, String userName, OnCompleteListener<MessageBean> listener);

}
