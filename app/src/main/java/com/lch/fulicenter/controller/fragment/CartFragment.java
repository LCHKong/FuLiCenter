package com.lch.fulicenter.controller.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lch.fulicenter.R;
import com.lch.fulicenter.application.FuLiCenterApplication;
import com.lch.fulicenter.application.I;
import com.lch.fulicenter.controller.adapter.CartAdapter;
import com.lch.fulicenter.model.bean.CartBean;
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

/**
 * A simple {@link Fragment} subclass.
 */
public class CartFragment extends Fragment {

    @BindView(R.id.tvRefresh)
    TextView mTvRefresh;
    @BindView(R.id.rv)
    RecyclerView mRv;
    @BindView(R.id.srl)
    SwipeRefreshLayout mSrl;
    @BindView(R.id.tv_nothing)
    TextView mtvNothing;

    public CartFragment() {
        // Required empty public constructor
    }

    LinearLayoutManager lm;
    CartAdapter mAdapter;
    ArrayList<CartBean> mList = new ArrayList<>();
    IModelUser model;
    User user;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cart, container, false);
        ButterKnife.bind(this, view);
        initView();
        model = new ModelUser();
        initData();
        setListener();
        return view;
    }

    private void setListener() {
        setPullDownListener();
    }


    private void setPullDownListener() {
        mSrl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mSrl.setRefreshing(true);
                mTvRefresh.setVisibility(View.VISIBLE);
                downloadList(I.ACTION_PULL_DOWN);
            }
        });
    }

    private void initData() {
        downloadList(I.ACTION_DOWNLOAD);
    }

    private void downloadList(final int action) {
        user = FuLiCenterApplication.getUser();
        if (user != null) {
            model.getCart(getContext(), user.getMuserName(), new OnCompleteListener<CartBean[]>() {
                @Override
                public void onSuccess(CartBean[] result) {
                    mSrl.setRefreshing(false);

                    mTvRefresh.setVisibility(View.GONE);
                    mSrl.setVisibility(View.VISIBLE);
                    mtvNothing.setVisibility(View.GONE);

                    if (result != null && result.length > 0) {
                        ArrayList<CartBean> list = ConvertUtils.array2List(result);
                        if (action == I.ACTION_DOWNLOAD || action == I.ACTION_PULL_DOWN) {
                            mAdapter.initData(list);
                        }
                    } else {
                        mSrl.setVisibility(View.GONE);
                        mtvNothing.setVisibility(View.VISIBLE);
                    }
                }
                @Override
                public void onError(String error) {

                }
            });
        } else {
            MFGT.gotoLogin(getActivity());
        }
    }

    private void initView() {
        mSrl.setColorSchemeColors(
                getResources().getColor(R.color.google_green),
                getResources().getColor(R.color.google_red),
                getResources().getColor(R.color.google_yellow),
                getResources().getColor(R.color.google_blue)
        );
        lm = new LinearLayoutManager(getContext());
        mRv.addItemDecoration(new SpaceItemDecoration(10));
        mRv.setLayoutManager(lm);
        mRv.setHasFixedSize(true);
        mAdapter = new CartAdapter(getContext(), mList);
        mRv.setAdapter(mAdapter);

        mSrl.setVisibility(View.GONE);
        mtvNothing.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.tv_nothing)
    public void onClick() {
        initData();
    }
}
