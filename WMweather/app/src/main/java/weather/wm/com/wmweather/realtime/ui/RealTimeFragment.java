package weather.wm.com.wmweather.realtime.ui;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMapOptions;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.greenrobot.event.EventBus;
import weather.wm.com.wmweather.MainActivity;
import weather.wm.com.wmweather.R;
import weather.wm.com.wmweather.common.SpaceItemDecoration;
import weather.wm.com.wmweather.common.bean.RealTimeData;
import weather.wm.com.wmweather.common.bean.RealTimeRankData;
import weather.wm.com.wmweather.common.units.RequestUtils;
import weather.wm.com.wmweather.common.units.SharedPreferenceUtils;
import weather.wm.com.wmweather.common.units.StringUtils;
import weather.wm.com.wmweather.common.units.UrlUtils;
import weather.wm.com.wmweather.common.view.FlowLinearLayout;
import weather.wm.com.wmweather.realtime.logic.RealTimeDataAdapter;
import weather.wm.com.wmweather.realtime.logic.RealTimeLogic;

import static weather.wm.com.wmweather.realtime.logic.RealTimeDataAdapter.OnItemClickListener;

/**
 * Created by HelloKiki on 2017/3/9.
 */

public class RealTimeFragment extends Fragment implements View.OnClickListener {

    private final int AQI = 1;
    private final int PM25 = 2;
    private final int PM10 = 3;
    private final int CO = 4;
    private final int NO2 = 5;
    private final int SO2 = 6;
    private final int O3 = 7;

    private BaiduMap mBaiduMap;
    private MapView mMapView;
    private TextView mTextViewScreening;
    private PopupWindow mPopupWindow;
    private PopupWindow mPopupWindow2;
    private LinearLayout mLinearLayoutLocation;
    private FrameLayout mFrameLayout;
    private RecyclerView mRecyclerViewData;
    private RadioGroup mRadioGroupPollutant;
    private RealTimeDataAddressDetailsView mDetailsView;

    private ScaleAnimation mScaleShow;
    private ScaleAnimation mScaleDestroy;
    private RealTimeDataAdapter mAdapter;
    private RealTimeLogic mLogic;
    private List<RealTimeRankData> mRankDatas;
    private List<RealTimeData> mRealTimeList = new ArrayList<>();
    private List<Marker> mMarkers;

    private List<Integer> mPollutantValue;

    private List<String> mAreaIds = new ArrayList<>();

    boolean mIsType = false;
    boolean mIsArea = false;

    private List<String> mAreaParams = new ArrayList<>();
    private List<String> mTypeParams = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_real_time, null);
//        EventBus.getDefault().register(this);
        mTextViewScreening = (TextView) view.findViewById(R.id.text_view_screening);
        mFrameLayout = (FrameLayout) view.findViewById(R.id.frame_layout);
        mRecyclerViewData = (RecyclerView) view.findViewById(R.id.recycler_view_real_time_data);
        mLinearLayoutLocation = (LinearLayout) view.findViewById(R.id.linear_layout_location);
        view.findViewById(R.id.image_view_more).setOnClickListener(this);
        mTextViewScreening.setOnClickListener(this);
        mLogic = new RealTimeLogic(getActivity());
        mMapView = new MapView(getActivity(), new BaiduMapOptions());
        mFrameLayout.addView(mMapView);
        mBaiduMap = mMapView.getMap();
        mMarkers = new ArrayList<>();
        mPollutantValue = new ArrayList<>();
        mRankDatas = new ArrayList<>();

        initRecyclerView();
        initPopupWindow();
        initPopupWindow2();
        initAnimation();
        loadData();
        mBaiduMap.setMapStatus(MapStatusUpdateFactory.newMapStatus(new MapStatus.Builder().zoom(13).build()));
        mBaiduMap.setMaxAndMinZoomLevel(15, 11);
        return view;
    }

    private void initRecyclerView() {
        mAdapter = new RealTimeDataAdapter(getActivity(), mRankDatas);
        mRecyclerViewData.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerViewData.setAdapter(mAdapter);
        mRecyclerViewData.addItemDecoration(new SpaceItemDecoration(10));
        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                mPopupWindow2.showAsDropDown(mTextViewScreening);
                RealTimeData data = mRealTimeList.get(position);
                mDetailsView.setAqi(data.getAqi())
                        .setPM25(data.getPm25())
                        .setPM10(data.getPm10())
                        .setCO(data.getCo())
                        .setSO2(data.getSo2())
                        .setNO2(data.getNo2())
                        .setO3(data.getO3())
                        .setStationName(data.getStationName())
                        .setTime(data.getTime());
                mDetailsView.loadData(data.getStationId());
            }
        });
    }


    private void initPopupWindow() {
        final LayoutInflater inflater = LayoutInflater.from(getActivity());
        View view = inflater.inflate(R.layout.layout_real_time_popupwindow, null);
        mRadioGroupPollutant = (RadioGroup) view.findViewById(R.id.radio_group_real_time_pollutants);
        final FlowLinearLayout areaLayout = (FlowLinearLayout) view.findViewById(R.id.linear_layout_area);
        final FlowLinearLayout typeLayout = (FlowLinearLayout) view.findViewById(R.id.linear_layout_type);
        final LinearLayout rootLayout = (LinearLayout) view.findViewById(R.id.linear_root);
        final ProgressBar progressBar = (ProgressBar) view.findViewById(R.id.progress_bar);
        rootLayout.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        mLogic.loadArea(SharedPreferenceUtils.getCurrentCityId(getActivity()), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object = new JSONObject(response);
                    if (object.getLong("status") == 2000000) {
                        JSONArray array = object.getJSONArray("data");
                        for (int i = 0; i < array.length(); i++) {
                            CheckBox checkBox = (CheckBox) inflater.inflate(R.layout.layout_check_box1, null);
                            checkBox.setText(array.getJSONObject(i).getString("name"));
                            checkBox.setId(array.getJSONObject(i).getInt("id"));
                            checkBox.setOnCheckedChangeListener(new OnAreaCheckBoxListener());
                            areaLayout.addView(checkBox);
                        }
                        mIsArea = true;
                        if (mIsType) {
                            rootLayout.setVisibility(View.VISIBLE);
                            progressBar.setVisibility(View.GONE);
                        }

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, null);
        mLogic.loadType(((MainActivity) getActivity()).getCityId(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object = new JSONObject(response);
                    if (object.getLong("status") == 2000000) {
                        JSONArray array = object.getJSONArray("data");
                        for (int i = 0; i < array.length(); i++) {
                            CheckBox checkBox = (CheckBox) inflater.inflate(R.layout.layout_check_box1, null);
                            checkBox.setText(array.getJSONObject(i).getString("name"));
                            checkBox.setId(array.getJSONObject(i).getInt("id"));
                            checkBox.setOnCheckedChangeListener(new OnTypeCheckBoxListener());
                            typeLayout.addView(checkBox);
                        }
                        mIsType = true;
                        if (mIsArea) {
                            rootLayout.setVisibility(View.VISIBLE);
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, null);
        mRadioGroupPollutant.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                radioGroupSelect();
                drawMarker();
            }
        });
        mPopupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
        mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                loadData();
            }
        });
    }

    private void initPopupWindow2() {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.layout_real_time_data_address_details_view, null);
        mDetailsView = (RealTimeDataAddressDetailsView) view.findViewById(R.id.real_time_data_details_view);
        mPopupWindow2 = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mPopupWindow2.setOutsideTouchable(true);
        mPopupWindow2.setBackgroundDrawable(new BitmapDrawable());

    }

    private void radioGroupSelect() {
        mPollutantValue.clear();
        switch (mRadioGroupPollutant.getCheckedRadioButtonId()) {
            case R.id.radio_button_aqi:
                mPollutantValue.addAll(mLogic.getValueFormAQI());
                break;
            case R.id.radio_button_PM25:
                mPollutantValue.addAll(mLogic.getValueFormPM25());
                break;
            case R.id.radio_button_PM10:
                mPollutantValue.addAll(mLogic.getValueFormPM10());
                break;
            case R.id.radio_button_CO:
                mPollutantValue.addAll(mLogic.getValueFormCO());
                break;
            case R.id.radio_button_NO2:
                mPollutantValue.addAll(mLogic.getValueFormNO2());
                break;
            case R.id.radio_button_SO2:
                mPollutantValue.addAll(mLogic.getValueFormSO2());
                break;
            case R.id.radio_button_O3:
                mPollutantValue.addAll(mLogic.getValueFormO3());
                break;
        }
    }


    private void initAnimation() {
        mScaleShow = new ScaleAnimation(0, 1, 1, 1, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 1f);
        mScaleShow.setDuration(400);
        mScaleDestroy = new ScaleAnimation(1, 0, 1, 1, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 1f);
        mScaleDestroy.setDuration(400);

        mScaleDestroy.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (mFrameLayout.getVisibility() == View.VISIBLE) {
                    mFrameLayout.setVisibility(View.INVISIBLE);
                    mLinearLayoutLocation.setVisibility(View.VISIBLE);
                    mLinearLayoutLocation.startAnimation(mScaleShow);
                } else {
                    mLinearLayoutLocation.setVisibility(View.INVISIBLE);
                    mFrameLayout.setVisibility(View.VISIBLE);
                    mFrameLayout.startAnimation(mScaleShow);
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

    }

    private void loadData() {
        Map<String, String> header = new HashMap<>();
        header.put("token", UrlUtils.TOKEN);
        Map<String, String> body = new HashMap<>();
        body.put("city", SharedPreferenceUtils.getCurrentCityName(getActivity()));
        body.put("area", StringUtils.listToString(mAreaParams));
        body.put("type", StringUtils.listToString(mTypeParams));
        Log.e("2017", "area->" + StringUtils.listToString(mAreaParams));
        Log.e("2017", "type->" + StringUtils.listToString(mTypeParams));


        RequestUtils.post(UrlUtils.REAL_TIME_DATA, header, body, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("2017", "response-> " + response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getLong("status") == 2000000) {
                        mRealTimeList.clear();
                        JSONArray array = jsonObject.getJSONArray("data");
                        for (int i = 0; i < array.length(); i++) {
                            RealTimeData realTimeData = new RealTimeData();
                            JSONObject data = array.getJSONObject(i);
                            realTimeData.setPm25(data.getInt("pm25"));
                            realTimeData.setPm10(data.getInt("pm10"));
                            realTimeData.setSo2(data.getInt("so2"));
                            realTimeData.setO3(data.getInt("o3"));
                            realTimeData.setCo(data.getInt("co"));
                            realTimeData.setNo2(data.getInt("no2"));
                            realTimeData.setAqi(data.getInt("aqi"));
                            realTimeData.setAqiFirst(data.getInt("aqiFirst"));
                            realTimeData.setTime(data.getString("time"));
                            realTimeData.setStationName(data.getString("stationName"));
                            realTimeData.setLat(data.getDouble("lat"));
                            realTimeData.setLng(data.getDouble("lng"));
                            realTimeData.setIndex(data.getString("index"));
                            realTimeData.setStationId(data.getString("stationId"));
                            mRealTimeList.add(realTimeData);
                        }
                        mLogic.saveData(mRealTimeList);
                        radioGroupSelect();
                        drawMarker();

                    } else {
                        Toast.makeText(getActivity(), "数据错误", Toast.LENGTH_SHORT).show();
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

    public void drawMarker() {
        for (Marker marker : mMarkers) {
            marker.remove();
        }
        mMarkers.clear();
        mRankDatas.clear();

        for (int i = 0; i < mRealTimeList.size(); i++) {
            RealTimeRankData rankData = new RealTimeRankData();
            rankData.setRank(i);
            rankData.setStationName(mRealTimeList.get(i).getStationName());
            rankData.setValue(mPollutantValue.get(i));
            mRealTimeList.get(i).setPollutant(mPollutantValue.get(i));
            mRankDatas.add(rankData);

            LatLng point = new LatLng(mRealTimeList.get(i).getLat(), mRealTimeList.get(i).getLng());//构建Marker图标
            Bitmap bitmap = mLogic.createBitmap(mPollutantValue.get(i));
            BitmapDescriptor bmp = BitmapDescriptorFactory
                    .fromBitmap(bitmap);//构建MarkerOption，用于在地图上添加Marker
            OverlayOptions option = new MarkerOptions()
                    .position(point)
                    .icon(bmp);//在地图上添加Marker，并显示
            mMarkers.add((Marker) mBaiduMap.addOverlay(option));
        }
        Collections.sort(mRankDatas);
        Collections.sort(mRealTimeList);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.image_view_more:
                if (mFrameLayout.getVisibility() == View.VISIBLE) {
                    mFrameLayout.startAnimation(mScaleDestroy);
                } else {
                    mLinearLayoutLocation.startAnimation(mScaleDestroy);
                }

                break;
            case R.id.text_view_screening:
                if (mPopupWindow == null) {
                    Toast.makeText(getActivity(), "加载数据中...", Toast.LENGTH_SHORT).show();
                } else {
                    mPopupWindow.showAsDropDown(mTextViewScreening, Gravity.CENTER, 0);
                }

                break;
        }

    }

//    @Subscribe
//    public void onEventMainThread(CityEvent event){
//        Log.e("2017","city_choose_2");
//        initPopupWindow();
//        loadData();
//    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onPause();
        EventBus.getDefault().unregister(this);
    }

    class OnAreaCheckBoxListener implements CompoundButton.OnCheckedChangeListener {

        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            if (b) {
                mAreaParams.add(compoundButton.getId() + "");
            } else {
                mAreaParams.remove(compoundButton.getId() + "");
            }

        }
    }

    class OnTypeCheckBoxListener implements CompoundButton.OnCheckedChangeListener {

        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            if (b) {
                mTypeParams.add(compoundButton.getId() + "");
            } else {
                mTypeParams.remove(compoundButton.getId() + "");
            }

        }
    }

}
