package com.example.jikeu;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.jikeu.base.utils.HeadTitleUtil;
import com.example.jikeu.base.utils.IntentUtils;
import com.example.jikeu.base.utils.SharedPreferencesUtils;
import com.example.jikeu.mycollect.view.MyCollectActivity;
import com.example.jikeu.mycoupon.view.MyCouponActivity;
import com.example.jikeu.mymessage.view.MyMessageActivity;
import com.example.jikeu.myorder.view.MyOrderActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends Activity {

    @BindView(R.id.tv_main_layout_name)
    TextView mtvName;
    @BindView(R.id.ivUserImage)
    ImageView ivImage;
    @BindView(R.id.ll_activity_my_message)
    LinearLayout mLlActivityMyMessage;
    @BindView(R.id.ll_activity_my_order)
    LinearLayout mLlActivityMyOrder;
    @BindView(R.id.ll_activity_my_coupon)
    LinearLayout mLlActivityMyCoupon;
    @BindView(R.id.ll_activity_my_collect)
    LinearLayout mLlActivityMyCollect;
    private String path;
    private String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initView();
    }

    public void initView() {
        HeadTitleUtil.setTextHead(this, "我的");
        path = (String) SharedPreferencesUtils.getParam(this, "userimage", "");
        if (!"".equals(path) && path != null) {
            ivImage.setImageBitmap(BitmapFactory.decodeFile(path));
        }
        name = (String) SharedPreferencesUtils.getParam(this, "username", "");
        if (!"".equals(name) && name != null) {
            mtvName.setText(name);
        }
    }

    @OnClick({R.id.ll_activity_my_message, R.id.ll_activity_my_order, R.id.ll_activity_my_coupon, R.id.ll_activity_my_collect})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_activity_my_message:
                IntentUtils.startActivity(this, MyMessageActivity.class);
                break;
            case R.id.ll_activity_my_order:
                IntentUtils.startActivity(this, MyOrderActivity.class);
                break;
            case R.id.ll_activity_my_coupon:
                IntentUtils.startActivity(this, MyCouponActivity.class);
                break;
            case R.id.ll_activity_my_collect:
                IntentUtils.startActivity(this, MyCollectActivity.class);
                break;
        }
    }


}
