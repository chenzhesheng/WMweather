package weather.wm.com.wmweather;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.RadioGroup;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.baidu.mapapi.SDKInitializer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import weather.wm.com.wmweather.account.ui.AccountFragment;
import weather.wm.com.wmweather.common.units.RequestUtils;
import weather.wm.com.wmweather.common.units.UrlUtils;
import weather.wm.com.wmweather.common.view.NoScrollViewPager;
import weather.wm.com.wmweather.history.ui.HistoryFragment;
import weather.wm.com.wmweather.home.ui.HomeFragment;
import weather.wm.com.wmweather.realtime.ui.RealTimeFragment;
import weather.wm.com.wmweather.statistical.StatisticalFragment;

public class MainActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener {

    private NoScrollViewPager mViewPager;
    private RadioGroup mRadioGroup;
    private LinearLayout mRoot;

    private SDKReceiver mReceiver;

    private List<String> mCityIds = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mViewPager = (NoScrollViewPager) findViewById(R.id.view_pager);
        mRadioGroup = (RadioGroup) findViewById(R.id.radio_group_main);
        mRoot = (LinearLayout) findViewById(R.id.root);
        MainFragmentAdapter fragmentAdapter = new MainFragmentAdapter(getSupportFragmentManager());
        mViewPager.setOffscreenPageLimit(5);
        mViewPager.setAdapter(fragmentAdapter);
        mViewPager.addOnPageChangeListener(new PagerChangeListener());
        mRadioGroup.setOnCheckedChangeListener(this);

        // 注册 SDK 广播监听者
        IntentFilter iFilter = new IntentFilter();
        iFilter.addAction(SDKInitializer.SDK_BROADTCAST_ACTION_STRING_PERMISSION_CHECK_OK);
        iFilter.addAction(SDKInitializer.SDK_BROADTCAST_ACTION_STRING_PERMISSION_CHECK_ERROR);
        iFilter.addAction(SDKInitializer.SDK_BROADCAST_ACTION_STRING_NETWORK_ERROR);
        mReceiver = new SDKReceiver();
        registerReceiver(mReceiver, iFilter);
        loadCity("110000");
    }

    public class SDKReceiver extends BroadcastReceiver {

        public void onReceive(Context context, Intent intent) {
            String s = intent.getAction();
            if (s.equals(SDKInitializer.SDK_BROADTCAST_ACTION_STRING_PERMISSION_CHECK_ERROR)) {

                Log.e("2017", "key 验证出错! 错误码 :" + intent.getIntExtra
                        (SDKInitializer.SDK_BROADTCAST_INTENT_EXTRA_INFO_KEY_ERROR_CODE, 0)
                        + " ; 请在 AndroidManifest.xml 文件中检查 key 设置");

            } else if (s.equals(SDKInitializer.SDK_BROADTCAST_ACTION_STRING_PERMISSION_CHECK_OK)) {
                Log.e("2017", "key 验证成功! 功能可以正常使用");
            } else if (s.equals(SDKInitializer.SDK_BROADCAST_ACTION_STRING_NETWORK_ERROR)) {
                Log.e("2017", "网络出错");
            }
        }
    }

    public void loadCity(String provinceId) {
        Map<String, String> header = new HashMap<>();
        header.put("token", UrlUtils.TOKEN);
        Map<String, String> body = new HashMap<>();
        body.put("province", provinceId);
        RequestUtils.post(UrlUtils.CITY, header, body, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getLong("status") == 2000000) {
                        JSONArray array = jsonObject.getJSONArray("data");
                        for (int i = 0; i < array.length(); i++) {
                            mCityIds.add(array.getJSONObject(i).getInt("id") + "");
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
    }

    public String getCityId(){
        if(mCityIds.size()>0){
            return mCityIds.get(0);
        }
        return "110100";
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        switch (mRadioGroup.getCheckedRadioButtonId()) {
            case R.id.radio_button_home:
                mViewPager.setCurrentItem(0);
                mRoot.setBackgroundResource(R.drawable.bg_home_test);
                break;
            case R.id.radio_button_real_time:
                mViewPager.setCurrentItem(1);
                mRoot.setBackgroundResource(0);
                break;
            case R.id.radio_button_history:
                mViewPager.setCurrentItem(2);
                mRoot.setBackgroundResource(0);
                break;
            case R.id.radio_button_statistical:
                mViewPager.setCurrentItem(3);
                mRoot.setBackgroundResource(0);
                break;
            case R.id.radio_button_account:
                mViewPager.setCurrentItem(4);
                mRoot.setBackgroundResource(0);
                break;
        }
    }

    class PagerChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            switch (position) {
                case 0:
                    mRadioGroup.check(R.id.radio_button_home);
                    break;
                case 1:
                    mRadioGroup.check(R.id.radio_button_real_time);
                    break;
                case 2:
                    mRadioGroup.check(R.id.radio_button_history);
                    break;
                case 3:
                    mRadioGroup.check(R.id.radio_button_statistical);
                    break;
                case 4:
                    mRadioGroup.check(R.id.radio_button_account);
                    break;
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

    class MainFragmentAdapter extends FragmentStatePagerAdapter {

        public MainFragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new HomeFragment();
                case 1:
                    return new RealTimeFragment();
                case 2:
                    return new HistoryFragment();
                case 3:
                    return new StatisticalFragment();
                case 4:
                    return new AccountFragment();
            }
            return null;
        }

        @Override
        public int getCount() {
            return 5;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mReceiver);
    }
}
