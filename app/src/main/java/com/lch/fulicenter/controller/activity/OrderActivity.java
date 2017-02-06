package com.lch.fulicenter.controller.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

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
}
