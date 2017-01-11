package com.lch.fulicenter.model.net;

import android.content.Context;

import com.lch.fulicenter.model.bean.NewGoodsBean;

/**
 * Created by LCH on 2017/1/11.
 */

public interface IModelNewGoods {
    void downData(Context context, int catId,int pageId, OnCompleteListener<NewGoodsBean[]> listener);

}