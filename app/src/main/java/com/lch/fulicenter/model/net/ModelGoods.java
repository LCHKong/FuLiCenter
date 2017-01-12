package com.lch.fulicenter.model.net;

import android.content.Context;

import com.lch.fulicenter.application.I;
import com.lch.fulicenter.model.bean.GoodsDetailsBean;
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
}
