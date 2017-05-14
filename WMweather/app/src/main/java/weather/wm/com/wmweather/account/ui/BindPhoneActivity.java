package weather.wm.com.wmweather.account.ui;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import weather.wm.com.wmweather.R;
import weather.wm.com.wmweather.common.units.RequestUtils;
import weather.wm.com.wmweather.common.units.SharedPreferenceUtils;
import weather.wm.com.wmweather.common.units.UrlUtils;

public class BindPhoneActivity extends AppCompatActivity implements View.OnClickListener {

    private Button mButtonBind;
    private EditText mEditTextPhone;
    private EditText mEditTextCode;
    private TextView mTextViewCode;
    private ProgressBar mProgressBar;
    private boolean mIsRunning = false;
    private boolean mIsOk=false;
    private String mCodeOld="123456";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bind_phone);
        findViewById(R.id.text_view_back).setOnClickListener(this);
        mButtonBind= (Button) findViewById(R.id.button_bind);
        mTextViewCode = (TextView) findViewById(R.id.text_view_auth_code);
        mEditTextPhone = (EditText) findViewById(R.id.edit_text_phone);
        mEditTextCode = (EditText) findViewById(R.id.edit_text_auth_code);
        mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);
        mTextViewCode.setOnClickListener(this);
        mButtonBind.setOnClickListener(this);
        mEditTextPhone.setText(SharedPreferenceUtils.getUsername(this));
        mEditTextPhone.setEnabled(false);
    }

    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            if (message.what > 0) {
                mTextViewCode.setText(message.what + " S");
            } else {
                mTextViewCode.setEnabled(true);
                mTextViewCode.setText("获取验证码");
            }
            return true;
        }
    });

    private void updateCode() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                int i = 60;
                mIsRunning = true;
                while (mIsRunning) {
                    if (i > 0) {
                        Message.obtain(handler, i).sendToTarget();
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        i--;
                    } else {
                        mIsRunning = false;
                        Message.obtain(handler, 0).sendToTarget();
                    }
                }
            }
        }).start();
    }

    private void getCode(String phone) {
        Map<String, String> body = new HashMap<>();
        body.put("phone", phone);
        body.put("type", "4");
        RequestUtils.post(UrlUtils.GETCAPTCHA, null, body, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object = new JSONObject(response);
                    if (object.getLong("status") == 2000000) {
                        Toast.makeText(BindPhoneActivity.this, "获取验证码成功", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(BindPhoneActivity.this, "获取验证码失败", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(BindPhoneActivity.this, "获取验证码失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void bind() {
        mProgressBar.setVisibility(View.VISIBLE);
        Map<String, String> body = new HashMap<>();
        body.put("phone", SharedPreferenceUtils.getUsername(this));
        body.put("newPhone", mEditTextPhone.getText().toString().trim());
        body.put("preCode",mCodeOld);
        body.put("newCode", mEditTextCode.getText().toString().trim());
        RequestUtils.post(UrlUtils.CHANG_PHONE, null, body, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                mProgressBar.setVisibility(View.GONE);
                try {
                    JSONObject object = new JSONObject(response);
                    if (object.getLong("status") == 2000000) {
                        Toast.makeText(BindPhoneActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(BindPhoneActivity.this, "修改失败", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mProgressBar.setVisibility(View.GONE);
                Toast.makeText(BindPhoneActivity.this, "修改失败", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private boolean verify() {
        String phone = mEditTextPhone.getText().toString();
        String code = mEditTextCode.getText().toString();
        if (TextUtils.isEmpty(phone)) {
            Toast.makeText(this, "电话号码不能为空", Toast.LENGTH_SHORT).show();
            return false;
        } else if (TextUtils.isEmpty(code)) {
            Toast.makeText(this, "验证码不能为空", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.text_view_back:
                finish();
                break;
            case R.id.text_view_auth_code:
                updateCode();
                mTextViewCode.setEnabled(false);
                if(TextUtils.isEmpty(mEditTextPhone.getText().toString())){
                    Toast.makeText(this,"请输入电话号码",Toast.LENGTH_SHORT).show();
                }else{
                    getCode(mEditTextPhone.getText().toString().trim());
                }

                break;
            case R.id.button_bind:
                if(verify()){
                    if(mIsOk){
                        bind();
                    }else{
                        mIsRunning=false;
                        mCodeOld=mEditTextCode.getText().toString();
                        mEditTextPhone.getText().clear();
                        mEditTextCode.getText().clear();
                        mEditTextPhone.setEnabled(true);
                        mButtonBind.setText("绑定");
                        Message.obtain(handler, 0).sendToTarget();
                        mIsOk=true;
                    }
                }
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mIsRunning = false;
    }
}
