package com.lch.fulicenter.view.MFGT;

import android.app.Activity;
import android.content.Intent;

import com.lch.fulicenter.R;

/**
 * Created by LCH on 2017/1/10.
 */

public class MFGT {
    public static void finish(Activity activity) {
        activity.finish();
        activity.overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
    }

    public static void startActivity(Activity context, Class<?> clz) {
        context.overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
        context.startActivity(new Intent(context, clz));
    }
}
