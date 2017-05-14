package com.example.jikeu.myorder.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jikeu.R;
import com.example.jikeu.base.BaseAdapter;
import com.example.jikeu.myorder.model.entity.Order;

import java.util.List;

import static com.example.jikeu.R.id.tv_order_message;

/**
 * Created by ChenZheSheng on 2016/8/14.
 */

public class AllOrderAdapter extends BaseAdapter {

    public AllOrderAdapter(Context context, List data) {
        super(context, data);
    }

    @Override
    protected BaseViewHolder newViewHolder(ViewGroup parent, int viewType) {
        BaseViewHolder holder = new mViewHodler(LayoutInflater.from(context).inflate(R.layout.order_item, null));
        return holder;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        mViewHodler viewHodler = (mViewHodler) holder;
        Order order = (Order) data.get(position);
        viewHodler.mTvOrderNumber.setText(order.getOrderNumber());
        viewHodler.mTvOrderStatu.setText(order.getStatus());
        viewHodler.mTvNumPrice.setText("￥" + order.getNumPrice());
        viewHodler.mTvOrderPrice.setText("￥" + order.getPrice());
        viewHodler.mTvGoodsNumber.setText(order.getNum());
        viewHodler.mTvOrderMessage.setText(order.getMessage());
        viewHodler.mTvOrderName.setText(order.getName());
        viewHodler.mTvOrderGoodsNumber.setText(order.getGoodsNumber());

    }

    class mViewHodler extends BaseViewHolder {
        private TextView mTvOrderNumber;
        private TextView mTvOrderStatu;
        private ImageView mIvOrderImage;
        private TextView mTvOrderName;
        private TextView mTvOrderPrice;
        private TextView mTvOrderMessage;
        private TextView mTvOrderGoodsNumber;
        private TextView mTvGoodsNumber;
        private TextView mTvNumPrice;
        private TextView mTvButton;

        public mViewHodler(View itemView) {
            super(itemView);
        }

        @Override
        public void initView(View itemView) {
            mTvButton = (TextView) itemView.findViewById(R.id.tvButton);
            mTvOrderNumber = (TextView) itemView.findViewById(R.id.tv_order_number);
            mTvOrderStatu = (TextView) itemView.findViewById(R.id.tvOrderStatu);
            mIvOrderImage = (ImageView) itemView.findViewById(R.id.ivOrderImage);
            mTvOrderName = (TextView) itemView.findViewById(R.id.tv_order_name);
            mTvOrderPrice = (TextView) itemView.findViewById(R.id.tv_order_price);
            mTvOrderMessage = (TextView) itemView.findViewById(tv_order_message);
            mTvOrderGoodsNumber = (TextView) itemView.findViewById(R.id.tv_order_goods_number);
            mTvGoodsNumber = (TextView) itemView.findViewById(R.id.tvGoodsNumber);
            mTvNumPrice = (TextView) itemView.findViewById(R.id.tvNumPrice);
        }
    }

}
