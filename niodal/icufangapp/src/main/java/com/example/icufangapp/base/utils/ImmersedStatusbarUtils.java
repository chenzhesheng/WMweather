package com.example.icufangapp.base.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by ChenZheSheng on 2016/3/25.
 * 说明：  当当前设备版本不小与19，则显示沉浸式状态栏
 */

public class ImmersedStatusbarUtils {


    /**
     * 在{@link Activity#setContentView}之后调用
     *
     * @param activity       要实现的沉浸式状态栏的Activity
     * @param titleViewGroup 头部控件的ViewGroup,若为null,整个界面将和状态栏重叠
     */

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static void initAfterSetContentView(Activity activity, View titleViewGroup) {
        // 如果当前设备版本小于19，则不修改状态栏
        if (getAndroidSDKVersion() < 19) {
            Log.i("TAG","SDK版本："+getAndroidSDKVersion()+"\n return");
            return;
        }
        if (activity == null)
            return;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = activity.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        if (titleViewGroup == null)
            return;
        // 设置头部控件ViewGroup的PaddingTop,防止界面与状态栏重叠
        int statusBarHeight = getStatusBarHeight(activity);
        titleViewGroup.setPadding(0, statusBarHeight, 0, 0);
    }


    /**
     * 获取状态栏高度
     *
     * @param context
     * @return
     */
    private static int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;

    }

    /**
     * Android开发中程序判断手机操作系统版本。
     */
    public static int getAndroidSDKVersion() {
        int version = 0;
        try {
            version = Integer.valueOf(Build.VERSION.SDK);
        } catch (NumberFormatException e) {
            Log.i("TAG", e.toString());
        }
        return version;
    }


}

