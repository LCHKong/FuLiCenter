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
import android.widget.Toast;

import com.lch.fulicenter.R;
import com.lch.fulicenter.application.I;
import com.lch.fulicenter.controller.adapter.BoutiqueAdapter;
import com.lch.fulicenter.model.bean.BoutiqueBean;
import com.lch.fulicenter.model.net.IModelBoutique;
import com.lch.fulicenter.model.net.ModeBoutique;
import com.lch.fulicenter.model.net.OnCompleteListener;
import com.lch.fulicenter.model.utils.ConvertUtils;
import com.lch.fulicenter.model.utils.SpaceItemDecoration;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class BoutiqueFragment extends Fragment {


    @BindView(R.id.tvRefresh)
    TextView mTvRefresh;
    @BindView(R.id.rv)
    RecyclerView mRv;
    @BindView(R.id.srl)
    SwipeRefreshLayout mSrl;
    @BindView(R.id.tvMore)
    TextView mtvMore;

    public BoutiqueFragment() {
        // Required empty public constructor
    }

    LinearLayoutManager lm;
    BoutiqueAdapter mAdapter;
    ArrayList<BoutiqueBean> mList = new ArrayList<>();
    IModelBoutique model;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_boutique, container, false);
        ButterKnife.bind(this, view);
        initView();
        model = new ModeBoutique();
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
        model.downData(getContext(), new OnCompleteListener<BoutiqueBean[]>() {
            @Override
            public void onSuccess(BoutiqueBean[] result) {
                mSrl.setRefreshing(false);

                mTvRefresh.setVisibility(View.GONE);
                mSrl.setVisibility(View.VISIBLE);
                mtvMore.setVisibility(View.GONE);

                if (result != null && result.length > 0) {
                    ArrayList<BoutiqueBean> list = ConvertUtils.array2List(result);
                    if (action == I.ACTION_DOWNLOAD || action == I.ACTION_PULL_DOWN) {
                        mAdapter.initData(list);
                    }
                } else {
                    mSrl.setVisibility(View.GONE);
                    mtvMore.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onError(String error) {
                Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
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
        lm = new LinearLayoutManager(getContext());
        mRv.addItemDecoration(new SpaceItemDecoration(10));
        mRv.setLayoutManager(lm);
        mRv.setHasFixedSize(true);
        mAdapter = new BoutiqueAdapter(getContext(), mList);
        mRv.setAdapter(mAdapter);

        mSrl.setVisibility(View.GONE);
        mtvMore.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.tvMore)
    public void onClick() {
        initData();
    }
}
