package weather.wm.com.wmweather.account.ui;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import weather.wm.com.wmweather.R;
import weather.wm.com.wmweather.account.logic.ClickItemLIstener;
import weather.wm.com.wmweather.account.logic.UpdataListAdapter;
import weather.wm.com.wmweather.common.bean.User;
import weather.wm.com.wmweather.common.bean.area;
import weather.wm.com.wmweather.common.bean.city;
import weather.wm.com.wmweather.common.units.CameraUtils;
import weather.wm.com.wmweather.common.units.RequestUtils;
import weather.wm.com.wmweather.common.units.SharedPreferenceUtils;
import weather.wm.com.wmweather.common.units.UrlUtils;
import weather.wm.com.wmweather.common.view.CircleImageView;
import weather.wm.com.wmweather.common.view.DoubleTextView;

public class InformationEditActivity extends AppCompatActivity implements View.OnClickListener ,ClickItemLIstener {
    private RelativeLayout mRelativeLayoutAvatar;
    private CircleImageView mImageViewAvater;
    private DoubleTextView mDoubleTextViewUserName;
    private DoubleTextView mDoubleTextViewGenter;
    private DoubleTextView mDoubleTextViewMobileno;
    private DoubleTextView mDoubleTextViewPro;
    private DoubleTextView mDoubleTextViewCity;
    private DoubleTextView mDoubleTextViewEmail;
    private Button mButtonConmit;
    private User mUser;
    private String avatarPath;
    private PopupWindow mPopupWindow;
    private EditText mEditText;
    private Button mButtonConfirm;
    private Button mButtonCancel;
    private PopupWindow mPopupWindowList;
    private RecyclerView mRecyclerViewList;
    private UpdataListAdapter mAdapter;
    private ArrayList<String> mDatas;
    private ArrayList<city> mCities;
    private ArrayList<area> mAreas;
    private int position = 0;
    private int clickId = 0;
    private boolean isCamera = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information_edit);
        findViewById(R.id.text_view_back).setOnClickListener(this);

        mRelativeLayoutAvatar = (RelativeLayout) findViewById(R.id.relative_layout_avatar);
        mImageViewAvater = (CircleImageView) findViewById(R.id.image_view_avatar);
        mDoubleTextViewUserName = (DoubleTextView) findViewById(R.id.text_view_name);
        mDoubleTextViewGenter = (DoubleTextView) findViewById(R.id.text_view_sex);
        mDoubleTextViewMobileno = (DoubleTextView) findViewById(R.id.text_view_phone);
        mDoubleTextViewPro = (DoubleTextView) findViewById(R.id.text_view_area);
        mDoubleTextViewCity = (DoubleTextView) findViewById(R.id.text_view_city);
        mDoubleTextViewEmail = (DoubleTextView) findViewById(R.id.text_view_email);
        mButtonConmit = (Button) findViewById(R.id.button_logout);
        mButtonConmit.setOnClickListener(this);
        mDoubleTextViewUserName.setOnClickListener(this);
        mDoubleTextViewGenter.setOnClickListener(this);
        mDoubleTextViewMobileno.setOnClickListener(this);
        mDoubleTextViewPro.setOnClickListener(this);
        mDoubleTextViewCity.setOnClickListener(this);
        mDoubleTextViewEmail.setOnClickListener(this);
        mRelativeLayoutAvatar.setOnClickListener(this);

        mUser = (User) getIntent().getSerializableExtra("user");
        if(mUser == null){
            return;
        }
        if(!TextUtils.isEmpty(mUser.getAvatar())) {
            mImageViewAvater.setImageBitmap(BitmapFactory.decodeFile(mUser.getAvatar()));
        }else {
            mImageViewAvater.setImageResource(R.drawable.ic_avatar_default);
        }
        mDoubleTextViewUserName.setValueText(mUser.getFullName());
        mDoubleTextViewGenter.setValueText(mUser.getGender());
        mDoubleTextViewMobileno.setValueText(mUser.getMobileno());
        mDoubleTextViewPro.setValueText(mUser.getPro());
        mDoubleTextViewCity.setValueText(mUser.getCity());
        mDoubleTextViewEmail.setValueText(mUser.getEmail());

        initPopupWindow();
        initPopupWindowList();
        mDoubleTextViewPro.setVisibility(View.GONE);
        mDoubleTextViewCity.setVisibility(View.GONE);
    }


    private void initPopupWindow() {
        View view = LayoutInflater.from(this).inflate(R.layout.layout_edit_popupwindow, null);
        mPopupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        mEditText = (EditText) view.findViewById(R.id.edit_text_name);
        mButtonConfirm = (Button) view.findViewById(R.id.button_ok);
        mButtonCancel = (Button) view.findViewById(R.id.button_no);
        mButtonConfirm.setOnClickListener(this);
        mButtonCancel.setOnClickListener(this);
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
    }

    private void initPopupWindowList() {
        View view = LayoutInflater.from(this).inflate(R.layout.layout_list_popupwindow, null);
        mPopupWindowList = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        mRecyclerViewList = (RecyclerView) view.findViewById(R.id.recycler_view_information_edit);
        mRecyclerViewList.setLayoutManager(new GridLayoutManager(this,1));
        mDatas = new ArrayList<>();
        mAreas = new ArrayList<area>();
        mCities = new ArrayList<city>();
        mAdapter = new UpdataListAdapter(this,mDatas,0);
        mAdapter.setClickItemLIstener(this);
        mRecyclerViewList.setAdapter(mAdapter);
        mPopupWindowList.setBackgroundDrawable(new BitmapDrawable());
        mPopupWindowList.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                mDatas = new ArrayList<String>();
                mAreas = new ArrayList<area>();
                mCities = new ArrayList<city>();
                mAdapter.notifyDataSetChanged();
            }
        });
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.text_view_back:
                finish();
                break;
            case R.id.relative_layout_avatar:
                if(isCamera) {
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                    CameraUtils.selectImage(this);
                    isCamera = false;
                }
                break;
            case R.id.button_ok:
                switch (position){
                    case 1:
                        mDoubleTextViewUserName.setValueText(mEditText.getText().toString());
                        break;
                    case 2:
                        mDoubleTextViewMobileno.setValueText(mEditText.getText().toString());
                        break;
                    case 3:
                        mDoubleTextViewEmail.setValueText(mEditText.getText().toString());
                        break;
                }
            case R.id.button_no:
                mPopupWindow.dismiss();
                mEditText.setText("");
                break;
            case R.id.text_view_name:
                mPopupWindow.showAsDropDown(mDoubleTextViewUserName);
                position = 1;
                break;
            case R.id.text_view_phone:
                mPopupWindow.showAsDropDown(mDoubleTextViewMobileno);
                position = 2;
                break;
            case R.id.text_view_email:
                mPopupWindow.showAsDropDown(mDoubleTextViewEmail);
                position = 3;
                break;
            case R.id.text_view_sex:
                mDatas.add("男");
                mDatas.add("女");
                mAdapter = new UpdataListAdapter(InformationEditActivity.this,mDatas,0);
                mAdapter.setClickItemLIstener(InformationEditActivity.this);
                mRecyclerViewList.setAdapter(mAdapter);
                mPopupWindowList.showAsDropDown(mDoubleTextViewGenter);
                break;
            case R.id.text_view_area:
                getAreaList();
                break;
            case R.id.text_view_city:
                if(clickId != 0) {
                    getCityList("" + clickId);
                }else {
                    Toast.makeText(this, "请先选择区域", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.button_logout:
                Commit();
                break;
        }
    }

    private void Commit() {
        String userName = mDoubleTextViewUserName.getValueText();
        String genter = mDoubleTextViewGenter.getValueText();
        String mobileNo = mDoubleTextViewMobileno.getValueText();
//        String area = mDoubleTextViewPro.getValueText();
//        String city = mDoubleTextViewCity.getValueText();
        String email = mDoubleTextViewEmail.getValueText();
        Map<String,String> header = new HashMap<>();
        header.put("token",UrlUtils.TOKEN);
        Map<String,String> body = new HashMap<>();
        body.put("uid", SharedPreferenceUtils.getAccountId(this));
        body.put("gender",genter);
        body.put("email",email);
//        body.put("birthday","20161010");
        body.put("fullName",userName);
        RequestUtils.post(UrlUtils.USER_UPDATA, header, body, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject result = new JSONObject(response);
                    if(!TextUtils.isEmpty(response)&&result.optInt("status")==2000000){
                        Toast.makeText(InformationEditActivity.this, "保存成功", Toast.LENGTH_SHORT).show();
                        setResult(10005);
                    }else{
                        Toast.makeText(InformationEditActivity.this, result.optString("message"), Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("2017","error:"+error.getMessage());
                Toast.makeText(InformationEditActivity.this, "保存失败", Toast.LENGTH_SHORT).show();
            }
        });
    }


    /**
     * 修改头像
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null || data.getData() == null || resultCode == Activity.RESULT_CANCELED)
            return;
        switch (requestCode) {
            case CameraUtils.GET_IMAGE_BY_CAMERA:
                Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                avatarPath = CameraUtils.saveBitmap(bitmap);
                break;
            case CameraUtils.GET_IMAGE_FROM_PHONE:
                avatarPath = CameraUtils.saveBitmap(CameraUtils.getRealPathFromURI(data.getData(), this));
                break;
        }
        // 上传图片
//        updateAvatar(avatarPath);
        mImageViewAvater.setImageBitmap(BitmapFactory.decodeFile(avatarPath));
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     *  上传图片 TODO
     * @param path
     */
    private void updateAvatar(String path){
            File file = new File(path);
            RequestBody fileBody = RequestBody.create(MediaType.parse("application/octet-stream"), file);
            RequestBody requestBody = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("image", new Date().getTime()+".jpg", fileBody)
                    .build();
            Request request = new Request.Builder()
                    .url(UrlUtils.USER_UPDATA_AVATAR)
                    .post(requestBody)
                    .build();

            final okhttp3.OkHttpClient.Builder httpBuilder = new OkHttpClient.Builder();
            OkHttpClient okHttpClient  = httpBuilder
                    //设置超时
                    .connectTimeout(10, TimeUnit.SECONDS)
                    .writeTimeout(15, TimeUnit.SECONDS)
                    .build();
            okHttpClient.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Log.e("2017", "uploadMultiFile() e=" + e);
                }

                @Override
                public void onResponse(Call call, okhttp3.Response response) throws IOException {
                    Log.i("2017", "uploadMultiFile() response=" + response.body().string());
                    mImageViewAvater.setImageBitmap(BitmapFactory.decodeFile(avatarPath));
                }
            });
        }


    private void getAreaList(){
        if(mAreas.size()>0){
            mAdapter = new UpdataListAdapter(InformationEditActivity.this,mAreas,1);
            mRecyclerViewList.setAdapter(mAdapter);
            return;
        }
        final List<area> areasList = new ArrayList<>();
        Map<String,String> header = new HashMap<>();
        header.put("token", UrlUtils.TOKEN);
        RequestUtils.post(UrlUtils.PROVINCE, header, null, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject result = new JSONObject(response);
                    if(result!=null&&result.optInt("status")==2000000){
                        area areas = new area();
                        for (int i = 0;i<result.getJSONArray("data").length();i++){
                            areas.setId(result.getJSONArray("data").getJSONObject(i).getInt("id"));
                            areas.setName(result.getJSONArray("data").getJSONObject(i).getString("name"));
                            areasList.add(areas);
                        }
                        mAreas.addAll(areasList);
                        mAdapter = new UpdataListAdapter(InformationEditActivity.this,mAreas,1);
                        mAdapter.setClickItemLIstener(InformationEditActivity.this);
                        mRecyclerViewList.setAdapter(mAdapter);
                        mPopupWindowList.showAsDropDown(mDoubleTextViewPro);
                    }else{
                        Toast.makeText(InformationEditActivity.this, result.getString("message"), Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(InformationEditActivity.this, "获取数据失败", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void getCityList(String provinceId){
        if(mCities.size()>0){
            mAdapter = new UpdataListAdapter(InformationEditActivity.this,mCities,2);
            mRecyclerViewList.setAdapter(mAdapter);
            return;
        }
        final List<city> citysList = new ArrayList<>();
        Map<String,String> header = new HashMap<>();
        header.put("token", UrlUtils.TOKEN);
        Map<String,String> body = new HashMap<>();
        body.put("province", provinceId);
        RequestUtils.post(UrlUtils.CITY, header, body, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject result = new JSONObject(response);
                    if(result!=null&&result.optInt("status")==2000000){
                        city citys = new city();
                        for (int i = 0;i<result.getJSONArray("data").length();i++){
                            citys.setId(result.getJSONArray("data").getJSONObject(i).getInt("id"));
                            citys.setName(result.getJSONArray("data").getJSONObject(i).getString("name"));
                            citysList.add(citys);
                        }
                        mCities.addAll(citysList);
                        mAdapter = new UpdataListAdapter(InformationEditActivity.this,mCities,2);
                        mAdapter.setClickItemLIstener(InformationEditActivity.this);
                        mRecyclerViewList.setAdapter(mAdapter);
                        mPopupWindowList.showAsDropDown(mDoubleTextViewPro);
                    }else{
                        Toast.makeText(InformationEditActivity.this, result.getString("message"), Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(InformationEditActivity.this, "获取数据失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void setClickItemListener(int position, int number) {
        switch (number){
            case 0:
                if(position == 0) {
                    mDoubleTextViewGenter.setValueText("男");
                }else{
                    mDoubleTextViewGenter.setValueText("女");
                }
                break;
            case 1:
                mDoubleTextViewPro.setValueText(mAreas.get(position).getName());
                clickId = mAreas.get(position).getId();
                break;
            case 2:
                mDoubleTextViewCity.setValueText(mCities.get(position).getName());
                break;
        }
        mPopupWindowList.dismiss();
    }
}
