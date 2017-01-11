package com.lch.fulicenter.model.net;

import android.content.Context;

import com.lch.fulicenter.model.bean.BoutiqueBean;

/**
 * Created by LCH on 2017/1/11.
 */

public interface IModelGoodsDetail {
    void downData(Context context, int goodsId, OnCompleteListener<BoutiqueBean[]> listener);

}