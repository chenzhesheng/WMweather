package com.example.jikeu.mycollect.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jikeu.R;
import com.example.jikeu.base.BaseAdapter;
import com.example.jikeu.mycollect.model.entity.Store;

import java.util.List;

/**
 * Created by ChenZheSheng on 2016/8/14.
 */

public class StoreAdapter extends BaseAdapter {

    public StoreAdapter(Context context, List data) {
        super(context, data);
    }

    @Override
    protected BaseViewHolder newViewHolder(ViewGroup parent, int viewType) {
        BaseViewHolder holder = new mViewHodler(LayoutInflater.from(context).inflate(R.layout.store_item, null));
        return holder;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        mViewHodler viewHodler = (mViewHodler) holder;
        Store order = (Store) data.get(position);
        viewHodler.tvStoreName.setText(order.getName());
        viewHodler.tvStoreMessage.setText(order.getMessage());
        viewHodler.tvShouCang.setText(order.getStatus());

    }

    class mViewHodler extends BaseViewHolder {
        private ImageView ivStoreImage;
        private TextView tvStoreName;
        private TextView tvStoreMessage;
        private TextView tvShouCang;
        private ImageView ivStoreMessageImage;

        public mViewHodler(View itemView) {
            super(itemView);
        }

        @Override
        public void initView(View itemView) {
            ivStoreImage = (ImageView) itemView.findViewById(R.id.ivStoreImage);
            tvStoreName = (TextView) itemView.findViewById(R.id.tvStoreNmae);
            tvStoreMessage = (TextView) itemView.findViewById(R.id.tvStoreMessage);
            tvShouCang = (TextView) itemView.findViewById(R.id.tvIsButton);
            ivStoreMessageImage = (ImageView) itemView.findViewById(R.id.ivStoreMessageImage);
        }
    }

}
