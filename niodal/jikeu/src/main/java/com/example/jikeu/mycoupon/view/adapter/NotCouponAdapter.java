package com.example.jikeu.mycoupon.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jikeu.R;
import com.example.jikeu.base.BaseAdapter;
import com.example.jikeu.mycoupon.model.entity.Coupon;

import java.util.List;

/**
 * Created by ChenZheSheng on 2016/8/14.
 */

public class NotCouponAdapter extends BaseAdapter {

    public NotCouponAdapter(Context context, List data) {
        super(context, data);
    }

    @Override
    protected BaseViewHolder newViewHolder(ViewGroup parent, int viewType) {
        BaseViewHolder holder = new mViewholder(LayoutInflater.from(context).inflate(R.layout.coupon_item,null));
        return holder;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        mViewholder viewholder = (mViewholder) holder;
        Coupon coupon = (Coupon) data.get(position);
        viewholder.tvStoreName.setText(coupon.getStoreName());
        viewholder.tvCouponDate.setText("使用期限："+coupon.getCouponDate());
    }

    class mViewholder extends BaseViewHolder {
        private ImageView ivStoreShoto;
        private TextView tvStoreName;
        private TextView tvCouponDate;
        private ImageView ivCouponNumber;

        public mViewholder(View itemView) {
            super(itemView);
        }

        @Override
        public void initView(View itemView) {
            ivStoreShoto = (ImageView) itemView.findViewById(R.id.ivCouponShoto);
            tvStoreName = (TextView) itemView.findViewById(R.id.tvStoreNmae);
            tvCouponDate = (TextView) itemView.findViewById(R.id.tvCouponDate);
            ivCouponNumber = (ImageView) itemView.findViewById(R.id.ivCouponNumber);
        }
    }
}
