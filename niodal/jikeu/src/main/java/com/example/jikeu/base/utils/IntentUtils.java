package com.example.jikeu.base.utils;

import android.content.Context;
import android.content.Intent;

/**
 * Created by ChenZheSheng on 2016/8/14.
 */

public class IntentUtils {
    public static void startActivity(Context context, Class startActivtiy){
        Intent intent = new Intent(context,startActivtiy);
        context.startActivity(intent);
    }
}
