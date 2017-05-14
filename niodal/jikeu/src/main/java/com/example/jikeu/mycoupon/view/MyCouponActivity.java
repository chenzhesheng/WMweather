package com.example.jikeu.mycoupon.view;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.jikeu.R;
import com.example.jikeu.base.utils.HeadTitleUtil;
import com.example.jikeu.mycoupon.model.dao.CouponDao;
import com.example.jikeu.mycoupon.view.adapter.NotCouponAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ChenZheSheng on 2016/8/14.
 */
public class MyCouponActivity extends Activity {
    @BindView(R.id.rvCouponNot)
    RecyclerView mRvCouponNot;
    @BindView(R.id.rvCouponYet)
    RecyclerView mRvCouponYet;
    private NotCouponAdapter mNotCouponAdapter;
    private CouponDao mCouponDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_coupon);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        HeadTitleUtil.setTextHead(this,"优惠券");
        mCouponDao = new CouponDao();
        mNotCouponAdapter = new NotCouponAdapter(this,mCouponDao.getCouponList());
        mRvCouponNot.setLayoutManager(new LinearLayoutManager(this));
        mRvCouponNot.setAdapter(mNotCouponAdapter);
        mRvCouponYet.setLayoutManager(new LinearLayoutManager(this));
        mRvCouponYet.setAdapter(mNotCouponAdapter);
    }
}
