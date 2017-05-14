package com.example.jikeu.base.utils;


import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.jikeu.R;


public class HeadTitleUtil {
	private static TextView tvHead;
	private static LinearLayout ivBack;

	public static void setTextHead(final Activity acty, String head) {
		ImmersedStatusbarUtils.initAfterSetContentView(acty,acty.findViewById(R.id.ll_header_title));
		tvHead = (TextView) acty.findViewById(R.id.tv_head);
		tvHead.setText(head);
		ivBack = (LinearLayout) acty.findViewById(R.id.iv_back);
		ivBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				acty.finish();
			}
		});
	}

	public static void setTextHeadWithoutBack(final Activity acty, String head) {
		tvHead = (TextView) acty.findViewById(R.id.tv_head);
		tvHead.setText(head);
	}
	public static void setTextHeadWithoutBack(final View v, String head) {
		tvHead = (TextView) v.findViewById(R.id.tv_head);
		tvHead.setText(head);
	}
}
