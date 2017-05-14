package weather.wm.com.wmweather.account.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RelativeLayout;

import weather.wm.com.wmweather.R;
import weather.wm.com.wmweather.common.units.SharedPreferenceUtils;
import weather.wm.com.wmweather.common.view.DoubleTextView;

public class SecurityCenterActivity extends AppCompatActivity implements View.OnClickListener {

    private DoubleTextView mTextViewPhone;
    private RelativeLayout mRelativeLayoutUpdataPassword;
    private String bindPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_security_center);
        findViewById(R.id.text_view_back).setOnClickListener(this);
        mTextViewPhone= (DoubleTextView) findViewById(R.id.text_view_bind_phone);
        mRelativeLayoutUpdataPassword = (RelativeLayout) findViewById(R.id.relative_layout_password);
        mRelativeLayoutUpdataPassword.setOnClickListener(this);
        mTextViewPhone.setOnClickListener(this);

        bindPhone = SharedPreferenceUtils.getUsername(this);
        mTextViewPhone.setValueText(bindPhone);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.text_view_back:
                finish();
                break;
            case R.id.text_view_bind_phone:
                startActivityForResult(new Intent(this,BindPhoneActivity.class),10000);
                break;
            case R.id.relative_layout_password:
                startActivityForResult(new Intent(this,UpdataPasswordActivity.class),10001);
        }
    }
}
