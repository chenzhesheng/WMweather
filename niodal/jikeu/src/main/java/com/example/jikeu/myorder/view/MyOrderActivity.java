package com.example.jikeu.myorder.view;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.example.jikeu.R;
import com.example.jikeu.base.utils.HeadTitleUtil;
import com.example.jikeu.myorder.view.Fragment.AllOrderFragment;
import com.example.jikeu.myorder.view.Fragment.ExtiFundFragment;
import com.example.jikeu.myorder.view.Fragment.ObligationFragment;
import com.example.jikeu.myorder.view.Fragment.WaitAssessFragment;
import com.example.jikeu.myorder.view.Fragment.WaitTakeFragment;
import com.example.jikeu.myorder.view.adapter.OrderAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ChenZheSheng on 2016/8/14.
 */
public class MyOrderActivity extends FragmentActivity implements ViewPager.OnPageChangeListener {

    @BindView(R.id.rbAllOrder)
    TextView mRbAllOrder;
    @BindView(R.id.rbObligation)
    TextView mRbObligation;
    @BindView(R.id.rbWaitTake)
    TextView mRbWaitTake;
    @BindView(R.id.rbWaitAssess)
    TextView mRbWaitAssess;
    @BindView(R.id.rbExtiFund)
    TextView mRbExtiFund;
    private OrderAdapter mOrderAdapter;
    private List<Fragment> mFragments;
    @BindView(R.id.vpOrder)
    ViewPager mVpOrder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_order);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        HeadTitleUtil.setTextHead(this, "我的订单");
        mFragments = new ArrayList<>();
        mFragments.add(new AllOrderFragment());
        mFragments.add(new ObligationFragment());
        mFragments.add(new WaitTakeFragment());
        mFragments.add(new WaitAssessFragment());
        mFragments.add(new ExtiFundFragment());
        mOrderAdapter = new OrderAdapter(getSupportFragmentManager(), mFragments);
        mVpOrder.setAdapter(mOrderAdapter);
        mVpOrder.addOnPageChangeListener(this);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        switch (position) {
            case 0:
                mRbAllOrder.setTextColor(Color.parseColor("#AA000000"));
                mRbObligation.setTextColor(Color.parseColor("#b8b8b8"));
                mRbWaitTake.setTextColor(Color.parseColor("#b8b8b8"));
                mRbWaitAssess.setTextColor(Color.parseColor("#b8b8b8"));
                mRbExtiFund.setTextColor(Color.parseColor("#b8b8b8"));
                break;
            case 1:
                mRbAllOrder.setTextColor(Color.parseColor("#b8b8b8"));
                mRbObligation.setTextColor(Color.parseColor("#AA000000"));
                mRbWaitTake.setTextColor(Color.parseColor("#b8b8b8"));
                mRbWaitAssess.setTextColor(Color.parseColor("#b8b8b8"));
                mRbExtiFund.setTextColor(Color.parseColor("#b8b8b8"));
                break;
            case 2:
                mRbAllOrder.setTextColor(Color.parseColor("#b8b8b8"));
                mRbObligation.setTextColor(Color.parseColor("#b8b8b8"));
                mRbWaitTake.setTextColor(Color.parseColor("#AA000000"));
                mRbWaitAssess.setTextColor(Color.parseColor("#b8b8b8"));
                mRbExtiFund.setTextColor(Color.parseColor("#b8b8b8"));
                break;
            case 3:
                mRbAllOrder.setTextColor(Color.parseColor("#b8b8b8"));
                mRbObligation.setTextColor(Color.parseColor("#b8b8b8"));
                mRbWaitTake.setTextColor(Color.parseColor("#b8b8b8"));
                mRbWaitAssess.setTextColor(Color.parseColor("#AA000000"));
                mRbExtiFund.setTextColor(Color.parseColor("#b8b8b8"));
                break;
            case 4:
                mRbAllOrder.setTextColor(Color.parseColor("#b8b8b8"));
                mRbObligation.setTextColor(Color.parseColor("#b8b8b8"));
                mRbWaitTake.setTextColor(Color.parseColor("#b8b8b8"));
                mRbWaitAssess.setTextColor(Color.parseColor("#b8b8b8"));
                mRbExtiFund.setTextColor(Color.parseColor("#AA000000"));
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @OnClick({R.id.rbAllOrder, R.id.rbObligation, R.id.rbWaitTake, R.id.rbWaitAssess, R.id.rbExtiFund})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rbAllOrder:
                mVpOrder.setCurrentItem(0);
                break;
            case R.id.rbObligation:
                mVpOrder.setCurrentItem(1);
                break;
            case R.id.rbWaitTake:
                mVpOrder.setCurrentItem(2);
                break;
            case R.id.rbWaitAssess:
                mVpOrder.setCurrentItem(3);
                break;
            case R.id.rbExtiFund:
                mVpOrder.setCurrentItem(4);
                break;
        }
    }
}
