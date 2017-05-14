package com.example.icufangapp.base.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;

import com.mingle.widget.ShapeLoadingDialog;

/**
 * Created by ChenZheSheng on 2016/3/28.
 * 说明：依赖shapeloading包，加载缓冲动画效果
 */
public class LoadingUtils {
    public static ShapeLoadingDialog shapeLoadingDialog;
    public static Dialog dialog;

    public static void startLoading(Context context) {
        shapeLoadingDialog = new ShapeLoadingDialog(context);
        shapeLoadingDialog.setLoadingText("加载中...");
        shapeLoadingDialog.setCanceledOnTouchOutside(false);
        shapeLoadingDialog.setBackground(0);
        dialog = shapeLoadingDialog.getDialog();
        dialog.setCancelable(false);
        if (shapeLoadingDialog != null && !dialog.isShowing()) {
            if (context instanceof Activity) {
                Activity act = (Activity) context;
                if (act.isFinishing()) {
                    return;
                }
            }
            shapeLoadingDialog.show();
        }
    }

    public static void dismiss() {
        if (shapeLoadingDialog != null && dialog.isShowing()) {
            dialog.setCancelable(true);
            shapeLoadingDialog.dismiss();
            shapeLoadingDialog = null;
            dialog = null;
        }
    }
}
