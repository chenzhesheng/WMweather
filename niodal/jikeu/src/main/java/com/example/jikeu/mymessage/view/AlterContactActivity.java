package com.example.jikeu.mymessage.view;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jikeu.R;
import com.example.jikeu.base.utils.HeadTitleUtil;
import com.example.jikeu.base.utils.SharedPreferencesUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ChenZheSheng on 2016/8/14.
 */
public class AlterContactActivity extends Activity {
    private String number;
    @BindView(R.id.et_alter_name)
    EditText mEtAlterName;
    @BindView(R.id.iv_alter_detele)
    ImageView mIvAlterDetele;
    @BindView(R.id.tv_alter_name_save)
    TextView mTvAlterNameSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alter_contact);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        HeadTitleUtil.setTextHead(this,"修改号码");
    }

    @OnClick({R.id.iv_alter_detele, R.id.tv_alter_name_save})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_alter_detele:
                mEtAlterName.setText("");
                break;
            case R.id.tv_alter_name_save:
                number = mEtAlterName.getText().toString().trim();
                SharedPreferencesUtils.setParam(this,"usernumber",number);
                Toast.makeText(this, "修改电话成功", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
