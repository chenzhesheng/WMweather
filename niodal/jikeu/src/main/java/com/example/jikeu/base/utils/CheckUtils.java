package com.example.jikeu.base.utils;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.List;
import java.util.Map;

public class CheckUtils {
    @SuppressWarnings("unused")
    public static Boolean checkInput(List<EditText> eds, Activity acty) {
        for (int i = 0; i < eds.size(); i++) {
            String str = eds.get(i).getText().toString();
            if (str.equals("")) {
                Toast.makeText(acty, "请完善信息", Toast.LENGTH_LONG).show();
                return false;
            }
        }
        return true;
    }

    /**
     * 判斷是否有網絡
     *
     * @param context
     * @return boolean
     */
    public static boolean checkNetworkState(Context context) {
        boolean flag = false;
        ConnectivityManager manager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (manager != null && manager.getActiveNetworkInfo() != null) {
            flag = manager.getActiveNetworkInfo().isAvailable();
        }
        return flag;
    }

    /**
     * 检查回调参数
     *
     * @param jsonObject
     * @return
     */
    public static boolean checkRequest(JSONObject jsonObject) {
        if (jsonObject == null) {
            return false;
        }
        if (!"0".equals(jsonObject.optString(Constant.ERROR)) && jsonObject.optString(Constant.DATA) == null) {
            return false;
        }
        return true;
    }

    /**
     * 判断回调类型
     *
     * @param map
     * @param types
     * @return
     */
    public static boolean checkRequestType(Map<String, Object> map, int... types) {
        if (map.get(Constant.REQUEST_TYPE) == null)
            return false;
        for (int i = 0; i < types.length; i++) {
            if (Integer.valueOf(map.get(Constant.REQUEST_TYPE) + "") == types[i])
                return true;
        }
        return false;
    }
}
