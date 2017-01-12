package com.lch.fulicenter.controller.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.lch.fulicenter.R;
import com.lch.fulicenter.application.I;
import com.lch.fulicenter.model.bean.GoodsDetailsBean;
import com.lch.fulicenter.model.net.IModeGoods;
import com.lch.fulicenter.model.net.ModelGoods;
import com.lch.fulicenter.model.net.OnCompleteListener;
import com.lch.fulicenter.view.MFGT.MFGT;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class GoodsDetailActivity extends AppCompatActivity {
    int goodsId = 0;
    IModeGoods mModel;

    @BindView(R.id.ivBack)
    ImageView mivBack;
    @BindView(R.id.wv_good_brief)
    WebView mwvGoodBrief;
    @BindView(R.id.tv_good_name_english)
    TextView mtvGoodNameEnglish;
    @BindView(R.id.tv_good_name)
    TextView mtvGoodName;
    @BindView(R.id.tv_good_price)
    TextView mtvGoodPrice;


    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_detail);
        ButterKnife.bind(this);
        goodsId = getIntent().getIntExtra(I.GoodsDetails.KEY_GOODS_ID, 0);
        if (goodsId == 0) {
            MFGT.finish(this);
        } else {
            initData();
        }
    }

    private void initData() {
        mModel = new ModelGoods();
        mModel.downData(this, goodsId, new OnCompleteListener<GoodsDetailsBean>() {
            @Override
            public void onSuccess(GoodsDetailsBean result) {
                if (result != null) {
                    showGoodsDetail(result);
                }
            }

            @Override
            public void onError(String error) {

            }
        });

    }

    private void showGoodsDetail(GoodsDetailsBean result) {
        String e = getIntent().getStringExtra(I.GoodsDetails.KEY_ENGLISH_NAME);
        mtvGoodNameEnglish.setText(e);
    }

    @OnClick(R.id.ivBack)
    public void onClick() {
        MFGT.finish(this);
    }
}
