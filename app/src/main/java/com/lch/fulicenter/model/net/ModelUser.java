package com.lch.fulicenter.model.net;

import android.content.Context;

import com.lch.fulicenter.application.FuLiCenterApplication;
import com.lch.fulicenter.application.I;
import com.lch.fulicenter.model.bean.CartBean;
import com.lch.fulicenter.model.bean.CategoryChildBean;
import com.lch.fulicenter.model.bean.CollectBean;
import com.lch.fulicenter.model.bean.MessageBean;
import com.lch.fulicenter.model.utils.MD5;
import com.lch.fulicenter.model.utils.OkHttpUtils;

import java.io.File;

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

    @Override
    public void uploadAvatar(Context context, String userName, File file, OnCompleteListener<String> listener) {
        OkHttpUtils<String> utils = new OkHttpUtils<>(context);
        utils.setRequestUrl(I.REQUEST_UPDATE_AVATAR)
                .addParam(I.NAME_OR_HXID, userName)
                .addParam(I.AVATAR_TYPE, I.AVATAR_TYPE_USER_PATH)
                .addFile2(file)
                .post()
                .targetClass(String.class)
                .execute(listener);
    }

    @Override
    public void collecCount(Context context, String userName, OnCompleteListener<MessageBean> listener) {
        OkHttpUtils<MessageBean> utils = new OkHttpUtils<>(context);
        utils.setRequestUrl(I.REQUEST_FIND_COLLECT_COUNT)
                .addParam(I.Collect.USER_NAME, userName)
                .targetClass(MessageBean.class)
                .execute(listener);
    }

    @Override
    public void downloadCollects(Context context, String userName, int pageId, OnCompleteListener<CollectBean[]> listener) {
        OkHttpUtils<CollectBean[]> utils = new OkHttpUtils<>(context);
        utils.setRequestUrl(I.REQUEST_FIND_COLLECTS)
                .addParam(I.Collect.USER_NAME, userName)
                .addParam(I.PAGE_ID, String.valueOf(pageId))
                .addParam(I.PAGE_SIZE, String.valueOf(I.PAGE_SIZE_DEFAULT))
                .targetClass(CollectBean[].class)
                .execute(listener);
    }

    @Override
    public void getCart(Context context, String userName, OnCompleteListener<CartBean[]> listener) {
        OkHttpUtils<CartBean[]> utils = new OkHttpUtils<>(context);
        utils.setRequestUrl(I.REQUEST_FIND_CARTS)
                .addParam(I.Cart.USER_NAME, userName)
                .targetClass(CartBean[].class)
                .execute(listener);

    }

    private void addCart(Context context, String userName, int goodsId, int count, OnCompleteListener<MessageBean> listener) {
        OkHttpUtils<MessageBean> utils = new OkHttpUtils<>(context);
        utils.setRequestUrl(I.REQUEST_ADD_CART)
                .addParam(I.Cart.USER_NAME, userName)
                .addParam(I.Cart.GOODS_ID, String.valueOf(goodsId))
                .addParam(I.Cart.COUNT, String.valueOf(count))
                .addParam(I.Cart.IS_CHECKED, String.valueOf(false))
                .targetClass(MessageBean.class)
                .execute(listener);

    }

    private void delCart(Context context, int cartId, OnCompleteListener<MessageBean> listener) {
        OkHttpUtils<MessageBean> utils = new OkHttpUtils<>(context);
        utils.setRequestUrl(I.REQUEST_DELETE_CART)
                .addParam(I.Cart.ID, String.valueOf(cartId))
                .targetClass(MessageBean.class)
                .execute(listener);

    }

    private void updateCart(Context context, int cartId, int count, OnCompleteListener<MessageBean> listener) {
        OkHttpUtils<MessageBean> utils = new OkHttpUtils<>(context);
        utils.setRequestUrl(I.REQUEST_UPDATE_CART)
                .addParam(I.Cart.ID, String.valueOf(cartId))
                .addParam(I.Cart.COUNT, String.valueOf(count))
                .addParam(I.Cart.IS_CHECKED, String.valueOf(false))
                .targetClass(MessageBean.class)
                .execute(listener);

    }

    @Override
    public void updateCart(Context context, int action, String userName, int goodIs, int count, int cartId, OnCompleteListener<MessageBean> listener) {
        if (action == I.ACTION_CART_ADD) {
            addCart(context, userName, goodIs, 1, listener);
        } else if (action == I.ACTION_CART_DEL) {
            delCart(context, cartId, listener);
        } else {
            updateCart(context, cartId, count, listener);
        }
    }
}
