package com.lch.fulicenter.controller.activity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.lch.fulicenter.R;
import com.lch.fulicenter.application.I;
import com.lch.fulicenter.controller.fragment.NewGoodsFragment;
import com.lch.fulicenter.view.CatFilterButton;
import com.lch.fulicenter.view.MFGT.MFGT;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CategoryChildActivity extends AppCompatActivity {
    NewGoodsFragment mNewGoodsFragment;
    boolean priceAsc = false;
    boolean addTimeAsc = false;

    @BindView(R.id.btnPriceSort)
    Button btnPriceSort;
    @BindView(R.id.btnAddTimeSort)
    Button btnAddTimeSort;
    @BindView(R.id.cat_filter)
    CatFilterButton mCatFilter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_child);
        ButterKnife.bind(this);

        mNewGoodsFragment = new NewGoodsFragment();


        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container1, mNewGoodsFragment)
                .commit();
        String groupName = getIntent().getStringExtra(I.CategoryGroup.NAME);
        mCatFilter.initCatFilterButton(groupName, null);
    }

    @OnClick({R.id.btnPriceSort, R.id.btnAddTimeSort})
    public void onClick(View view) {
        int sortBy = I.SORT_BY_PRICE_ASC;
        Drawable right;
        switch (view.getId()) {
            case R.id.btnPriceSort:
                if (priceAsc) {
                    sortBy = I.SORT_BY_PRICE_ASC;
                    right = getResources().getDrawable(R.mipmap.arrow_order_up);
                } else {
                    sortBy = I.SORT_BY_PRICE_DESC;
                    right = getResources().getDrawable(R.mipmap.arrow_order_down);
                }
                right.setBounds(0, 0, right.getIntrinsicWidth(), right.getIntrinsicHeight());
                btnPriceSort.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, right, null);
                priceAsc = !priceAsc;
                break;
            case R.id.btnAddTimeSort:
                if (addTimeAsc) {
                    sortBy = I.SORT_BY_ADDTIME_ASC;
                    right = getResources().getDrawable(R.mipmap.arrow_order_up);
                } else {
                    sortBy = I.SORT_BY_ADDTIME_DESC;
                    right = getResources().getDrawable(R.mipmap.arrow_order_down);
                }
                right.setBounds(0, 0, right.getIntrinsicWidth(), right.getIntrinsicHeight());
                btnAddTimeSort.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, right, null);
                addTimeAsc = !addTimeAsc;
                break;
        }
        mNewGoodsFragment.sortGoods(sortBy);
    }

    @OnClick(R.id.ivBack)
    public void onClick() {
        MFGT.finish(this);
    }
}
