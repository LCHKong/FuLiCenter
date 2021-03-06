package com.lch.fulicenter.controller.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.lch.fulicenter.R;
import com.lch.fulicenter.application.FuLiCenterApplication;
import com.lch.fulicenter.application.I;
import com.lch.fulicenter.controller.adapter.CollectsAdapter;
import com.lch.fulicenter.model.bean.CollectBean;
import com.lch.fulicenter.model.bean.User;
import com.lch.fulicenter.model.net.IModelUser;
import com.lch.fulicenter.model.net.ModelUser;
import com.lch.fulicenter.model.net.OnCompleteListener;
import com.lch.fulicenter.model.utils.ConvertUtils;
import com.lch.fulicenter.model.utils.SpaceItemDecoration;
import com.lch.fulicenter.view.MFGT.MFGT;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CollectActivity extends AppCompatActivity {

    GridLayoutManager gm;
    CollectsAdapter mAdapter;
    ArrayList<CollectBean> mList = new ArrayList<>();
    IModelUser model;
    int pageId = 1;
    User user = null;
    UpdateCollectReceiver mReceiver;

    @BindView(R.id.tvRefresh)
    TextView mTvRefresh;
    @BindView(R.id.rv)
    RecyclerView mRv;
    @BindView(R.id.srl)
    SwipeRefreshLayout mSrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collect);
        ButterKnife.bind(this);
        user = FuLiCenterApplication.getUser();
        mReceiver = new UpdateCollectReceiver();
        if (user == null) {
            finish();
        } else {
            initView();
            model = new ModelUser();
            initData();
            setListener();
            setReceiverListener();
        }
    }

    private void setReceiverListener() {
        IntentFilter filter = new IntentFilter(I.BROADCAST_UPDATA_COLLECT);
        registerReceiver(mReceiver, filter);
    }

    private void setListener() {
        setPullDownListener();
        setPullUpListener();
    }

    private void setPullUpListener() {
        mRv.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                final int lastPosition = gm.findLastVisibleItemPosition();


                if (newState == RecyclerView.SCROLL_STATE_IDLE
                        && mAdapter.isMore()
                        && lastPosition == mAdapter.getItemCount() - 1) {
                    pageId++;
                    downloadList(I.ACTION_PULL_UP, pageId);
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                // 让第一页不刷新
                int fist = gm.findFirstCompletelyVisibleItemPosition();
                mSrl.setEnabled(fist == 0);
            }
        });
    }

    private void setPullDownListener() {
        mSrl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mSrl.setRefreshing(true);
                mTvRefresh.setVisibility(View.VISIBLE);
                pageId = 1;
                downloadList(I.ACTION_PULL_DOWN, pageId);
            }
        });
    }

    private void initData() {
        pageId = 1;
        downloadList(I.ACTION_DOWNLOAD, pageId);
    }

    private void downloadList(final int action, int PageId) {

        model.downloadCollects(this, user.getMuserName(), pageId, new OnCompleteListener<CollectBean[]>() {
            @Override
            public void onSuccess(CollectBean[] result) {
                mSrl.setRefreshing(false);
                mTvRefresh.setVisibility(View.GONE);
                mAdapter.setMore(true);
                if (result != null && result.length >= 0) {
                    ArrayList<CollectBean> list = ConvertUtils.array2List(result);
                    if (action == I.ACTION_DOWNLOAD || action == I.ACTION_PULL_DOWN) {
                        mAdapter.initData(list);
                    } else {
                        mAdapter.addData(list);
                    }
                    if (list.size() < I.PAGE_SIZE_DEFAULT) {
                        mAdapter.setMore(false);
                    }
                } else {
                    mAdapter.setMore(false);
                }
            }

            @Override
            public void onError(String error) {

            }
        });
    }

    private void initView() {
        mSrl.setColorSchemeColors(
                getResources().getColor(R.color.google_green),
                getResources().getColor(R.color.google_red),
                getResources().getColor(R.color.google_yellow),
                getResources().getColor(R.color.google_blue)
        );
        gm = new GridLayoutManager(this, I.COLUM_NUM);

        // 使页脚居中
        gm.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (position == mAdapter.getItemCount() - 1) {
                    return 2;
                }
                return 1;
            }
        });

        mRv.addItemDecoration(new SpaceItemDecoration(10));
        mRv.setLayoutManager(gm);
        mRv.setHasFixedSize(true);
        mAdapter = new CollectsAdapter(this, mList);
        mRv.setAdapter(mAdapter);
    }

    @OnClick(R.id.ivBack)
    public void onClick() {
        MFGT.finish(this);
    }

    class UpdateCollectReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            int goodsId = intent.getIntExtra(I.Collect.GOODS_ID, 0);
            mAdapter.removeItem(goodsId);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mReceiver != null) {
            unregisterReceiver(mReceiver);
        }
    }
}
