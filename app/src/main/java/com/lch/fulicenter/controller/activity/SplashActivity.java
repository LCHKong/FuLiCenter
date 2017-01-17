package com.lch.fulicenter.controller.activity;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.lch.fulicenter.R;
import com.lch.fulicenter.application.FuLiCenterApplication;
import com.lch.fulicenter.model.Dao.UserDao;
import com.lch.fulicenter.model.bean.User;
import com.lch.fulicenter.model.utils.L;
import com.lch.fulicenter.model.utils.SharePrefrenceUtils;
import com.lch.fulicenter.view.MFGT.MFGT;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
    }

    @Override
    protected void onStart() {
        super.onStart();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                String userName = SharePrefrenceUtils.getInstance(SplashActivity.this).getUser();
                if (userName != null) {
                    User user = UserDao.getInstance().getUser(userName);
                    L.e("main", "user=”+user");
                    if (user != null) {
                        FuLiCenterApplication.setUser(user);
                    }
                }
                MFGT.startActivity(SplashActivity.this, MainActivity.class);
                MFGT.finish(SplashActivity.this);
            }
        }, 2000);
    }
}
