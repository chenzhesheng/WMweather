package weather.wm.com.wmweather.account.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import weather.wm.com.wmweather.R;
import weather.wm.com.wmweather.common.bean.User;
import weather.wm.com.wmweather.common.units.RequestUtils;
import weather.wm.com.wmweather.common.units.SharedPreferenceUtils;
import weather.wm.com.wmweather.common.units.UrlUtils;
import weather.wm.com.wmweather.common.view.CircleImageView;
import weather.wm.com.wmweather.common.view.DoubleTextView;

/**
 * Created by HelloKiki on 2017/3/9.
 */

public class AccountFragment extends Fragment implements View.OnClickListener {

    private RelativeLayout mRelativeLayout;
    private DoubleTextView mTextViewSecurity;
    private DoubleTextView mTextViewSetting;
    private DoubleTextView mTextViewNotification;
    private DoubleTextView mTextViewAbout;
    private TextView mTextViewName;
    private TextView mTextViewId;
    private TextView mTextViewPhone;
    private CircleImageView mImageViewAvatar;
    private String uid;
    private User user;

    private AlertDialog mDialogCache;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account, null);
        mRelativeLayout = (RelativeLayout) view.findViewById(R.id.relative_layout_personal);
        mTextViewSecurity = (DoubleTextView) view.findViewById(R.id.text_view_security_center);
        mTextViewSetting = (DoubleTextView) view.findViewById(R.id.text_view_setting);
        mTextViewNotification = (DoubleTextView) view.findViewById(R.id.text_view_notification);
        mTextViewAbout = (DoubleTextView) view.findViewById(R.id.text_view_about);
        mTextViewId = (TextView) view.findViewById(R.id.text_view_id);
        mTextViewPhone = (TextView) view.findViewById(R.id.text_view_phone);
        mTextViewName = (TextView) view.findViewById(R.id.text_view_name);
        mImageViewAvatar = (CircleImageView) view.findViewById(R.id.image_view_avatar);


        view.findViewById(R.id.button_logout).setOnClickListener(this);
        mRelativeLayout.setOnClickListener(this);
        mTextViewSecurity.setOnClickListener(this);
        mTextViewSetting.setOnClickListener(this);
        mTextViewNotification.setOnClickListener(this);
        mTextViewAbout.setOnClickListener(this);

        mTextViewId.setText("id：" + SharedPreferenceUtils.getAccountId(getActivity()));
        mTextViewId.setVisibility(View.INVISIBLE);
        mTextViewPhone.setText("cell：" + SharedPreferenceUtils.getUsername(getActivity()));
        mTextViewName.setText(SharedPreferenceUtils.getUsername(getActivity()));
        initDialog();
        initData();

        return view;
    }

    private void initData() {
        uid = SharedPreferenceUtils.getAccountId(getActivity());
        Map<String, String> header = new HashMap<>();
        header.put("token", UrlUtils.TOKEN);
        Map<String, String> body = new HashMap<>();
        body.put("uid", uid);

        RequestUtils.post(UrlUtils.USER_INFO, header, body, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject result = new JSONObject(response);
                    if(!TextUtils.isEmpty(response) && result.optInt("status") == 2000000){
                        user = new User();
                        user.setUid(result.getJSONObject("data").optString("uid"));
                        user.setBirthday(result.getJSONObject("data").optString("birthday"));
                        user.setMobileno(result.getJSONObject("data").optString("mobileno"));
                        user.setGender(result.getJSONObject("data").optString("gender"));
                        user.setEmail(result.getJSONObject("data").optString("email"));
                        user.setFullName(result.getJSONObject("data").optString("fullName"));
                        user.setPro(result.getJSONObject("data").optString("pro"));
                        user.setCity(result.getJSONObject("data").optString("city"));
                        user.setAvatar(result.getJSONObject("data").optString("avatar"));
                        user.setUserName(result.getJSONObject("data").optString("userName"));

                        if(!TextUtils.isEmpty(user.getAvatar())) {
                            mImageViewAvatar.setImageBitmap(BitmapFactory.decodeFile(user.getAvatar()));
                        }else {
                            mImageViewAvatar.setImageResource(R.drawable.ic_avatar_default);
                        }
                        mTextViewName.setText(user.getFullName());
                        mTextViewId.setText("id："+user.getUid());
                        mTextViewPhone.setText("cell："+user.getMobileno().substring(0,4)+"****"+user.getMobileno().substring(7,11));
                    }else{
                        Toast.makeText(getActivity(), result.getJSONObject("data").optString("message"), Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("2017", "请求错误");
            }
        });
    }

    public void initDialog() {
        mDialogCache = new AlertDialog.Builder(getActivity())
                .setTitle("确定注销账号吗？")
                .setNeutralButton("取消", null)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        SharedPreferenceUtils.clear(getActivity());
                        startActivity(new Intent(getActivity(),LoginActivity.class));
                        getActivity().finish();
                    }
                })
                .create();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.relative_layout_personal:
                Intent intentEdit = new Intent(getActivity(), InformationEditActivity.class);
                if(user!=null){
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("user", user);
                    intentEdit.putExtras(bundle);
                }
                startActivityForResult(intentEdit,10005);
                break;
            case R.id.text_view_security_center:
                Intent intentSecurity = new Intent(getActivity(), SecurityCenterActivity.class);
                startActivity(intentSecurity);
                break;
            case R.id.text_view_setting:
                Intent intentSetting = new Intent(getActivity(), SettingActivity.class);
                startActivity(intentSetting);
                break;
            case R.id.text_view_notification:
                break;
            case R.id.text_view_about:
                break;
            case R.id.button_logout:
                mDialogCache.show();
                break;
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == 10005){
            initData();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
