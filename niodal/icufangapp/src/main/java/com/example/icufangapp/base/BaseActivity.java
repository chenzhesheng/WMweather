package com.example.icufangapp.base;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.icufangapp.R;
import com.example.icufangapp.base.utils.ImmersedStatusbarUtils;
import com.example.icufangapp.base.utils.LoadingUtils;
import com.netframe.core.BaseAction;
import com.netframe.core.Form;
import com.netframe.utils.ViewUtil;

import java.util.Observable;
import java.util.Observer;


public abstract class BaseActivity extends FragmentActivity implements Observer {
	protected Toast toast;
	private TextView tvToast;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ViewUtil.inject(this);
		initView();
		ImmersedStatusbarUtils.initAfterSetContentView(this,null);
	}


	@Override
	public void update(Observable observable, Object data) {
		Form form = (Form) data;
		if (form.isSuccess()) {
			requestSuccess(form);
		} else {
			requestFail(form);
		}
	}

	/**
	 * action請求成功後的回調方法,需要重寫
	 * 
	 * @param form
	 *            回調成功後返回的數據,裏面包含請求標識requestType和返回的數據data
	 */
	public abstract void requestSuccess(Form form);

	/**
	 * 請求失敗後的回調方法,需要重寫
	 * 
	 *            回調失敗後回傳的請求標識
	 */
	public abstract void requestFail(Form form);

	/**
	 * @说明：初始化view
	 */
	public abstract void initView();

	/**
	 * @说明：点击事件
	 */
	public abstract void onClick(View v);

	/**
	 * toast提示信息。
	 * 
	 * @param message
	 */
	public void showToast(String message) {
		if (toast == null) {
			toast = new Toast(this);
			View view = LayoutInflater.from(this).inflate(R.layout.toast_item, null);
			tvToast = (TextView) view.findViewById(R.id.tvToast);
			tvToast.setText(message);
			toast.setView(view);
			toast.setDuration(Toast.LENGTH_LONG);
		} else
			tvToast.setText(message);
		toast.show();
	}

	/**
	 * 顯示loading
	 */
	public void showLoading() {
		LoadingUtils.startLoading(this);
	}

	/**
	 * 消除 loading
	 */
	protected void disLoading() {
		LoadingUtils.dismiss();
	}

	/**
	 * 將action與view綁定起來的方法,交由註解綁定,不需要顯式調用
	 * 
	 * @param action
	 *            需要與view綁定的action
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
