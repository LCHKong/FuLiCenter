package com.lch.fulicenter.model.net;

import android.content.Context;

import com.lch.fulicenter.model.bean.BoutiqueBean;
import com.lch.fulicenter.model.bean.NewGoodsBean;

/**
 * Created by LCH on 2017/1/11.
 */

public interface IModelBoutique {
    void downData(Context context, OnCompleteListener<BoutiqueBean[]> listener);

}