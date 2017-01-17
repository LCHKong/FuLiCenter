package com.lch.fulicenter.controller.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lch.fulicenter.R;
import com.lch.fulicenter.application.FuLiCenterApplication;
import com.lch.fulicenter.model.bean.User;
import com.lch.fulicenter.model.utils.ImageLoader;
import com.lch.fulicenter.model.utils.SharePrefrenceUtils;
import com.lch.fulicenter.view.MFGT.MFGT;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SettingsActivity extends AppCompatActivity {

    @BindView(R.id.tvUserName)
    TextView mtvUserName;
    @BindView(R.id.tvUserNick)
    TextView mtvUserNick;
    @BindView(R.id.ivUserAvatar)
    ImageView mivUserAvatar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        ButterKnife.bind(this);
        initData();
    }

    private void initData() {
        User user = FuLiCenterApplication.getUser();
        if (user != null) {
            loadUserInfo(user);
        } else {
            MFGT.gotoLogin(this);
        }
    }

    private void loadUserInfo(User user) {
        ImageLoader.setAvatar(ImageLoader.getAvatarUrl(user), this, mivUserAvatar);
        mtvUserName.setText(user.getMuserName());
        mtvUserNick.setText(user.getMuserNick());
    }


    // 修改昵称
    @OnClick(R.id.layout_user_nickname)
    public void onClick() {
        String nick = mtvUserNick.getText().toString().trim();
        if (TextUtils.isEmpty(nick)) {

        }
    }

    @OnClick({R.id.ivBack, R.id.btBack})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivBack:
                MFGT.finish(this);
                break;
            case R.id.btBack:
                FuLiCenterApplication.setUser(null);
                SharePrefrenceUtils.getInstance(this).removeUser();
                MFGT.gotoLogin(this);
                MFGT.finish(this);
                break;
        }
    }
}
