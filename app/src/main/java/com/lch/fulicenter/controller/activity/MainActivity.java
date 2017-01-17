package com.lch.fulicenter.controller.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RadioButton;

import com.lch.fulicenter.R;
import com.lch.fulicenter.application.FuLiCenterApplication;
import com.lch.fulicenter.application.I;
import com.lch.fulicenter.controller.fragment.BoutiqueFragment;
import com.lch.fulicenter.controller.fragment.CategoryFragment;
import com.lch.fulicenter.controller.fragment.NewGoodsFragment;
import com.lch.fulicenter.controller.fragment.PersonalCenterFragment;
import com.lch.fulicenter.view.MFGT.MFGT;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    int index, currentIndex;
    RadioButton[] rbs = new RadioButton[5];
    @BindView(R.id.layout_new_goods)
    RadioButton layoutNewGood;
    @BindView(R.id.layout_boutique)
    RadioButton layoutBoutique;
    @BindView(R.id.layout_category)
    RadioButton layoutCategory;
    @BindView(R.id.layout_cart)
    RadioButton layoutCart;
    @BindView(R.id.layout_personal_center)
    RadioButton layoutPersonalCenter;
    Fragment[] mFragments = new Fragment[5];
    NewGoodsFragment mNewGoodsFragment;
    BoutiqueFragment mBoutiqueFragment;
    CategoryFragment mCategoryFragment;
    PersonalCenterFragment mPersonalCenterFragment;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        rbs[0] = layoutNewGood;
        rbs[1] = layoutBoutique;
        rbs[2] = layoutCategory;
        rbs[3] = layoutCart;
        rbs[4] = layoutPersonalCenter;
        mNewGoodsFragment = new NewGoodsFragment();
        mBoutiqueFragment = new BoutiqueFragment();
        mCategoryFragment = new CategoryFragment();
        mPersonalCenterFragment = new PersonalCenterFragment();

        mFragments[0] = mNewGoodsFragment;
        mFragments[1] = mBoutiqueFragment;
        mFragments[2] = mCategoryFragment;
        mFragments[4] = mPersonalCenterFragment;
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, mNewGoodsFragment)
                .add(R.id.fragment_container, mBoutiqueFragment)
                .add(R.id.fragment_container, mCategoryFragment)
                .show(mNewGoodsFragment)
                .hide(mBoutiqueFragment)
                .hide(mCategoryFragment)
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
                    rbs[4].setChecked(false);
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
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.hide(mFragments[currentIndex]);
        if (!mFragments[index].isAdded()) {
            ft.add(R.id.fragment_container, mFragments[index]);
        }
        ft.show(mFragments[index]).commitAllowingStateLoss();
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

    @Override
    protected void onResume() {
        super.onResume();
        if (index == 4 && FuLiCenterApplication.getUser() == null) {
            index = 0;
        }
        setFragment();
        setRadioStatus();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == I.REQUEST_CODE_LOGIN) {
            index = 4;
            setFragment();
            setRadioStatus();
        }
    }
}