package com.lch.fulicenter.model.net;

import android.content.Context;

import com.lch.fulicenter.application.I;
import com.lch.fulicenter.model.bean.BoutiqueBean;
import com.lch.fulicenter.model.bean.NewGoodsBean;
import com.lch.fulicenter.model.utils.OkHttpUtils;

/**
 * Created by LCH on 2017/1/11.
 */

public class ModeBoutique implements IModelBoutique {
    @Override
    public void downData(Context context, OnCompleteListener<BoutiqueBean[]> listener) {
        OkHttpUtils<BoutiqueBean[]> utils = new OkHttpUtils<>(context);
        utils.setRequestUrl(I.REQUEST_FIND_BOUTIQUES)
                .targetClass(BoutiqueBean[].class)
                .execute(listener);
    }
}
