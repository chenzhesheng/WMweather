package com.example.icufangapp.base.utils;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;

import com.example.icufangapp.R;


/**
 * �Ի��򹤾��࣬����������AlertDialog�����ProgressDialog
 * 
 * @author tarena.sunwei
 *
 */
public class DialogUtils {
	/**
	 * �����û��趨���ݻ��һ��AlertDialog����
	 * 
	 * @param context
	 * @param title �Ի���ı���
	 * @param message �Ի�������ʾ������
	 * @param icon �Ի����ͼƬ
	 * @param positive �Ի�����positive��ť����ʾ������
	 * @param positiveListener �Ի�����positive��ť������ļ�����
	 * @param negative �Ի�����negative��ť����ʾ������
	 * @param negativeListener �Ի�����negative��ť������ļ�����
	 * @return һ��AlertDialog�Ի������
	 */
	public static AlertDialog createAlertDialog(
			Context context,
			String title,
			String message,
			int icon,
			String positive,
			DialogInterface.OnClickListener positiveListener,
			String negative,
			DialogInterface.OnClickListener negativeListener){
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle(title);
		builder.setMessage(message);
		if(icon !=-1)builder.setIcon(icon);
		if(!TextUtils.isEmpty(positive))
			builder.setPositiveButton(positive, positiveListener);
		if(!TextUtils.isEmpty(negative)){
			builder.setNegativeButton(negative, negativeListener);
		}
		return builder.create();
	}
	/**
	 * ���һ��ProgressDialog����
	 * 
	 * @param context
	 * @param text ���ȶԻ�������ʾ������
	 * @return һ��ProgressDialog
	 */
	public static ProgressDialog createProgressDialog(Context context,String text){
		ProgressDialog pd = new ProgressDialog(context, ProgressDialog.THEME_HOLO_LIGHT);
		pd.setIndeterminate(true);
		pd.setIndeterminateDrawable(context.getResources().getDrawable(R.drawable.back));
		pd.setMessage(text);
		return pd;
	}
}
