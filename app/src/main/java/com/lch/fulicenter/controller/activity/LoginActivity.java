package com.lch.fulicenter.controller.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.lch.fulicenter.R;
import com.lch.fulicenter.application.FuLiCenterApplication;
import com.lch.fulicenter.application.I;
import com.lch.fulicenter.model.bean.Result;
import com.lch.fulicenter.model.bean.User;
import com.lch.fulicenter.model.net.IModelUser;
import com.lch.fulicenter.model.net.ModelUser;
import com.lch.fulicenter.model.net.OnCompleteListener;
import com.lch.fulicenter.model.utils.CommonUtils;
import com.lch.fulicenter.model.utils.ResultUtils;
import com.lch.fulicenter.model.utils.SharePrefrenceUtils;
import com.lch.fulicenter.view.MFGT.MFGT;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {
    IModelUser mModel;

    @BindView(R.id.etUserName)
    EditText metUserName;
    @BindView(R.id.etPassword)
    EditText metPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.ivBack, R.id.btnLogin, R.id.btnRegister})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivBack:
                MFGT.finish(this);
                break;
            case R.id.btnLogin:
                checkInput();
                break;
            case R.id.btnRegister:
                MFGT.gotoRegister(this);
                break;
        }
    }

    private void checkInput() {
        String userName = metUserName.getText().toString().trim();
        String passWord = metPassword.getText().toString().trim();
        if (TextUtils.isEmpty(userName)) {
            metUserName.setError(getString(R.string.user_name_connot_be_empty));
            metUserName.requestFocus();
        } else if (TextUtils.isEmpty(passWord)) {
            metPassword.setError(getString(R.string.password_connot_be_empty));
            metPassword.requestFocus();
        } else {
            login(userName, passWord);
        }

    }

    private void login(String userName, String passWord) {
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage(getString(R.string.logining));
        mModel = new ModelUser();
        mModel.login(this, userName, passWord, new OnCompleteListener<String>() {
            @Override
            public void onSuccess(String s) {
                if (s != null) {
                    Result result = ResultUtils.getResultFromJson(s, User.class);
                    if (result != null) {
                        if (result.isRetMsg()) {
                            User user = (User) result.getRetData();
                            SharePrefrenceUtils.getInstance(LoginActivity.this).saveUser(user.getMuserName());
                            FuLiCenterApplication.setUser(user);
                            MFGT.finish(LoginActivity.this);
                        } else {
                            if (result.getRetCode() == I.MSG_LOGIN_UNKNOW_USER) {
                                CommonUtils.showLongToast(getString(R.string.login_fail_unknow_user));
                            }
                            if (result.getRetCode() == I.MSG_LOGIN_ERROR_PASSWORD) {
                                CommonUtils.showLongToast(getString(R.string.login_fail_error_password));
                            }
                        }
                    } else {
                        CommonUtils.showLongToast(getString(R.string.login_fail));
                    }
                } else {
                    CommonUtils.showLongToast(getString(R.string.login_fail));
                }
                dialog.dismiss();
            }

            @Override
            public void onError(String error) {
                CommonUtils.showLongToast(getString(R.string.login_fail));
                dialog.dismiss();
            }
        });
    }
}
