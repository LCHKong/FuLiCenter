package com.lch.fulicenter.model.net;

import android.content.Context;

import com.lch.fulicenter.model.bean.GoodsDetailsBean;
import com.lch.fulicenter.model.bean.MessageBean;

/**
 * Created by LCH on 2017/1/12.
 */

public interface IModeGoods {
    void downData(Context context, int goodsId, OnCompleteListener<GoodsDetailsBean> listener);

    void isCollect(Context context, int goodsId, String userName, OnCompleteListener<MessageBean> listener);

    void setCollect(Context context, int goodsId, String userName, int action, OnCompleteListener<MessageBean> listener);

}
