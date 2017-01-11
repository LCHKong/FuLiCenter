package com.lch.fulicenter.controller.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.lch.fulicenter.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewGoodsFragment extends Fragment {
    static final int ACTION_DOWNLOAD = 0;//下载首页
    static final int ACTION_PULL_DOWNLOAD = 1;//下拉刷新
    static final int ACTION_PULL_UP = 2;//上拉加载

    @BindView(R.id.imageView)
    ImageView imageView;

    public NewGoodsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_new_goods, container, false);

        ButterKnife.bind(this, view);

        return view;
    }

}
