package com.lch.fulicenter.controller.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lch.fulicenter.R;
import com.lch.fulicenter.application.FuLiCenterApplication;
import com.lch.fulicenter.application.I;
import com.lch.fulicenter.model.bean.CollectBean;
import com.lch.fulicenter.model.bean.MessageBean;
import com.lch.fulicenter.model.net.IModeGoods;
import com.lch.fulicenter.model.net.ModelGoods;
import com.lch.fulicenter.model.net.OnCompleteListener;
import com.lch.fulicenter.model.utils.CommonUtils;
import com.lch.fulicenter.model.utils.ImageLoader;
import com.lch.fulicenter.view.MFGT.FooterViewHolder;
import com.lch.fulicenter.view.MFGT.MFGT;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by LCH on 2017/1/11.
 */

public class CollectsAdapter extends RecyclerView.Adapter {
    Context mContext;
    ArrayList<CollectBean> mList;
    String footer;
    boolean isMore;
    IModeGoods model;


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


    public CollectsAdapter(Context context, ArrayList<CollectBean> list) {
        mContext = context;
        mList = new ArrayList<>();
        mList.addAll(list);
    }

    public void removeItem(int goodsId) {
        if (goodsId != 0) {
            mList.remove(new CollectBean(goodsId));
            notifyDataSetChanged();
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == I.TYPE_ITEM) {
            RecyclerView.ViewHolder holder = new CollectsViewHolder(View.inflate(mContext, R.layout.item_collect, null));
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

        CollectsViewHolder vh = (CollectsViewHolder) ParentHolder;
        ImageLoader.downloadImg(mContext, vh.mIvGoodsThumb, mList.get(position).getGoodsThumb());

        CollectBean goods = mList.get(position);

        vh.mTvGoodsName.setText(goods.getGoodsName());
        vh.tvItemCollect.setTag(goods);
        vh.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MFGT.gotoGoodsDetail(mContext, mList.get(position).getGoodsId());
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

    public void initData(ArrayList<CollectBean> list) {
        if (mList != null) {
            mList.clear();
        }
        mList.addAll(list);
        notifyDataSetChanged();
    }

    public void addData(ArrayList<CollectBean> list) {
        mList.addAll(list);
        notifyDataSetChanged();
    }


    class CollectsViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.ivGoodsThume)
        ImageView mIvGoodsThumb;
        @BindView(R.id.tvGoodsName)
        TextView mTvGoodsName;
        @BindView(R.id.ivDelete)
        ImageView mivDelete;
        @BindView(R.id.item_collect)
        LinearLayout tvItemCollect;

        CollectsViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        @OnClick(R.id.item_collect)
        public void onGoodsItemClick() {
            CollectBean goods = (CollectBean) tvItemCollect.getTag();
            MFGT.gotoGoodsDetail(mContext, goods.getGoodsId());

        }

        @OnClick(R.id.ivDelete)
        public void deleteCollects() {
            model = new ModelGoods();
            final CollectBean goods = (CollectBean) tvItemCollect.getTag();
            String username = FuLiCenterApplication.getUser().getMuserName();
            model.setCollect(mContext, goods.getGoodsId(), username, I.ACTION_DELETE_COLLECT, new OnCompleteListener<MessageBean>() {
                @Override
                public void onSuccess(MessageBean result) {
                    if (result != null && result.isSuccess()) {
                        mList.remove(goods);
                        notifyDataSetChanged();
                    } else {
                        CommonUtils.showLongToast(result != null ? result.getMsg() :
                                mContext.getResources().getString(R.string.delete_collect_fail));
                    }
                }

                @Override
                public void onError(String error) {

                }
            });
        }

    }
}