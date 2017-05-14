package weather.wm.com.wmweather.account.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.github.mikephil.charting.utils.MPPointD;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import de.greenrobot.event.EventBus;
import weather.wm.com.wmweather.MainActivity;
import weather.wm.com.wmweather.R;
import weather.wm.com.wmweather.common.event.LoginEvent;
import weather.wm.com.wmweather.common.units.RequestUtils;
import weather.wm.com.wmweather.common.units.SharedPreferenceUtils;
import weather.wm.com.wmweather.common.units.UrlUtils;

public class QuickLoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText mEditTextPhone;
    private EditText mEditTextCode;
    private TextView mTextViewTips;
    private TextView mTextViewCode;
    private ProgressBar mProgressBar;

    private boolean mIsRunning = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quick_login);
        findViewById(R.id.text_view_back).setOnClickListener(this);
        findViewById(R.id.button_login).setOnClickListener(this);
        mTextViewCode= (TextView) findViewById(R.id.text_view_auth_code);
        mEditTextPhone = (EditText) findViewById(R.id.edit_text_phone);
        mEditTextCode = (EditText) findViewById(R.id.edit_text_auth_code);
        mTextViewTips = (TextView) findViewById(R.id.text_view_tips);
        mProgressBar= (ProgressBar) findViewById(R.id.progress_bar);
        mTextViewCode.setOnClickListener(this);

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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.text_view_back:
                finish();
                break;
            case R.id.button_login:
                if (verify()) {
                    login();
                }
                break;
            case R.id.text_view_auth_code:
                updateCode();
                mTextViewCode.setEnabled(false);
                getCode();
                break;
        }
    }

    private void getCode(){
        Map<String, String> body = new HashMap<>();
        body.put("phone", mEditTextPhone.getText().toString().trim());
        body.put("type","2");
        RequestUtils.post(UrlUtils.GETCAPTCHA, null, body, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object=new JSONObject(response);
                    if(object.getLong("status")==2000000){
                        Toast.makeText(QuickLoginActivity.this,"获取验证码成功",Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(QuickLoginActivity.this,"获取验证码失败",Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(QuickLoginActivity.this,"获取验证码失败",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void login() {
        mProgressBar.setVisibility(View.VISIBLE);
        Map<String, String> body = new HashMap<>();
        body.put("phone", mEditTextPhone.getText().toString().trim());
        body.put("captcha", mEditTextCode.getText().toString().trim());
        RequestUtils.post(UrlUtils.LOGIN_FAST, null, body, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                mProgressBar.setVisibility(View.GONE);
                try {
                    JSONObject object = new JSONObject(response);
                    if (object.getLong("status") == 2000000) {
                        JSONObject json = object.getJSONObject("data");
                        SharedPreferenceUtils.setAccountId(QuickLoginActivity.this, json.getString("id"));
                        SharedPreferenceUtils.setUserName(QuickLoginActivity.this, json.getString("username"));
                        SharedPreferenceUtils.setStatus(QuickLoginActivity.this, json.getString("status"));
                        SharedPreferenceUtils.setSessionId(QuickLoginActivity.this, json.getString("sessionId"));
                        EventBus.getDefault().post(new LoginEvent());
                        startActivity(new Intent(QuickLoginActivity.this, MainActivity.class));
                        finish();
                    } else if (object.getLong("status") == 5000107) {
                        Toast.makeText(QuickLoginActivity.this, "验证码错误", Toast.LENGTH_SHORT).show();
                    }else if(object.getLong("status") == 5000106){
                        Toast.makeText(QuickLoginActivity.this, "用户不存在", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(QuickLoginActivity.this, "登录失败", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mProgressBar.setVisibility(View.GONE);
                Toast.makeText(QuickLoginActivity.this, "登录失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean verify() {
        String phone = mEditTextPhone.getText().toString();
        String code = mEditTextCode.getText().toString();
        if (TextUtils.isEmpty(phone)) {
            mTextViewTips.setVisibility(View.VISIBLE);
            mTextViewTips.setText("电话号码不能为空");
            return false;
        } else if (TextUtils.isEmpty(code)) {
            mTextViewTips.setVisibility(View.VISIBLE);
            mTextViewTips.setText("验证码不能为空");
            return false;
        }
        return true;
    }

}
