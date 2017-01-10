package com.lch.fulicenter.controller.activity;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.lch.fulicenter.MainActivity;
import com.lch.fulicenter.R;
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
                MFGT.startActivity(SplashActivity.this, MainActivity.class);
            }
        }, 2000);
    }
}
