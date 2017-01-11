package com.lch.fulicenter.view.MFGT;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.lch.fulicenter.R;

/**
 * Created by LCH on 2017/1/11.
 */

public class FooterViewHolder extends RecyclerView.ViewHolder {
    public TextView tvFooter;

    public FooterViewHolder(View itemView) {
        super(itemView);
        tvFooter = (TextView) itemView.findViewById(R.id.tvRefresh);
    }
}
