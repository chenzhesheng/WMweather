package com.example.jikeu.mycollect.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jikeu.R;
import com.example.jikeu.base.BaseAdapter;
import com.example.jikeu.mycollect.model.entity.Content;

import java.util.List;

/**
 * Created by ChenZheSheng on 2016/8/14.
 */

public class ContentAdapter extends BaseAdapter {

    public ContentAdapter(Context context, List data) {
        super(context, data);
    }

    @Override
    protected BaseViewHolder newViewHolder(ViewGroup parent, int viewType) {
        BaseViewHolder holder = new mViewHodler(LayoutInflater.from(context).inflate(R.layout.content_item, null));
        return holder;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        mViewHodler viewHodler = (mViewHodler) holder;
        Content con = (Content) data.get(position);
        viewHodler.tvName.setText(con.getName());
        viewHodler.tvMessage.setText(con.getMessage());
        viewHodler.tvPrice.setText(con.getPrice());

    }

    class mViewHodler extends BaseViewHolder {
        private ImageView ivContentImage;
        private TextView tvName;
        private TextView tvMessage;
        private TextView tvPrice;

        public mViewHodler(View itemView) {
            super(itemView);
        }

        @Override
        public void initView(View itemView) {
            ivContentImage = (ImageView) itemView.findViewById(R.id.ivStoreImage);
            tvName = (TextView) itemView.findViewById(R.id.tvContentName);
            tvMessage = (TextView) itemView.findViewById(R.id.tvCOntentMessage);
            tvPrice = (TextView) itemView.findViewById(R.id.tvContentPrice);
        }
    }

}
