package com.lch.fulicenter.controller.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.lch.fulicenter.R;
import com.lch.fulicenter.application.I;
import com.lch.fulicenter.view.MFGT.MFGT;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OrderActivity extends AppCompatActivity {
    int payPrice;
    @BindView(R.id.tv_count)
    TextView mtvCount;
    @BindView(R.id.etReceiverName)
    EditText metReceiverName;
    @BindView(R.id.etPhone)
    EditText metPhone;
    @BindView(R.id.spinArea)
    Spinner mspinArea;
    @BindView(R.id.etAddress)
    EditText metAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        ButterKnife.bind(this);
        payPrice = getIntent().getIntExtra(I.Cart.PAY_PRICE, 0);
        setView();
    }

    private void setView() {
        mtvCount.setText("合计：￥" + payPrice);
    }

    @OnClick(R.id.ivBack)
    public void onClick() {
        MFGT.finish(this);
    }

    @OnClick(R.id.btnBuy)
    public void checkOrder() {
        String receiveName = metReceiverName.getText().toString();
        if (TextUtils.isEmpty(receiveName)) {
            metReceiverName.setError("收货人姓名不能为空");
            metReceiverName.requestFocus();
            return;
        }
        String phone = metPhone.getText().toString();
        if (TextUtils.isEmpty(phone)) {
            metPhone.setError("手机号码不能为空");
            metPhone.requestFocus();
            return;
        }
        if (!phone.matches("[\\d]{11}")) {
            metPhone.setError("手机号码格式错误");
            metPhone.requestFocus();
            return;
        }
        String area = mspinArea.getSelectedItem().toString();
        if (TextUtils.isEmpty(area)) {
            Toast.makeText(this, "收货地区不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        String address = metAddress.getText().toString();
        if (TextUtils.isEmpty(address)) {
            metAddress.setError("街道地址不能为空");
            metAddress.requestFocus();
            return;
        }
    }
}
