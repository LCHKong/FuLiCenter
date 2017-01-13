package com.lch.fulicenter.controller.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.lch.fulicenter.R;
import com.lch.fulicenter.application.I;
import com.lch.fulicenter.controller.fragment.NewGoodsFragment;
import com.lch.fulicenter.view.MFGT.MFGT;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CategoryChildActivity extends AppCompatActivity {
    NewGoodsFragment mNewGoodsFragment;
    @BindView(R.id.tv_common_title)
    TextView tvCommonTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_child);
        ButterKnife.bind(this);

        mNewGoodsFragment = new NewGoodsFragment();


        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container1, mNewGoodsFragment)
                .commit();
    }

    @OnClick({R.id.btnPriceSort, R.id.btnAddTimeSort})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnPriceSort:
                mNewGoodsFragment.sortGoods(I.SORT_BY_PRICE_ASC);
                break;
            case R.id.btnAddTimeSort:
                mNewGoodsFragment.sortGoods(I.SORT_BY_ADDTIME_ASC);
                break;
        }
    }

    @OnClick(R.id.ivBack)
    public void onClick() {
        MFGT.finish(this);
    }
}
