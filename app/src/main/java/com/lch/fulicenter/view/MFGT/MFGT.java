package com.lch.fulicenter.view.MFGT;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;

import com.lch.fulicenter.R;
import com.lch.fulicenter.application.I;
import com.lch.fulicenter.controller.activity.BoutiqueChildActivity;
import com.lch.fulicenter.controller.activity.CategoryChildActivity;
import com.lch.fulicenter.controller.activity.GoodsDetailActivity;
import com.lch.fulicenter.controller.activity.LoginActivity;
import com.lch.fulicenter.controller.activity.RegisterActivity;
import com.lch.fulicenter.controller.activity.SettingsActivity;
import com.lch.fulicenter.controller.activity.UpdateNickActivity;
import com.lch.fulicenter.model.bean.BoutiqueBean;
import com.lch.fulicenter.model.bean.CategoryChildBean;

import java.util.ArrayList;

/**
 * Created by LCH on 2017/1/10.
 */

public class MFGT {
    public static void finish(Activity activity) {
        activity.finish();
        activity.overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
    }

    public static void startActivity(Activity context, Class<?> clz) {
        context.startActivity(new Intent(context, clz));
        context.overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
    }

    public static void startActivity(Activity context, Intent intent) {
        context.startActivity(intent);
        context.overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
    }

    public static void gotoBoutiqueChild(Context context, BoutiqueBean boutiqueBean) {
        Intent intent = new Intent(context, BoutiqueChildActivity.class);
        intent.putExtra(I.NewAndBoutiqueGoods.CAT_ID, boutiqueBean.getId());
        intent.putExtra(I.Boutique.TITLE, boutiqueBean.getTitle());
        startActivity((Activity) context, intent);
    }

    public static void gotoGoodsDetail(Context context, int goodsId) {
        Intent intent = new Intent(context, GoodsDetailActivity.class);
        intent.putExtra(I.GoodsDetails.KEY_GOODS_ID, goodsId);
        startActivity((Activity) context, intent);
    }

    public static void gotoCategoryChild(Context context, int catId, String groupName, ArrayList<CategoryChildBean> list) {
        Intent intent = new Intent(context, CategoryChildActivity.class);
        intent.putExtra(I.NewAndBoutiqueGoods.CAT_ID, catId);
        intent.putExtra(I.CategoryGroup.NAME, groupName);
        intent.putExtra(I.CategoryChild.DATA, list);
        startActivity((Activity) context, intent);
    }

    public static void gotoLogin(Activity context) {
        context.startActivityForResult(new Intent(context, LoginActivity.class), I.REQUEST_CODE_LOGIN);
    }

    public static void gotoRegister(LoginActivity registerActivity) {
        startActivity(registerActivity, RegisterActivity.class);
    }

    public static void gotoSetting(FragmentActivity activity) {
        startActivity(activity, SettingsActivity.class);
    }

    public static void gotoUpDateNick(Activity activity) {
        activity.startActivityForResult(new Intent(activity, UpdateNickActivity.class), I.REQUEST_CODE_NICK);
    }
}
