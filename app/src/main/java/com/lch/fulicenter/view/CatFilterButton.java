package com.lch.fulicenter.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.RippleDrawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;

import com.lch.fulicenter.R;
import com.lch.fulicenter.model.bean.CategoryChildBean;

import java.util.ArrayList;

/**
 * Created by LCH on 2017/1/16.
 */

public class CatFilterButton extends Button {
    boolean isExpand;

    public CatFilterButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void initCatFilterButton(String groupName, ArrayList<CategoryChildBean> list) {
        this.setText(groupName);
        setCatFilterButtonListener();
    }

    private void setCatFilterButtonListener() {
        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isExpand) {

                } else {

                }
                setArrow();
            }
        });
    }

    private void setArrow() {
        Drawable right;
        if (isExpand) {
            right = getResources().getDrawable(R.mipmap.arrow2_up);
        } else {
            right = getResources().getDrawable(R.mipmap.arrow2_down);
        }
        right.setBounds(0, 0, right.getIntrinsicWidth(), right.getIntrinsicHeight());
        this.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, right, null);
        isExpand = !isExpand;
    }
}
