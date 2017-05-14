package weather.wm.com.wmweather.account.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

import de.greenrobot.event.EventBus;
import weather.wm.com.wmweather.MainActivity;
import weather.wm.com.wmweather.R;
import weather.wm.com.wmweather.common.units.RequestUtils;
import weather.wm.com.wmweather.common.units.SharedPreferenceUtils;
import weather.wm.com.wmweather.common.units.UrlUtils;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText mEditTextPhone;
    private EditText mEditTextPassword;
    private TextView mTextViewTips;
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
//        EventBus.getDefault().register(this);
        mEditTextPhone = (EditText) findViewById(R.id.edit_text_phone);
        mEditTextPassword = (EditText) findViewById(R.id.edit_text_password);
        mTextViewTips = (TextView) findViewById(R.id.text_view_tips);
        mProgressBar= (ProgressBar) findViewById(R.id.progress_bar);

        findViewById(R.id.button_login).setOnClickListener(this);
        findViewById(R.id.text_view_forget_password).setOnClickListener(this);
        findViewById(R.id.text_view_quick_login).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_login:
                if (verify()) {
                    login();
                }
                break;
            case R.id.text_view_forget_password:
                startActivity(new Intent(this, ForgetPasswordActivity.class));
                break;
            case R.id.text_view_quick_login:
                startActivity(new Intent(this, QuickLoginActivity.class));
                break;
        }
    }

    private void login() {
        mProgressBar.setVisibility(View.VISIBLE);
        Map<String, String> body = new HashMap<>();
        body.put("phone", mEditTextPhone.getText().toString());
        body.put("password", mEditTextPassword.getText().toString());
        RequestUtils.post(UrlUtils.LOGIN, null, body, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                mProgressBar.setVisibility(View.GONE);
                try {
                    JSONObject object = new JSONObject(response);
                    if (object.getLong("status") == 2000000) {
                        JSONObject json = object.getJSONObject("data");
                        SharedPreferenceUtils.setAccountId(LoginActivity.this, json.getString("id"));
                        SharedPreferenceUtils.setUserName(LoginActivity.this, json.getString("username"));
                        SharedPreferenceUtils.setStatus(LoginActivity.this, json.getString("status"));
                        SharedPreferenceUtils.setSessionId(LoginActivity.this, json.getString("sessionId"));
                       startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        finish();
                    } else {
                        Toast.makeText(LoginActivity.this, object.optString("message"), Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mProgressBar.setVisibility(View.GONE);
                Toast.makeText(LoginActivity.this, "登录失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean verify() {
        String phone = mEditTextPhone.getText().toString();
        String password = mEditTextPassword.getText().toString();
        if (TextUtils.isEmpty(phone)) {
            mTextViewTips.setVisibility(View.VISIBLE);
            mTextViewTips.setText("电话号码不能为空");
            return false;
        } else if (TextUtils.isEmpty(password)) {
            mTextViewTips.setVisibility(View.VISIBLE);
            mTextViewTips.setText("密码不能为空");
            return false;
        }
        return true;
    }

//    public void onEvent(LoginEvent event) {
//        finish();
//    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
