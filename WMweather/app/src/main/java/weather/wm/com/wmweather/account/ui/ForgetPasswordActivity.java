package weather.wm.com.wmweather.account.ui;

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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import weather.wm.com.wmweather.R;
import weather.wm.com.wmweather.common.units.RequestUtils;
import weather.wm.com.wmweather.common.units.UrlUtils;

public class ForgetPasswordActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText mEditTextPhone;
    private EditText mEditTextCode;
    private EditText mEditTextPassword;
    private EditText mEditTextPassword2;
    private TextView mTextViewCode;
    private TextView mTextViewTips;
    private ProgressBar mProgressBar;

    private boolean mIsRunning = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        findViewById(R.id.text_view_back).setOnClickListener(this);
        findViewById(R.id.button_find_password).setOnClickListener(this);
        mEditTextPhone = (EditText) findViewById(R.id.edit_text_phone);
        mEditTextCode = (EditText) findViewById(R.id.edit_text_auth_code);
        mEditTextPassword = (EditText) findViewById(R.id.edit_text_new_password);
        mEditTextPassword2 = (EditText) findViewById(R.id.edit_text_repeat_password);
        mTextViewCode = (TextView) findViewById(R.id.text_view_auth_code);
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

    private void getCode() {
        Map<String, String> body = new HashMap<>();
        body.put("phone", mEditTextPhone.getText().toString().trim());
        body.put("type", "3");
        RequestUtils.post(UrlUtils.GETCAPTCHA, null, body, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object = new JSONObject(response);
                    if (object.getLong("status") == 2000000) {
                        Toast.makeText(ForgetPasswordActivity.this, "获取验证码成功", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(ForgetPasswordActivity.this, "获取验证码失败", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ForgetPasswordActivity.this, "获取验证码失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean verify() {
        String phone = mEditTextPhone.getText().toString().trim();
        String code = mEditTextCode.getText().toString().trim();
        String password = mEditTextPassword.getText().toString().trim();
        String password2 = mEditTextPassword2.getText().toString().trim();
        if (TextUtils.isEmpty(phone)) {
            mTextViewTips.setVisibility(View.VISIBLE);
            mTextViewTips.setText("电话号码不能为空");
            return false;
        } else if (TextUtils.isEmpty(code)) {
            mTextViewTips.setVisibility(View.VISIBLE);
            mTextViewTips.setText("验证码不能为空");
            return false;
        } else if (TextUtils.isEmpty(password)) {
            mTextViewTips.setVisibility(View.VISIBLE);
            mTextViewTips.setText("密码不能为空");
            return false;
        } else if (TextUtils.isEmpty(password2)) {
            mTextViewTips.setVisibility(View.VISIBLE);
            mTextViewTips.setText("重复密码不能为空");
            return false;
        } else if (!password.equals(password2)) {
            mTextViewTips.setVisibility(View.VISIBLE);
            mTextViewTips.setText("两次密码不相同");
            return false;
        }

        return true;
    }

    private void findPassword(){
        mProgressBar.setVisibility(View.VISIBLE);
        Map<String,String> body=new HashMap<>();
        body.put("phone",mEditTextPhone.getText().toString().trim());
        body.put("captcha",mEditTextCode.getText().toString().trim());
        body.put("passWord",mEditTextPassword.getText().toString().trim());
        body.put("type","3");
        RequestUtils.post(UrlUtils.SET_PASSWORD, null, body, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object=new JSONObject(response);
                    mProgressBar.setVisibility(View.GONE);
                    if(object.getLong("status")==2000000){
                        Toast.makeText(ForgetPasswordActivity.this,"重设密码成功",Toast.LENGTH_SHORT).show();
                        finish();
                    }else{
                        Toast.makeText(ForgetPasswordActivity.this,"重设密码失败",Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mProgressBar.setVisibility(View.GONE);
                Toast.makeText(ForgetPasswordActivity.this,"重设密码失败",Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.text_view_back:
                finish();
                break;
            case R.id.button_find_password:
                if(verify()){
                    findPassword();
                }
                break;
            case R.id.text_view_auth_code:
                updateCode();
                mTextViewCode.setEnabled(false);
                getCode();
                break;
        }
    }
}
