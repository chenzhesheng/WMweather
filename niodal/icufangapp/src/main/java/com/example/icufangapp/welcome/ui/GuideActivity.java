package com.example.icufangapp.welcome.ui;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.example.icufangapp.R;
import com.example.icufangapp.base.BaseActivity;
import com.netframe.core.Form;
import com.netframe.view.annotation.ContentViewInject;
import com.netframe.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ChenZheSheng on 2016/7/15.
 */
@ContentViewInject(value = R.layout.activity_guide)
public class GuideActivity extends BaseActivity {
    @ViewInject(value = R.id.vp_guide_activity_viewpager)
    private ViewPager mViewPager;
    private GuideAdapter adapter;
    private List<Fragment> mFragment;

    @Override
    public void requestSuccess(Form form) {

    }

    @Override
    public void requestFail(Form form) {

    }

    @Override
    public void initView() {
        mFragment = new ArrayList<>();
        mFragment.add(new GuideFragmentOne());
        mFragment.add(new GuideFragmentTwo());
        mFragment.add(new GuideFragmentThree());
        adapter = new GuideAdapter(getSupportFragmentManager() ,mFragment);
        mViewPager.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {

    }
}
