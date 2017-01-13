package com.lch.fulicenter.controller.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lch.fulicenter.R;
import com.lch.fulicenter.application.I;
import com.lch.fulicenter.controller.activity.GoodsDetailActivity;
import com.lch.fulicenter.model.bean.NewGoodsBean;
import com.lch.fulicenter.model.utils.ImageLoader;
import com.lch.fulicenter.view.MFGT.FooterViewHolder;
import com.lch.fulicenter.view.MFGT.MFGT;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by LCH on 2017/1/11.
 */

public class GoodsAdapter extends RecyclerView.Adapter {
    Context mContext;
    ArrayList<NewGoodsBean> mList;
    String footer;
    boolean isMore;


    public String getFooter() {
        return isMore ? "加载更多数据 " : "没有更多数据";
    }

    public void setFooter(String footer) {
        this.footer = footer;
        notifyDataSetChanged();
    }

    public boolean isMore() {
        return isMore;
    }

    public void setMore(boolean more) {
        isMore = more;
        notifyDataSetChanged();
    }


    public GoodsAdapter(Context context, ArrayList<NewGoodsBean> list) {
        mContext = context;
        mList = new ArrayList<>();
        mList.addAll(list);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == I.TYPE_ITEM) {
            RecyclerView.ViewHolder holder = new GoodsViewHolder(View.inflate(mContext, R.layout.item_goods, null));
            return holder;
        } else {
            RecyclerView.ViewHolder holder = new FooterViewHolder(View.inflate(mContext, R.layout.item_footer, null));
            return holder;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder ParentHolder, final int position) {
        if (getItemViewType(position) == I.TYPE_FOOTER) {
            FooterViewHolder vh = (FooterViewHolder) ParentHolder;
            vh.tvFooter.setText(getFooter());
            return;
        }

        GoodsViewHolder vh = (GoodsViewHolder) ParentHolder;
        ImageLoader.downloadImg(mContext, vh.mIvGoodsThumb, mList.get(position).getGoodsThumb());

        NewGoodsBean goods = mList.get(position);
        vh.mTvGoodsName.setText(mList.get(position).getGoodsName());
        vh.mTvGoodsPrice.setText(mList.get(position).getCurrencyPrice());
        vh.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MFGT.gotoGoodsDettail(mContext, mList.get(position).getGoodsId());
            }
        });

    }

    @Override
    public int getItemCount() {
        return mList.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == getItemCount() - 1) {
            return I.TYPE_FOOTER;
        }
        return I.TYPE_ITEM;
    }

    public void initData(ArrayList<NewGoodsBean> list) {
        if (mList != null) {
            mList.clear();
        }
        mList.addAll(list);
        notifyDataSetChanged();
    }

    public void addData(ArrayList<NewGoodsBean> list) {
        mList.addAll(list);
        notifyDataSetChanged();
    }

    // 排序
    public void sorfGoods(final int sortBy) {
        Collections.sort(mList, new Comparator<NewGoodsBean>() {
            @Override
            public int compare(NewGoodsBean leftBean, NewGoodsBean rightBean) {
                int result = 0;
                switch (sortBy) {
                    case I.SORT_BY_ADDTIME_ASC:
                        result = (int) (leftBean.getAddTime() - rightBean.getAddTime());
                        break;
                    case I.SORT_BY_ADDTIME_DESC:
                        result = (int) (rightBean.getAddTime() - leftBean.getAddTime());
                        break;
                    case I.SORT_BY_PRICE_ASC:
                        result = getPrice(leftBean.getCurrencyPrice()) - getPrice(rightBean.getCurrencyPrice());
                        break;
                    case I.SORT_BY_PRICE_DESC:
                        result = getPrice(rightBean.getCurrencyPrice()) - getPrice(leftBean.getCurrencyPrice());
                        break;
                }
                return result;
            }
        });
        notifyDataSetChanged();
    }

    int getPrice(String price) {
        int p = 0;
        p = Integer.valueOf(price.substring(price.indexOf("￥") + 1));
        return p;
    }

    class GoodsViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.ivGoodsThume)
        ImageView mIvGoodsThumb;
        @BindView(R.id.tvGoodsName)
        TextView mTvGoodsName;
        @BindView(R.id.tvGoodsPrice)
        TextView mTvGoodsPrice;
        @BindView(R.id.layout_goods)
        LinearLayout tvLayoutGoods;

        GoodsViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

    }
}