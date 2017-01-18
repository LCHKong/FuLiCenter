package com.lch.fulicenter.controller.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lch.fulicenter.R;
import com.lch.fulicenter.application.FuLiCenterApplication;
import com.lch.fulicenter.model.bean.MessageBean;
import com.lch.fulicenter.model.bean.User;
import com.lch.fulicenter.model.net.IModelUser;
import com.lch.fulicenter.model.net.ModelUser;
import com.lch.fulicenter.model.net.OnCompleteListener;
import com.lch.fulicenter.model.utils.ImageLoader;
import com.lch.fulicenter.view.MFGT.MFGT;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class PersonalCenterFragment extends Fragment {
    IModelUser mModel;


    @BindView(R.id.ivUserAvatar)
    ImageView mivUserAvatar;
    @BindView(R.id.tvUserName)
    TextView mtvUserName;
    @BindView(R.id.tvCollectCount)
    TextView mtvCollectCount;

    public PersonalCenterFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_personal_center, container, false);
        ButterKnife.bind(this, layout);
        initData();
        return layout;
    }

    private void initData() {
        User user = FuLiCenterApplication.getUser();
        if (user != null) {
            loadUserInfo(user);
            getCollectCount();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        initData();
    }

    private void loadUserInfo(User user) {
        ImageLoader.setAvatar(ImageLoader.getAvatarUrl(user), getContext(), mivUserAvatar);
        mtvUserName.setText(user.getMuserNick());
        loadCollectCount("0");
    }

    private void getCollectCount() {
        mModel = new ModelUser();
        mModel.collecCount(getContext(), FuLiCenterApplication.getUser().getMuserName(),
                new OnCompleteListener<MessageBean>() {
                    @Override
                    public void onSuccess(MessageBean result) {
                        if (result != null && result.isSuccess()) {
                            loadCollectCount(result.getMsg());
                        } else {
                            loadCollectCount("0");
                        }
                    }

                    @Override
                    public void onError(String error) {
                        loadCollectCount("0");
                    }
                });
    }

    private void loadCollectCount(String count) {
        mtvCollectCount.setText(String.valueOf(count));
    }

    @OnClick({R.id.center_top, R.id.center_user_info})
    public void setting() {
        MFGT.gotoSetting(getActivity());
    }
}
