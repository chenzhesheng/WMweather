package com.example.jikeu.mymessage.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.example.jikeu.R;
import com.example.jikeu.base.utils.HeadTitleUtil;
import com.example.jikeu.base.utils.IntentUtils;
import com.example.jikeu.mymessage.model.dao.AddressDao;
import com.example.jikeu.mymessage.view.adapter.AddressAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ChenZheSheng on 2016/8/14.
 */
public class AddressActivity extends Activity {
    @BindView(R.id.tvAddAddress)
    TextView mTvAddAddress;
    private AddressDao mAddressDao;
    private AddressAdapter mAddressAdapter;
    @BindView(R.id.rvAddress)
    RecyclerView mRvAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        HeadTitleUtil.setTextHead(this, "地址管理");
        mAddressDao = new AddressDao();
        mAddressAdapter = new AddressAdapter(this, mAddressDao.getAddressData());
        mRvAddress.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mRvAddress.setAdapter(mAddressAdapter);
    }

    @OnClick(R.id.tvAddAddress)
    public void onClick() {
        IntentUtils.startActivity(this,AddAddressActivity.class);
    }
}
