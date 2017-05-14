package com.example.jikeu.myorder.view.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jikeu.R;
import com.example.jikeu.myorder.model.dao.OrderDao;
import com.example.jikeu.myorder.view.adapter.AllOrderAdapter;

/**
 * Created by ChenZheSheng on 2016/8/14.
 * 待付款
 */

public class ObligationFragment extends Fragment {
    private AllOrderAdapter mAllOrderAdapter;
    private OrderDao mOrderDao;
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
        mOrderDao = new OrderDao();
        mAllOrderAdapter = new AllOrderAdapter(getActivity(),mOrderDao.getOrderList());
        mRvAllOrder.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRvAllOrder.setAdapter(mAllOrderAdapter);
    }
}
