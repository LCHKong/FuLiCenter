package com.lch.fulicenter.controller.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lch.fulicenter.R;
import com.lch.fulicenter.application.FuLiCenterApplication;
import com.lch.fulicenter.application.I;
import com.lch.fulicenter.model.bean.Result;
import com.lch.fulicenter.model.bean.User;
import com.lch.fulicenter.model.net.IModelUser;
import com.lch.fulicenter.model.net.ModelUser;
import com.lch.fulicenter.model.net.OnCompleteListener;
import com.lch.fulicenter.model.utils.CommonUtils;
import com.lch.fulicenter.model.utils.ImageLoader;
import com.lch.fulicenter.model.utils.L;
import com.lch.fulicenter.model.utils.OnSetAvatarListener;
import com.lch.fulicenter.model.utils.ResultUtils;
import com.lch.fulicenter.model.utils.SharePrefrenceUtils;
import com.lch.fulicenter.view.MFGT.MFGT;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SettingsActivity extends AppCompatActivity {
    OnSetAvatarListener mOnSetAvatarListener;
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


    @OnClick({R.id.ivBack, R.id.btBack})
    public void onClick(View view) {
        switch (view.getId()) {
            // 返回
            case R.id.ivBack:
                MFGT.finish(this);
                break;
            // 退出
            case R.id.btBack:
                FuLiCenterApplication.setUser(null);
                SharePrefrenceUtils.getInstance(this).removeUser();
                MFGT.gotoLogin(this);
                MFGT.finish(this);
                break;
        }
    }

    @OnClick({R.id.user_head_avatar, R.id.layout_user_name, R.id.layout_user_nickname})
    public void update(View view) {
        switch (view.getId()) {
            case R.id.user_head_avatar:
                mOnSetAvatarListener = new OnSetAvatarListener(this,
                        R.id.ivUserAvatar,
                        FuLiCenterApplication.getUser().getMuserName(),
                        I.AVATAR_TYPE_USER_PATH);
                break;
            // 用户名称
            case R.id.layout_user_name:
                CommonUtils.showLongToast(R.string.username_connot_be_modify);
                break;
            // 更新用户昵称
            case R.id.layout_user_nickname:
                MFGT.gotoUpDateNick(this);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            return;
        }
        if (requestCode == I.REQUEST_CODE_NICK) {
            mtvUserNick.setText(FuLiCenterApplication.getUser().getMuserNick());
        } else {
            mOnSetAvatarListener.setAvatar(requestCode, data, mivUserAvatar);
        }
        if (requestCode == OnSetAvatarListener.REQUEST_CROP_PHOTO) {
            uploadAvatar();
        }
    }

    private void uploadAvatar() {
        IModelUser mModel = new ModelUser();
        final User user = FuLiCenterApplication.getUser();
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage(getString(R.string.update_user_avatar));
        dialog.show();
        File file = null;
        file = new File(String.valueOf(OnSetAvatarListener.getAvatarFile(this,
                I.AVATAR_TYPE_USER_PATH + "/" + user.getMuserName() + user.getMavatarSuffix())));
        L.e("file：" + file.exists());
        L.e("file：" + file.getAbsolutePath());
        mModel.uploadAvatar(this, user.getMuserName(), file, new OnCompleteListener<String>() {
            @Override
            public void onSuccess(String s) {
                int msg = R.string.update_user_avatar_fail;
                if (s != null) {
                    Result result = ResultUtils.getResultFromJson(s, User.class);
                    if (result != null) {
                        if (result.isRetMsg()) {
                            msg = R.string.update_user_avatar_success;
                        }
                    }
                }
                CommonUtils.showLongToast(msg);
                dialog.dismiss();
            }

            @Override
            public void onError(String error) {
                CommonUtils.showLongToast(R.string.update_user_avatar_fail);
                dialog.dismiss();
            }
        });
    }
}
