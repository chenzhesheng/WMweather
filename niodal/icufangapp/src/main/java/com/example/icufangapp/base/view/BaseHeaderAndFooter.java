package com.example.icufangapp.base.view;

import android.content.Context;
import android.support.v4.util.SparseArrayCompat;
import android.view.View;
import android.view.ViewGroup;

import com.example.icufangapp.base.BaseAdapter;

/**
 * Created by ChenZheSheng on 2016/7/17.
 */

public class BaseHeaderAndFooter<T> extends BaseAdapter {
    public static final int BASE_ITEM_TYPE_HEADER = 10000;
    public static final int BASE_ITEN_TYPE_TOOTER = 20000;

    //SparseArrayCompat有什么特点呢？它类似于Map，只不过在某些情况下比Map的性能要好，并且只能存储key为int的情况。
    private SparseArrayCompat<View> mHeaderViews = new SparseArrayCompat<>();
    private SparseArrayCompat<View> mTooterViews = new SparseArrayCompat<>();

    public BaseHeaderAndFooter(Context context) {
        super(context);
    }

    public void addHeaderView(View view) {
        mHeaderViews.put(mHeaderViews.size() + BASE_ITEM_TYPE_HEADER, view);
    }

    public void addTooterView(View view) {
        mTooterViews.put((mTooterViews.size()) + BASE_ITEN_TYPE_TOOTER, view);
    }

    public int getHeaderSize() {
        return mHeaderViews.size();
    }

    public int getTooterSize() {
        return mTooterViews.size();
    }


    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
    }

    @Override
    protected BaseViewHolder newViewHolder(ViewGroup parent, int viewType) {
        if (mHeaderViews.get(viewType) != null) {

        } else if (mTooterViews.get(viewType) != null){

        }
        return null;
    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
