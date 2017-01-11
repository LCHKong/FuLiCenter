package com.lch.fulicenter.model.net;

import android.content.Context;

import com.lch.fulicenter.application.I;
import com.lch.fulicenter.model.bean.BoutiqueBean;
import com.lch.fulicenter.model.utils.OkHttpUtils;

/**
 * Created by LCH on 2017/1/11.
 */

public class ModelGoodsDetail implements IModelGoodsDetail {
    @Override
    public void downData(Context context, int goodsId,  OnCompleteListener<BoutiqueBean[]> listener) {
        OkHttpUtils<BoutiqueBean[]> utils = new OkHttpUtils<>(context);
        utils.setRequestUrl(I.REQUEST_FIND_GOOD_DETAILS)
                .addParam(I.Goods.KEY_GOODS_ID,goodsId+"")
                .targetClass(BoutiqueBean[].class)
                .execute(listener);
    }
}
