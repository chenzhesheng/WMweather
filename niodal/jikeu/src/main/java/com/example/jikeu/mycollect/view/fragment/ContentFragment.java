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
import com.example.jikeu.mycollect.model.dao.ContentDao;
import com.example.jikeu.mycollect.view.adapter.ContentAdapter;

/**
 * Created by ChenZheSheng on 2016/8/14.
 * 内容
 */

public class ContentFragment extends Fragment {
    private ContentAdapter mContentAdapter;
    private ContentDao mContentDao;
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
        mContentDao = new ContentDao();
        mContentAdapter = new ContentAdapter(getActivity(),mContentDao.getContentList());
        mRvAllOrder.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRvAllOrder.setAdapter(mContentAdapter);
    }
}
