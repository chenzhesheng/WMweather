package weather.wm.com.wmweather.account.ui;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.io.File;

import weather.wm.com.wmweather.R;
import weather.wm.com.wmweather.common.units.FileUtils;
import weather.wm.com.wmweather.common.view.DoubleTextView;

public class SettingActivity extends AppCompatActivity implements View.OnClickListener {

    private DoubleTextView mTextViewGPS;
    private DoubleTextView mTextViewCache;
    private AlertDialog mDialogCache;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        findViewById(R.id.text_view_back).setOnClickListener(this);
        mTextViewGPS = (DoubleTextView) findViewById(R.id.text_view_gps);
        mTextViewCache = (DoubleTextView) findViewById(R.id.text_view_clear);
        mTextViewGPS.setOnClickListener(this);
        mTextViewCache.setOnClickListener(this);
         initGPS();
        initDialog();
        calculateCacheSize();
    }

    private void calculateCacheSize() {
        long fileSize = 0;
        String cacheSize = "0KB";
        File filesDir = getFilesDir();
        File cacheDir = getCacheDir();

//        fileSize += FileUtils.getDirSize(filesDir);
        fileSize += FileUtils.getDirSize(cacheDir);
        if (fileSize > 0)
            cacheSize = FileUtils.formatFileSize(fileSize);
        mTextViewCache.setValueText(cacheSize);
    }

    public void initDialog() {
        mDialogCache = new AlertDialog.Builder(this)
                .setTitle("确定清理缓存吗？")
                .setNeutralButton("取消", null)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        deleteDir(getCacheDir());
                        if (Environment.getExternalStorageState().equals(
                                Environment.MEDIA_MOUNTED)) {
                            deleteDir(getExternalCacheDir());
                        }
                        calculateCacheSize();
                    }
                })
                .create();
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.text_view_clear:
                mDialogCache.show();
                break;
            case R.id.text_view_back:
                finish();
                break;
            case R.id.text_view_gps:
                Intent intent = new Intent(
                        Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivityForResult(intent, 10000);
                break;
        }
    }

    private boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            int size = 0;
            if (children != null) {
                size = children.length;
                for (int i = 0; i < size; i++) {
                    boolean success = deleteDir(new File(dir, children[i]));
                    if (!success) {
                        return false;
                    }
                }
            }
        }
        if (dir == null) {
            return true;
        } else {
            return dir.delete();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 10000) {
              initGPS();
        }
    }

    private void initGPS() {
        LocationManager locationManager = (LocationManager) this
                .getSystemService(Context.LOCATION_SERVICE);
        if(locationManager==null){
            mTextViewGPS.setValueText("GPS模块出错");
            return;
        }
        // 判断GPS模块是否开启，如果没有则开启
        if (!locationManager.isProviderEnabled(android.location.LocationManager.GPS_PROVIDER)) {
            mTextViewGPS.setValueText("关");
        } else {
            mTextViewGPS.setValueText("开");
        }
    }
}
