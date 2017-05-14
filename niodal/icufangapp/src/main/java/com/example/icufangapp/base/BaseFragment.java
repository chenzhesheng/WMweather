package com.example.icufangapp.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.icufangapp.R;
import com.example.icufangapp.base.utils.LoadingUtils;
import com.lidroid.xutils.ViewUtils;
import com.netframe.core.BaseAction;
import com.netframe.core.Form;

import java.util.Observable;
import java.util.Observer;


/**
 * Created by ChenZheSheng on 2016/4/25.
 * 说明： ① Fragment v4包的基类，用于与ViewPager相配合
 *        ② 注解使用xUtils包，即  @ContentView(value = R.layout.xxx) 声明布局
 *                                 @ViewInject(value = R.id.xxx) 声明控件
 */
public abstract class BaseFragment extends Fragment implements Observer {
    protected Toast toast;
    private TextView tvToast;
    public Activity context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(setLayoutId(),container,false);
//        ViewUtil.inject(this, super.onCreateView(inflater, container, savedInstanceState));
        ViewUtils.inject(this,view);
        initView(view);
        return view;
    }

    public abstract int setLayoutId();

    @Override
    public void update(Observable observable, Object data) {
        Form form = (Form) data;
        if (form.isSuccess()) {
            requestSuccess(form);
        } else {
            requestFail(form);
        }
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        context = activity;
    }

    /**
     * action請求成功後的回調方法,需要重寫
     *
     * @param form 回調成功後返回的數據,裏面包含請求標識requestType和返回的數據data
     */
    public abstract void requestSuccess(Form form);

    /**
     * 請求失敗後的回調方法,需要重寫
     *
     */
    public abstract void requestFail(Form form);

    /**
     * @说明：初始化view
     */
    public abstract void initView(View view);


    /**
     * 顯示loading
     */
    public void showLoading() {
        LoadingUtils.startLoading(context);
    }

    /**
     * 消除 loading
     */
    protected void disLoading() {
        LoadingUtils.dismiss();
    }

    /**
     * toast提示信息。
     *
     * @param message
     */
    public void showToast(String message) {
        if (toast == null) {
            toast = new Toast(getActivity());
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.toast_item, null);
            tvToast = (TextView) view.findViewById(R.id.tvToast);
            tvToast.setText(message);
            toast.setView(view);
            toast.setDuration(Toast.LENGTH_LONG);
        } else
            tvToast.setText(message);
        toast.show();
    }

    /**
     * 將action與view綁定起來的方法,交由註解綁定,不需要顯式調用
     *
     * @param action 需要與view綁定的action
     * @return 返回綁定是否成功
     */
    public boolean setAction(BaseAction action) {
        if (action != null) {
            action.addObserver(this);
        } else {
            return false;
        }
        return true;
    }
}
