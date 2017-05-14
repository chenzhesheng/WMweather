package com.example.icufangapp.welcome.presenter;

import android.app.Activity;

import com.example.icufangapp.welcome.model.IWelcomeModel;
import com.example.icufangapp.welcome.model.WelcomeModel;

/**
 * Created by ChenZheSheng on 2016/7/14.
 */

public class WelcomePreserter {
    private IWelcomeModel mWelcomeModel;

    public WelcomePreserter(){
        mWelcomeModel = new WelcomeModel();
    }

    public void start(Activity activity,Class cls){
        mWelcomeModel.startActivity(activity,cls);
    }
}
