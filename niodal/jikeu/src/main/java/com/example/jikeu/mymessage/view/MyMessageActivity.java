package com.example.jikeu.mymessage.view;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jikeu.R;
import com.example.jikeu.base.utils.CheckUtils;
import com.example.jikeu.base.utils.HeadTitleUtil;
import com.example.jikeu.base.utils.ImageUtils;
import com.example.jikeu.base.utils.IntentUtils;
import com.example.jikeu.base.utils.SharedPreferencesUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ChenZheSheng on 2016/8/14.
 */
public class MyMessageActivity extends Activity {
    private String image;
    private String name;
    private String number;
    @BindView(R.id.ivMessageShoto)
    ImageView mIvMessageShoto;
    @BindView(R.id.tvMessageName)
    TextView mtvName;
    @BindView(R.id.tvMessageNumber)
    TextView mtvNumber;
    @BindView(R.id.llMessageShoto)
    LinearLayout mLlMessageShoto;
    @BindView(R.id.ll_message_my_name)
    LinearLayout mLlMessageMyName;
    @BindView(R.id.ll_message_my_access)
    LinearLayout mLlMessageMyAccess;
    @BindView(R.id.ll_message_my_contact)
    LinearLayout mLlMessageMyContact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_message);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        HeadTitleUtil.setTextHead(this, "我的资料");
        image = (String) SharedPreferencesUtils.getParam(this, "userimage", "");
        if (!"".equals(image) && image != null) {
            mIvMessageShoto.setImageBitmap(BitmapFactory.decodeFile(image));
        }
        name = (String) SharedPreferencesUtils.getParam(this, "username", "");
        if (!"".equals(name) && name != null) {
            mtvName.setText(name);
        }
        number = (String) SharedPreferencesUtils.getParam(this, "usernumber", "");
        if (!"".equals(number) && number != null) {
            mtvNumber.setText(number);
        }
    }

    @OnClick({R.id.ivMessageShoto, R.id.ll_message_my_name, R.id.ll_message_my_access, R.id.ll_message_my_contact,R.id.llMessageShoto})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.llMessageShoto:
                ImageUtils.selectImage(this);
                break;
            case R.id.ll_message_my_name:
                IntentUtils.startActivity(this,AlterNameActivity.class);
                break;
            case R.id.ll_message_my_access:
                IntentUtils.startActivity(this,AddressActivity.class);
                break;
            case R.id.ll_message_my_contact:
                IntentUtils.startActivity(this,AlterContactActivity.class);
                break;
        }
    }

    /**
     * 修改头像
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null || resultCode == Activity.RESULT_CANCELED)
            return;
        switch (requestCode) {
            case ImageUtils.GET_IMAGE_BY_CAMERA:
                Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                image = ImageUtils.saveBitmap(bitmap);
                break;
            case ImageUtils.GET_IMAGE_FROM_PHONE:
                image = ImageUtils.saveBitmap(ImageUtils.getRealPathFromURI(
                        data.getData(), this));
                break;
        }
        if (!CheckUtils.checkNetworkState(this)) {
            Toast.makeText(this, getResources().getString(R.string.no_network), Toast.LENGTH_SHORT).show();
            return;
        }
        if (!android.os.Environment.getExternalStorageState().equals(
                android.os.Environment.MEDIA_MOUNTED)) {
            Toast.makeText(this, getResources().getString(R.string.sd_isexist), Toast.LENGTH_SHORT).show();
            return;
        }
        // 上传图片
        SharedPreferencesUtils.setParam(this,"userimage",image);
        mIvMessageShoto.setImageBitmap(BitmapFactory.decodeFile(image));
    }
}
