package com.example.jikeu.mymessage.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.jikeu.R;
import com.example.jikeu.base.BaseAdapter;
import com.example.jikeu.mymessage.model.entity.Address;

import java.util.List;

/**
 * Created by ChenZheSheng on 2016/8/14.
 */

public class AddressAdapter extends BaseAdapter {

    public AddressAdapter(Context context) {
        super(context);
    }

    public AddressAdapter(Context context, List data) {
        super(context, data);
    }

    @Override
    protected BaseViewHolder newViewHolder(ViewGroup parent, int viewType) {
        BaseViewHolder holder = new mViewHodler(LayoutInflater.from(context).inflate(R.layout.address_item, null));
        return holder;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        Address address = (Address) data.get(position);
        mViewHodler mViewHodler = (AddressAdapter.mViewHodler) holder;
        mViewHodler.mTvContactNumber.setText(address.getNumber());
        mViewHodler.mTvAddress.setText(address.getAddress());
        mViewHodler.mTvName.setText(address.getName());
    }


    class mViewHodler extends BaseViewHolder {
        private TextView mTvContactNumber;
        private TextView mTvAddress;
        private TextView mTvName;
        private CheckBox mCbAddress;
        private TextView mTvEditItem;
        private TextView mTvDeteleItem;

        public mViewHodler(View itemView) {
            super(itemView);
        }

        @Override
        public void initView(View itemView) {
            mTvContactNumber = (TextView) itemView.findViewById(R.id.tvContactNumber);
            mTvAddress = (TextView) itemView.findViewById(R.id.tvAddress);
            mTvName = (TextView) itemView.findViewById(R.id.tvAddressName);
            mCbAddress = (CheckBox) itemView.findViewById(R.id.cbAddress);
            mTvEditItem = (TextView) itemView.findViewById(R.id.tvEditItem);
            mTvDeteleItem = (TextView) itemView.findViewById(R.id.tvDeteleItem);
        }
    }
}
