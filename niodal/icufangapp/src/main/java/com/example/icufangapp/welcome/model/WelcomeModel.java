package com.example.icufangapp.welcome.model;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;

/**
 * Created by ChenZheSheng on 2016/7/13.
 */

public class WelcomeModel implements IWelcomeModel{
    public void startActivity(final Activity activity, final Class clas){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                activity.startActivity(new Intent(activity,clas));
                activity.finish();
            }
        },3000);
    }
}
