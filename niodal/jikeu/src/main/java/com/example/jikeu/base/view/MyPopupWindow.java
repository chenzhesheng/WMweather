package com.example.jikeu.base.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.PopupWindow;

public class MyPopupWindow extends PopupWindow {
    private Activity activity;
    private WindowManager.LayoutParams lp;

    public MyPopupWindow(Activity activity, View popview, int width, int height,
                         boolean focusable) {
        super(popview, width, height, focusable);
        this.activity = activity;
        //外边不可点击
        setOutsideTouchable(false);
        //设置点击返回键销毁popupwindow
        ColorDrawable colorDrawable = new ColorDrawable(0x00000000);
        setBackgroundDrawable(colorDrawable);
    }

    public void showAt(View view, int gravity, int Xoffset, int Yoffset) {
        lp = activity.getWindow().getAttributes();
        lp.alpha = 0.3f;
        activity.getWindow().setAttributes(lp);
        super.showAtLocation(view, gravity, Xoffset, Yoffset);
    }

    @Override
    public void dismiss() {
        lp = activity.getWindow().getAttributes();
        lp.alpha = 1f;
        activity.getWindow().setAttributes(lp);
        editClearFocus();
        super.dismiss();
    }

    /**
     * 强制关闭软键盘
     */
    private void editClearFocus() {
//        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
//        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
        View view =activity.getWindow().peekDecorView();
        if (view != null) {
            InputMethodManager inputmanger = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputmanger.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}
