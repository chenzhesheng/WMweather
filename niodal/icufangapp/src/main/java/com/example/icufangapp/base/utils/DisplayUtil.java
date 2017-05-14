package com.example.icufangapp.base.utils;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;

public class DisplayUtil {

    /** 灞忓箷瀹藉害   */
    private static int DisplayWidthPixels = 0;
    /** 灞忓箷楂樺害   */
    private static int DisplayheightPixels = 0;

    /**
     * 鑾峰彇灞忓箷鍙傛暟
     * @param context
     */
    private static void getDisplayMetrics(Context context) {
        DisplayMetrics dm = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(dm);
        // 瀹藉害
        DisplayWidthPixels = dm.widthPixels;
        // 楂樺害
        DisplayheightPixels = dm.heightPixels;
    }

    /**
     * 鑾峰彇灞忓箷瀹藉害
     * @param context
     * @return
     */
    public static int getDisplayWidthPixels(Context context) {
        if (context == null) {
            return -1;
        }
        if (DisplayWidthPixels == 0) {
            getDisplayMetrics(context);
        }
        return DisplayWidthPixels;
    }

    /**
     * 鑾峰彇灞忓箷楂樺害
     * @param context
     * @return
     */
    public static int getDisplayheightPixels(Context context) {
        if (context == null) {
            return -1;
        }
        if (DisplayheightPixels == 0) {
            getDisplayMetrics(context);
        }
        return DisplayheightPixels;
    }

    /**
     * 灏唒x鍊艰浆鎹负dip鎴杁p鍊� 
     *
     * @param pxValue
     * @return
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 灏哾ip鎴杁p鍊艰浆鎹负px鍊� 
     *
     * @param dipValue
     * @return
     */
    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    /**
     * 灏唒x鍊艰浆鎹负sp鍊� 
     *
     * @param pxValue
     * @return
     */
    public static int px2sp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    /**
     * 灏唖p鍊艰浆鎹负px鍊� 
     * @param spValue
     * @return
     */
    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }
}
