package com.example.icufangapp.welcome.ui;

import android.view.View;

import com.example.icufangapp.R;
import com.example.icufangapp.base.BaseActivity;
import com.example.icufangapp.welcome.presenter.WelcomePreserter;
import com.netframe.core.Form;
import com.netframe.view.annotation.ContentViewInject;

/**
 * Created by ChenZheSheng on 2016/7/13.
 */
@ContentViewInject(value = R.layout.activity_welcome)
public class WelcomeActivity extends BaseActivity {
    private WelcomePreserter mWelcomePreserter;

    @Override
    public void requestSuccess(Form form) {

    }

    @Override
    public void requestFail(Form form) {

    }

    @Override
    public void initView() {
        mWelcomePreserter = new WelcomePreserter();
        mWelcomePreserter.start(this, GuideActivity.class);
    }

    @Override
    public void onClick(View v) {

    }
}
