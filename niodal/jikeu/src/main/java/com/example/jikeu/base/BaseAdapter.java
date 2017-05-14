package com.example.jikeu.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.example.jikeu.base.listener.OnItemClickAdapter;
import com.example.jikeu.base.listener.OnLongItemClickAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ChenZheSheng on 2016/8/14.
 */

public abstract class BaseAdapter extends RecyclerView.Adapter<BaseAdapter.BaseViewHolder> {
    public List<Object> data;
    public Context context;
    public OnItemClickAdapter itemClickAdapter;
    public OnLongItemClickAdapter longItmeClickAdapter;

    public BaseAdapter(Context context) {
        this(context, null);
    }

    public BaseAdapter(Context context, List<Object> data) {
        if (data == null) {
            data = new ArrayList<>();
        }
        this.data = data;
        this.context = context;
    }


    /**
     * 更新数据
     *
     * @param data
     */
    public void refresh(List<Object> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    /**
     * 替换全部数据
     */
    public void replace(List<Object> data) {
        if (data.size() > 0) {
            this.data.clear();
            this.data.addAll(data);
            notifyDataSetChanged();
        }
    }


    /**
     * 添加全部数据
     */
    public void addAll(List<Object> data) {
        if (data.size() > 0) {
            this.data.addAll(0, data);
            notifyDataSetChanged();
        }
    }

    /**
     * 移除一条数据集合
     *
     * @param position
     */
    public void remove(int position) {
        data.remove(position);
        notifyDataSetChanged();
    }

    /**
     * 移除所有数据
     */
    public void removeAll() {
        data.clear();
        notifyDataSetChanged();
    }

    /**
     * 动态增加一条数据
     *
     * @param itemDataType 数据实体类对象
     */
    public void append(Object itemDataType) {
        if (itemDataType != null) {
            data.add(itemDataType);
            notifyDataSetChanged();
        }
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return newViewHolder(parent, viewType);
    }

    protected abstract BaseViewHolder newViewHolder(ViewGroup parent, int viewType);

    @Override
    public int getItemCount() {
        if (data == null || data.size() == 0) {
            return 0;
        }
        return data.size();
    }


    /**
     * ViewHolder
     */
    public abstract class BaseViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        public BaseViewHolder(View itemView) {
            super(itemView);
            initView(itemView);
        }

        @Override
        public boolean onLongClick(View v) {
            if (longItmeClickAdapter != null)
                longItmeClickAdapter.OnLongClickAdapterListener(v, getAdapterPosition());
            return true;
        }

        @Override
        public void onClick(View v) {
            if (itemClickAdapter != null)
                itemClickAdapter.OnClickAdapterListener(v, getAdapterPosition());
        }

        /**
         * 初始化ViewHolder数据
         */
        public abstract void initView(View itemView);
    }

    public void setOnClickAdapterListener(OnItemClickAdapter listener) {
        this.itemClickAdapter = listener;
    }

    public void setOnLongClickAdapterListener(OnLongItemClickAdapter listener) {
        this.longItmeClickAdapter = listener;
    }
}