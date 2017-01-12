package com.lch.fulicenter.model.net;

import android.content.Context;

import com.lch.fulicenter.model.bean.GoodsDetailsBean;

/**
 * Created by LCH on 2017/1/12.
 */

public interface IModeGoods {
    void downData(Context context, int goodsId, OnCompleteListener<GoodsDetailsBean> listener);

}
