package com.lch.fulicenter.controller.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.lch.fulicenter.R;
import com.lch.fulicenter.application.FuLiCenterApplication;
import com.lch.fulicenter.application.I;
import com.lch.fulicenter.model.bean.AlbumsBean;
import com.lch.fulicenter.model.bean.GoodsDetailsBean;
import com.lch.fulicenter.model.bean.MessageBean;
import com.lch.fulicenter.model.bean.User;
import com.lch.fulicenter.model.net.IModeGoods;
import com.lch.fulicenter.model.net.IModelUser;
import com.lch.fulicenter.model.net.ModelGoods;
import com.lch.fulicenter.model.net.ModelUser;
import com.lch.fulicenter.model.net.OnCompleteListener;
import com.lch.fulicenter.model.utils.CommonUtils;
import com.lch.fulicenter.view.FlowIndicator;
import com.lch.fulicenter.view.MFGT.MFGT;
import com.lch.fulicenter.view.SlideAutoLoopView;
import com.sharesdk.onekeyshare.OnekeyShare;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class GoodsDetailActivity extends AppCompatActivity {
    int goodsId = 0;
    IModeGoods mModel;
    boolean isCollect;
    IModelUser mUserModel;

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
    @BindView(R.id.salv)
    SlideAutoLoopView msalv;
    @BindView(R.id.indicator)
    FlowIndicator mindicator;
    @BindView(R.id.tv_good_price_shop)
    TextView mtvGoodPriceShop;
    @BindView(R.id.iv_good_collect)
    ImageView mivGoodCollect;


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
                } else {
                    MFGT.finish(GoodsDetailActivity.this);
                }
            }

            @Override
            public void onError(String error) {
                Toast.makeText(GoodsDetailActivity.this, error, Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void showGoodsDetail(GoodsDetailsBean goods) {
        mtvGoodName.setText(goods.getGoodsName());
        mtvGoodNameEnglish.setText(goods.getGoodsEnglishName());
        mtvGoodPriceShop.setText(goods.getCurrencyPrice());
        mtvGoodPrice.setText(goods.getShopPrice());

        msalv.startPlayLoop(mindicator, getAlbumUrl(goods), getAlbumCount(goods));
        mwvGoodBrief.loadDataWithBaseURL(null, goods.getGoodsBrief(), I.TEXT_HTML, I.UTF_8, null);
    }

    private int getAlbumCount(GoodsDetailsBean goods) {
        if (goods != null && goods.getPromotePrice() != null && goods.getProperties().length > 0) {
            return goods.getProperties()[0].getAlbums().length;
        }
        return 0;
    }

    private String[] getAlbumUrl(GoodsDetailsBean goods) {
        if (goods != null && goods.getPromotePrice() != null && goods.getProperties().length > 0) {
            AlbumsBean[] albums = goods.getProperties()[0].getAlbums();
            if (albums != null && albums.length > 0) {
                String[] urls = new String[albums.length];
                for (int i = 0; i < albums.length; i++) {
                    urls[i] = albums[i].getImgUrl();
                }
                return urls;
            }
        }
        return new String[0];
    }

    @OnClick(R.id.ivBack)
    public void onClick() {
        MFGT.finish(this);
    }

    @OnClick(R.id.iv_good_collect)
    public void setCollectListener() {
        User user = FuLiCenterApplication.getUser();
        if (user != null) {
            setCollect(user);
        } else {
            MFGT.gotoSetting(this);
        }
    }

    private void setCollect(User user) {
        mModel.setCollect(this, goodsId, user.getMuserName(),
                isCollect ? I.ACTION_DELETE_COLLECT : I.ACTION_ADD_COLLECT,
                new OnCompleteListener<MessageBean>() {
                    @Override
                    public void onSuccess(MessageBean result) {
                        if (result != null && result.isSuccess()) {
                            isCollect = !isCollect;
                            setCollectStatus();
                            CommonUtils.showShortToast(result.getMsg());
                            sendBroadcast(new Intent(I.BROADCAST_UPDATA_COLLECT).
                                    putExtra(I.Collect.GOODS_ID, goodsId));
                        }
                    }

                    @Override
                    public void onError(String error) {

                    }
                });
    }

    @Override
    protected void onResume() {
        super.onResume();
        initCollectStatus();
    }

    private void initCollectStatus() {
        User user = FuLiCenterApplication.getUser();
        if (user != null) {
            mModel.isCollect(this, goodsId, user.getMuserName(), new OnCompleteListener<MessageBean>() {
                @Override
                public void onSuccess(MessageBean result) {
                    if (result != null && result.isSuccess()) {
                        isCollect = true;
                    } else {
                        isCollect = false;
                    }
                    setCollectStatus();
                }

                @Override
                public void onError(String error) {
                    isCollect = false;
                    setCollectStatus();
                }
            });
        }
    }

    private void setCollectStatus() {
        if (isCollect) {
            mivGoodCollect.setImageResource(R.mipmap.bg_collect_out);
        } else {
            mivGoodCollect.setImageResource(R.mipmap.bg_collect_in);
        }
    }


    @OnClick(R.id.iv_good_cart)
    public void addCart() {
        User user = FuLiCenterApplication.getUser();
        mUserModel = new ModelUser();
        mUserModel.updateCart(this, I.ACTION_CART_ADD, user.getMuserName(), goodsId, 1, 0, new OnCompleteListener<MessageBean>() {
            @Override
            public void onSuccess(MessageBean result) {
                if (result != null && result.isSuccess()) {
                    CommonUtils.showShortToast(R.string.add_goods_success);
                }
            }

            @Override
            public void onError(String error) {

            }
        });
    }

    @OnClick(R.id.iv_good_share)
    public void onShare() {
        showShare();
    }

    // 分享方法
    private void showShare() {
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();
        // title标题，印象笔记、邮箱、信息、微信、人人网、QQ和QQ空间使用
        oks.setTitle("标题");
        // titleUrl是标题的网络链接，仅在Linked-in,QQ和QQ空间使用
        oks.setTitleUrl("http://sharesdk.cn");
        // text是分享文本，所有平台都需要这个字段
        oks.setText("我是分享文本");
        //分享网络图片，新浪微博分享网络图片需要通过审核后申请高级写入接口，否则请注释掉测试新浪微博
        oks.setImageUrl("http://f1.sharesdk.cn/imgs/2014/02/26/owWpLZo_638x960.jpg");
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl("http://sharesdk.cn");
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("我是测试评论文本");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite("ShareSDK");
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl("http://sharesdk.cn");

        // 启动分享GUI
        oks.show(this);
    }
}
