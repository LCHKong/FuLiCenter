package com.lch.fulicenter.model.net;

import android.content.Context;

import com.lch.fulicenter.model.bean.CategoryChildBean;
import com.lch.fulicenter.model.bean.CategoryGroupBean;

/**
 * Created by LCH on 2017/1/13.
 */

public interface IModelNewCategory {
    void downData(Context context, OnCompleteListener<CategoryGroupBean[]> listener);

    void downData(Context context, int parentId, OnCompleteListener<CategoryChildBean[]> listener);

}
