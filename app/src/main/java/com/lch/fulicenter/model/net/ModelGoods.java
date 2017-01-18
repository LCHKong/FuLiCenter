package com.lch.fulicenter.model.net;

import android.content.Context;
import android.content.SearchRecentSuggestionsProvider;

import com.lch.fulicenter.application.I;
import com.lch.fulicenter.model.bean.GoodsDetailsBean;
import com.lch.fulicenter.model.bean.MessageBean;
import com.lch.fulicenter.model.utils.OkHttpUtils;

/**
 * Created by LCH on 2017/1/12.
 */

public class ModelGoods implements IModeGoods {
    @Override
    public void downData(Context context, int goodsId, OnCompleteListener<GoodsDetailsBean> listener) {
        OkHttpUtils<GoodsDetailsBean> utils = new OkHttpUtils<>(context);
        utils.setRequestUrl(I.REQUEST_FIND_GOOD_DETAILS)
                .addParam(I.Goods.KEY_GOODS_ID, String.valueOf(goodsId))
                .targetClass(GoodsDetailsBean.class)
                .execute(listener);
    }

    @Override
    public void isCollect(Context context, int goodsId, String userName, OnCompleteListener<MessageBean> listener) {
        OkHttpUtils<MessageBean> utils = new OkHttpUtils<>(context);
        utils.setRequestUrl(I.REQUEST_IS_COLLECT)
                .addParam(I.Goods.KEY_GOODS_ID, String.valueOf(goodsId))
                .addParam(I.Collect.USER_NAME, userName)
                .targetClass(MessageBean.class)
                .execute(listener);
    }

    @Override
    public void setCollect(Context context, int goodsId, String userName, int action, OnCompleteListener<MessageBean> listener) {
        OkHttpUtils<MessageBean> utils = new OkHttpUtils<>(context);
        String url = I.REQUEST_ADD_COLLECT;
        if (action == I.ACTION_DELETE_COLLECT) {
            url = I.REQUEST_DELETE_COLLECT;
        }
        utils.setRequestUrl(url)
                .addParam(I.Goods.KEY_GOODS_ID, String.valueOf(goodsId))
                .addParam(I.Collect.USER_NAME, userName)
                .targetClass(MessageBean.class)
                .execute(listener);
    }
}
