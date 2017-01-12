package com.lch.fulicenter.controller.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lch.fulicenter.R;
import com.lch.fulicenter.application.I;
import com.lch.fulicenter.model.bean.BoutiqueBean;
import com.lch.fulicenter.model.utils.ImageLoader;
import com.lch.fulicenter.view.MFGT.FooterViewHolder;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by LCH on 2017/1/11.
 */

public class BoutiqueAdapter extends RecyclerView.Adapter {
    Context mContext;
    ArrayList<BoutiqueBean> mList;
    String footer;

    public String getFooter() {
        return footer;
    }

    public void setFooter(String footer) {
        this.footer = footer;
        notifyDataSetChanged();
    }


    public BoutiqueAdapter(Context context, ArrayList<BoutiqueBean> list) {
        mContext = context;
        mList = new ArrayList<>();
        mList.addAll(list);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder =
                new BoutiqueViewHolder(View.inflate(mContext, R.layout.item_boutique, null));
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder ParentHolder, int position) {
        BoutiqueViewHolder vh = (BoutiqueViewHolder) ParentHolder;
        ImageLoader.downloadImg(mContext, vh.mivBoutiqueImg, mList.get(position).getImageurl());
        vh.mtvBoutiqueName.setText(mList.get(position).getName());
        vh.mtvBoutiqueTitle.setText(mList.get(position).getTitle());
        vh.mtvBoutiqueDescription.setText(mList.get(position).getDescription());

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


    public void initData(ArrayList<BoutiqueBean> list) {
        if (mList != null) {
            mList.clear();
        }
        mList.addAll(list);
        notifyDataSetChanged();
    }

    public void addData(ArrayList<BoutiqueBean> list) {
        mList.addAll(list);
        notifyDataSetChanged();
    }

    static class BoutiqueViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.ivBoutiqueImg)
        ImageView mivBoutiqueImg;
        @BindView(R.id.tvBoutiqueTitle)
        TextView mtvBoutiqueTitle;
        @BindView(R.id.tvBoutiqueName)
        TextView mtvBoutiqueName;
        @BindView(R.id.tvBoutiqueDescription)
        TextView mtvBoutiqueDescription;

        BoutiqueViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}