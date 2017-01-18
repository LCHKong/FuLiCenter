package com.lch.fulicenter.controller.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.lch.fulicenter.R;
import com.lch.fulicenter.application.FuLiCenterApplication;
import com.lch.fulicenter.application.I;
import com.lch.fulicenter.model.Dao.UserDao;
import com.lch.fulicenter.model.bean.Result;
import com.lch.fulicenter.model.bean.User;
import com.lch.fulicenter.model.net.IModelUser;
import com.lch.fulicenter.model.net.ModelUser;
import com.lch.fulicenter.model.net.OnCompleteListener;
import com.lch.fulicenter.model.utils.CommonUtils;
import com.lch.fulicenter.model.utils.ResultUtils;
import com.lch.fulicenter.view.MFGT.MFGT;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UpdateNickActivity extends AppCompatActivity {

    @BindView(R.id.etUpdateUserName)
    EditText metUpdateUserName;

    User user;
    IModelUser mModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_nick);
        ButterKnife.bind(this);
        initData();
    }

    private void initData() {
        user = FuLiCenterApplication.getUser();
        if (user == null) {
            MFGT.finish(this);
        } else {
            metUpdateUserName.setText(user.getMuserNick());
        }
    }

    @OnClick({R.id.ivBack, R.id.btnSave})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivBack:
                MFGT.finish(this);
                break;
            case R.id.btnSave:
                String nick = metUpdateUserName.getText().toString().trim();
                if (TextUtils.isEmpty(nick)) {
                    CommonUtils.showLongToast(R.string.nick_name_connot_be_empty);
                } else if (nick.equals(user.getMuserNick())) {
                    CommonUtils.showLongToast(R.string.update_nick_fail_unmodify);
                } else {
                    updateNick(nick);
                }
                break;
        }
    }

    private void updateNick(String nick) {
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage(getString(R.string.update_user_nick));
        dialog.show();
        mModel = new ModelUser();
        mModel.update(this, user.getMuserName(), nick, new OnCompleteListener<String>() {
            @Override
            public void onSuccess(String s) {
                int msg = R.string.update_fail;
                if (s != null) {
                    Result result = ResultUtils.getResultFromJson(s, User.class);
                    if (result != null) {
                        if (result.isRetMsg()) {
                            msg = R.string.update_user_nick_success;
                            User user = (User) result.getRetData();
                            saveNewUser(user);
                            setResult(RESULT_OK);
                            finish();
                        } else {
                            if (result.getRetCode() == I.MSG_USER_SAME_NICK
                                    || result.getRetCode() == I.MSG_USER_UPDATE_NICK_FAIL) {
                                msg = R.string.update_nick_fail_unmodify;
                            }
                        }
                    }
                }
                CommonUtils.showLongToast(msg);
                dialog.dismiss();
            }

            @Override
            public void onError(String error) {
                CommonUtils.showLongToast(R.string.update_fail);
                dialog.dismiss();
            }
        });
    }

    private void saveNewUser(User user) {
        FuLiCenterApplication.setUser(user);
        UserDao.getInstance().savaUser(user);
    }
}
