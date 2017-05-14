package com.example.jikeu.mycollect.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jikeu.R;
import com.example.jikeu.mycollect.model.dao.StoreDao;
import com.example.jikeu.mycollect.view.adapter.StoreAdapter;

/**
 * Created by ChenZheSheng on 2016/8/14.
 * 店铺
 */

public class StoreFragment extends Fragment {
    private StoreAdapter mStoreAdapter;
    private StoreDao mStoreDao;
    RecyclerView mRvAllOrder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_all_order, null);
        mRvAllOrder = (RecyclerView) view.findViewById(R.id.rvAllOrder);
        initView();
        return view;
    }

    private void initView() {
        mStoreDao = new StoreDao();
        mStoreAdapter = new StoreAdapter(getActivity(),mStoreDao.getStoreList());
        mRvAllOrder.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRvAllOrder.setAdapter(mStoreAdapter);
    }
}
