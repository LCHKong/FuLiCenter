package com.lch.fulicenter.controller.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RadioButton;

import com.lch.fulicenter.R;
import com.lch.fulicenter.application.FuLiCenterApplication;
import com.lch.fulicenter.controller.fragment.BoutiqueFragment;
import com.lch.fulicenter.controller.fragment.CategoryFragment;
import com.lch.fulicenter.controller.fragment.NewGoodsFragment;
import com.lch.fulicenter.controller.fragment.PersonalCenterFragment;
import com.lch.fulicenter.view.MFGT.MFGT;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.layout_new_goods)
    RadioButton layoutNewGoods;
    @BindView(R.id.layout_boutique)
    RadioButton layoutBoutique;
    @BindView(R.id.layout_category)
    RadioButton layoutCategory;
    @BindView(R.id.layout_cart)
    RadioButton layoutCart;
    @BindView(R.id.layout_personal_center)
    RadioButton layoutPersonalCenter;


    int index, currentIndex;
    Fragment mFragment[] = new Fragment[5];
    RadioButton[] rbs = new RadioButton[5];

    NewGoodsFragment mNewGoodsFragment;
    BoutiqueFragment mBoutiqueFargment;
    CategoryFragment mCategoryFragment;
    PersonalCenterFragment mPersonalCenterFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        rbs[0] = layoutNewGoods;
        rbs[1] = layoutBoutique;
        rbs[2] = layoutCategory;
        rbs[3] = layoutCart;
        rbs[4] = layoutPersonalCenter;
        mNewGoodsFragment = new NewGoodsFragment();
        mBoutiqueFargment = new BoutiqueFragment();
        mCategoryFragment = new CategoryFragment();
        mPersonalCenterFragment = new PersonalCenterFragment();
        mFragment[0] = mNewGoodsFragment;
        mFragment[1] = mBoutiqueFargment;
        mFragment[2] = mCategoryFragment;
        mFragment[4] = mPersonalCenterFragment;

        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, mNewGoodsFragment)
                .add(R.id.fragment_container, mBoutiqueFargment)
                .add(R.id.fragment_container, mCategoryFragment)
                .add(R.id.fragment_container, mPersonalCenterFragment)
                .show(mNewGoodsFragment)
                .hide(mBoutiqueFargment)
                .hide(mCategoryFragment)
                .hide(mPersonalCenterFragment)
                .commit();

    }

    public void onCheckedChange(View view) {
        switch (view.getId()) {
            case R.id.layout_new_goods:
                index = 0;
                break;
            case R.id.layout_boutique:
                index = 1;
                break;
            case R.id.layout_category:
                index = 2;
                break;
            case R.id.layout_cart:
                index = 3;
                break;
            case R.id.layout_personal_center:
                if (FuLiCenterApplication.getUser() == null) {
                    MFGT.gotoLogin(this);
                } else {
                    index = 4;
                }
                break;
        }
        setFragment();
        if (index != currentIndex) {
            setRadioStatus();
        }
    }

    private void setFragment() {
        getSupportFragmentManager().beginTransaction().show(mFragment[index])
                .hide(mFragment[currentIndex]).commit();
    }

    private void setRadioStatus() {
        for (int i = 0; i < rbs.length; i++) {
            if (index != i) {
                rbs[i].setChecked(false);
            } else {
                rbs[i].setChecked(true);
            }
        }
        currentIndex = index;
    }
}