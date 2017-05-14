package com.example.jikeu.mycollect.view;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.example.jikeu.R;
import com.example.jikeu.base.utils.HeadTitleUtil;
import com.example.jikeu.mycollect.view.adapter.CollectAdapter;
import com.example.jikeu.mycollect.view.fragment.ContentFragment;
import com.example.jikeu.mycollect.view.fragment.StoreFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ChenZheSheng on 2016/8/15.
 */
public class MyCollectActivity extends FragmentActivity implements ViewPager.OnPageChangeListener{
    private CollectAdapter mCollectAdapter;
    @BindView(R.id.tvCollectStore)
    TextView mTvCollectStore;
    @BindView(R.id.tvCollectContent)
    TextView mTvCollectContent;
    @BindView(R.id.vpCollect)
    ViewPager mVpCollect;
    private List<Fragment> fragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_collect);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        HeadTitleUtil.setTextHead(this,"我的收藏");
        fragments = new ArrayList<>();
        fragments.add(new StoreFragment());
        fragments.add(new ContentFragment());
        mCollectAdapter = new CollectAdapter(getSupportFragmentManager(),fragments);
        mVpCollect.setAdapter(mCollectAdapter);
        mVpCollect.addOnPageChangeListener(this);
    }

    @OnClick({R.id.tvCollectStore, R.id.tvCollectContent, R.id.vpCollect})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tvCollectStore:
                mVpCollect.setCurrentItem(0);
                break;
            case R.id.tvCollectContent:
                mVpCollect.setCurrentItem(1);
                break;
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        switch (position){
            case 0:
                mTvCollectStore.setTextColor(Color.parseColor("#AA000000"));
                mTvCollectContent.setTextColor(Color.parseColor("#b8b8b8"));
                break;
            case 1:
                mTvCollectStore.setTextColor(Color.parseColor("#b8b8b8"));
                mTvCollectContent.setTextColor(Color.parseColor("#AA000000"));
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
