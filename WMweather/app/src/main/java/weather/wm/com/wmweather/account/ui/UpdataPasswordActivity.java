package weather.wm.com.wmweather.account.ui;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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


/**
 * Created by ChenZheSheng on 2017/4/9.
 */

public class UpdataPasswordActivity extends Activity implements View.OnClickListener{
    private TextView mTextViewBack;
    private EditText mEditTextOdlPassword;
    private EditText mEditTextNewPassword;
    private EditText mEditTextRepeatPassword;
    private TextView mTextViewTips;
    private Button mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updata_password);

        mTextViewBack = (TextView) findViewById(R.id.text_view_back);
        mEditTextOdlPassword = (EditText) findViewById(R.id.edit_text_odl_password);
        mEditTextNewPassword = (EditText) findViewById(R.id.edit_text_new_password);
        mEditTextRepeatPassword = (EditText) findViewById(R.id.edit_text_repeat_password);
        mTextViewTips = (TextView) findViewById(R.id.text_view_tips);
        mButton = (Button) findViewById(R.id.button_find_password);

        mTextViewBack.setOnClickListener(this);
        mButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.text_view_back:
                finish();
                break;
            case R.id.button_find_password:
                String odlPassword = mEditTextOdlPassword.getText().toString();
                String newPassword = mEditTextNewPassword.getText().toString();
                String repeatPassword = mEditTextRepeatPassword.getText().toString();
                if(TextUtils.isEmpty(odlPassword)||TextUtils.isEmpty(newPassword)||TextUtils.isEmpty(repeatPassword)){
                    mTextViewTips.setVisibility(View.VISIBLE);
                    mTextViewTips.setText("密码不能为空");
                    return;
                }
                if(odlPassword.length()<6||newPassword.length()<6||repeatPassword.length()<6){
                    mTextViewTips.setVisibility(View.VISIBLE);
                    mTextViewTips.setText("密码长度不能小于6位数");
                    return;
                }
                if(!repeatPassword.equals(newPassword)){
                    mTextViewTips.setVisibility(View.VISIBLE);
                    mTextViewTips.setText("新密码确认不一致");
                    return;
                }
                mTextViewTips.setVisibility(View.INVISIBLE);
                updataPassword(odlPassword,newPassword);
                break;
        }
    }

    private void updataPassword(String odlPassword, String newPassword) {
        Map<String, String> header = new HashMap<>();
        Log.i("TAG","sessionId:"+SharedPreferenceUtils.getSessionId(this));
        header.put("sessionId", SharedPreferenceUtils.getSessionId(this));
        Map<String, String> body = new HashMap<>();
        body.put("prepassword", odlPassword);
        body.put("password", newPassword);

        RequestUtils.post(UrlUtils.USER_UPDATA_PASSWORD, header, body, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject result = new JSONObject(response);
                    if(!TextUtils.isEmpty(response) && result.optInt("status") == 2000000){
                        mTextViewTips.setVisibility(View.VISIBLE);
                        mTextViewTips.setText("修改成功");
                    }else{
                        mTextViewTips.setVisibility(View.VISIBLE);
                        mTextViewTips.setText(result.optString("message"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mTextViewTips.setVisibility(View.VISIBLE);
                mTextViewTips.setText("修改失败");
                Log.e("2017", "请求错误"+error.getMessage());
            }
        });
    }
}
