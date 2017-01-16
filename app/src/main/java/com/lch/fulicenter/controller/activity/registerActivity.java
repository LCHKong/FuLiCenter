package com.lch.fulicenter.controller.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.lch.fulicenter.R;
import com.lch.fulicenter.view.MFGT.MFGT;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.ivBack)
    public void onClick() {
        MFGT.finish(this);
    }
}
