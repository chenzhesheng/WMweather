package com.example.jikeu.mymessage.view;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jikeu.R;
import com.example.jikeu.base.utils.HeadTitleUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ChenZheSheng on 2016/8/14.
 */
public class AddAddressActivity extends Activity {

    @BindView(R.id.et_add_name)
    EditText mEtAddName;
    @BindView(R.id.ivDeteleName)
    ImageView mIvDeteleName;
    @BindView(R.id.et_add_number)
    EditText mEtAddNumber;
    @BindView(R.id.ivDeteleContact)
    ImageView mIvDeteleContact;
    @BindView(R.id.et_add_address)
    EditText mEtAddAddress;
    @BindView(R.id.ivDeteleAddress)
    ImageView mIvDeteleAddress;
    @BindView(R.id.tvAddSave)
    TextView mTvAddSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_address);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        HeadTitleUtil.setTextHead(this,"修改收货地址");
    }

    @OnClick({R.id.ivDeteleName, R.id.ivDeteleContact, R.id.ivDeteleAddress, R.id.tvAddSave})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivDeteleName:
                mEtAddName.setText("");
                break;
            case R.id.ivDeteleContact:
                mEtAddNumber.setText("");
                break;
            case R.id.ivDeteleAddress:
                mEtAddAddress.setText("");
                break;
            case R.id.tvAddSave:
                break;
        }
    }
}
