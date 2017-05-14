package weather.wm.com.wmweather.history.ui;

import android.app.DatePickerDialog;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.Gradient;
import com.baidu.mapapi.map.HeatMap;
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
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import weather.wm.com.wmweather.MainActivity;
import weather.wm.com.wmweather.R;
import weather.wm.com.wmweather.common.bean.HistoryData;
import weather.wm.com.wmweather.common.units.DateUtils;
import weather.wm.com.wmweather.common.units.RequestUtils;
import weather.wm.com.wmweather.common.units.SharedPreferenceUtils;
import weather.wm.com.wmweather.common.units.StringUtils;
import weather.wm.com.wmweather.common.units.UrlUtils;
import weather.wm.com.wmweather.common.view.FlowLinearLayout;
import weather.wm.com.wmweather.history.logic.HistoryLogic;

/**
 * Created by HelloKiki on 2017/3/9.
 */

public class HistoryFragment extends Fragment implements View.OnClickListener {
    private TextView mTextViewScreening;
    private TextView mTextViewDateStart;
    private TextView mTextViewDateEnd;
    private Button mButtonFenbutu;
    private Button mButtonChazhitu;
    private PopupWindow mPopupWindow;
    private Button mButtonWeixingtu;
    private RadioGroup mRadioGroupPollutant;
    private MapView mMapView;
    private TextView mTextViewCurrentDate;
    private TextView mTextViewNumDate;
    private ProgressBar mProgressBar;
    private ProgressBar mProgressBarLoding;
    private ImageView mImageViewPro;
    private ImageView mImageViewNext;
    private ImageView mImageViewPause;

    private HistoryLogic mLogic;
    private List<HistoryData> mHistoryDatas;
    private List<Marker> mMarkers;

    private List<Integer> mPollutantValue;
    private BaiduMap mBaiduMap;
    private HeatMap heatmap;
    private DatePickerDialog datePickerDialog;
    private int position = 0;
    private int listSize = 0;
    private String timeType = "hour";
    private String startDate;
    private String endDate;

    private RadioGroup mRadioGroupDate;
    private ArrayList<LatLng> randomList;
    private Gradient gradient;
    private boolean isRunning = false;
    private boolean isHeatMap = true;
    private int searchYear;
    private int searchMonth;
    private int searchDay;

    private List<String> mTypeParams;
    private List<String> mAreaParams;
    boolean mIsType = false;
    boolean mIsArea = false;

    public HistoryFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history, null);
        view.findViewById(R.id.text_view_screening).setOnClickListener(this);
        mTextViewScreening = (TextView) view.findViewById(R.id.text_view_screening);
        mButtonFenbutu = (Button) view.findViewById(R.id.button_fenbutu);
        mButtonChazhitu = (Button) view.findViewById(R.id.button_chazhitu);
        mButtonWeixingtu = (Button) view.findViewById(R.id.button_weixingtu);
        mMapView = (MapView) view.findViewById(R.id.map_view);
        mTextViewCurrentDate = (TextView) view.findViewById(R.id.text_view_current_date);
        mTextViewNumDate = (TextView) view.findViewById(R.id.text_view_num);
        mProgressBarLoding = (ProgressBar) view.findViewById(R.id.progress_bar_loding);
        mProgressBar = (ProgressBar) view.findViewById(R.id.progress_bar);
        mImageViewNext = (ImageView) view.findViewById(R.id.image_view_next);
        mImageViewPause = (ImageView) view.findViewById(R.id.image_view_pause);
        mImageViewPro = (ImageView) view.findViewById(R.id.image_view_pro);
        mImageViewNext.setOnClickListener(this);
        mImageViewPro.setOnClickListener(this);
        mImageViewPause.setOnClickListener(this);
        mTextViewScreening.setOnClickListener(this);
        mButtonFenbutu.setOnClickListener(this);
        mButtonChazhitu.setOnClickListener(this);
        mButtonWeixingtu.setOnClickListener(this);

        mLogic = new HistoryLogic(getActivity());
        mMarkers = new ArrayList<>();
        mPollutantValue = new ArrayList<>();
        randomList = new ArrayList<>();
        mTypeParams = new ArrayList<>();
        mAreaParams = new ArrayList<>();

        startDate = DateUtils.formatYMD(new Date().getTime()-24*60*60*1000);
        endDate = DateUtils.formatYMD(new Date().getTime());
        searchYear = Calendar.getInstance().get(Calendar.YEAR)-1;
        searchMonth = Calendar.getInstance().get(Calendar.MONTH)-1;
        searchDay = Calendar.getInstance().get(Calendar.DAY_OF_MONTH)-1;

        initMap();
        initData();
        initPopupWindow();
        initDatePicker();
        initUI();
        return view;
    }

    private void initUI() {
        mTextViewDateStart.setText(startDate);
        mTextViewDateEnd.setText(endDate);
        mImageViewPause.setImageResource(R.drawable.jdt_pl);
        mImageViewNext.setImageResource(R.drawable.jdt_right);
        mImageViewPro.setImageResource(R.drawable.jdt_left2);
    }

    private void initMap() {
        mBaiduMap = mMapView.getMap();
        //普通地图
        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
        mBaiduMap.setMapStatus(MapStatusUpdateFactory.newMapStatus(new MapStatus.Builder().zoom(13).build()));
        mBaiduMap.setMaxAndMinZoomLevel(15,11);
        mMapView.showZoomControls(false);
        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);


        //设置渐变颜色值
        int[] DEFAULT_GRADIENT_COLORS = {Color.rgb(255, 0,  0), Color.rgb(255, 0, 0) };
        //设置渐变颜色起始值
        float[] DEFAULT_GRADIENT_START_POINTS = { 0.2f, 1f };
        //构造颜色渐变对象,
        gradient = new Gradient(DEFAULT_GRADIENT_COLORS, DEFAULT_GRADIENT_START_POINTS);
    }

    private void initData() {
        mProgressBarLoding.setVisibility(View.VISIBLE);
        pause();
        Map<String,String> header = new HashMap<>();
        header.put("token",UrlUtils.TOKEN);
        Map<String,String> body = new HashMap<>();
//        body.put("city", SharedPreferenceUtils.getCurrentCityName(getActivity()));
        body.put("city", "北京市");
        body.put("start",startDate);
        body.put("end",endDate);
        body.put("time",timeType);
        body.put("area", StringUtils.listToString(mAreaParams));
        body.put("type", StringUtils.listToString(mTypeParams));
        Log.i("TAG","start:"+startDate+"end:"+endDate);
        Log.i("TAG", StringUtils.listToString(mTypeParams)+SharedPreferenceUtils.getCurrentCityName(getActivity())+ StringUtils.listToString(mAreaParams));
        RequestUtils.post(UrlUtils.HISTORY_DATA,header,body, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    mProgressBarLoding.setVisibility(View.GONE);
                    if(!TextUtils.isEmpty(response)) {
                        JSONObject jsonObject = new JSONObject(response);
                        if(2000000 == jsonObject.getInt("status")){
                            mHistoryDatas = new ArrayList<HistoryData>();
                            JSONArray jsonArray = jsonObject.getJSONArray("data");
                            for (int i=0;i<jsonArray.length();i++) {
                                HistoryData historyData = new HistoryData();
                                historyData.setPm25(jsonArray.getJSONObject(i).getInt("pm25"));
                                historyData.setPm10(jsonArray.getJSONObject(i).getInt("pm10"));
                                historyData.setSo2(jsonArray.getJSONObject(i).getInt("so2"));
                                historyData.setO3(jsonArray.getJSONObject(i).getInt("o3"));
                                historyData.setCo(jsonArray.getJSONObject(i).getInt("co"));
                                historyData.setNo2(jsonArray.getJSONObject(i).getInt("no2"));
                                historyData.setAqi(jsonArray.getJSONObject(i).getInt("aqi"));
                                historyData.setAqiFirst(jsonArray.getJSONObject(i).getString("aqiFirst"));
                                historyData.setTime(jsonArray.getJSONObject(i).getString("time"));
                                historyData.setLat(jsonArray.getJSONObject(i).getDouble("lat"));
                                historyData.setLng(jsonArray.getJSONObject(i).getDouble("lng"));
                                mHistoryDatas.add(historyData);
                            }
                            mLogic.saveData(mHistoryDatas);
                            listSize = mHistoryDatas.size();
                            if(mHistoryDatas.size()>0)mTextViewCurrentDate.setText("当前:"+mHistoryDatas.get(0).getTime());
                            mTextViewNumDate.setText(startDate+" "+endDate);
                            if(isHeatMap) {
                                radioGroupSelect();
                                drawMarker(-1,true);
                            }else{
                                drawDataMarker();
                            }
                        }else{
                            Toast.makeText(getActivity(), jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(getActivity(), "查询不到数据", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "获取数据失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initPopupWindow() {
        final LayoutInflater inflater = LayoutInflater.from(getActivity());
        View view = inflater.inflate(R.layout.layout_history_popupwindow, null);
        mPopupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        final FlowLinearLayout areaLayout = (FlowLinearLayout) view.findViewById(R.id.linear_layout_area);
        final FlowLinearLayout typeLayout = (FlowLinearLayout) view.findViewById(R.id.linear_layout_type);
        mTextViewDateStart = (TextView) view.findViewById(R.id.text_view_history_date_start);
        mTextViewDateEnd = (TextView) view.findViewById(R.id.text_view_history_date_end);
        mRadioGroupPollutant = (RadioGroup) view.findViewById(R.id.radio_group_history_pollutants);
        mRadioGroupDate = (RadioGroup) view.findViewById(R.id.radio_group_history_date);
        mTextViewDateStart.setOnClickListener(this);
        mTextViewDateEnd.setOnClickListener(this);
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
        mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                initData();
            }
        });

        mProgressBarLoding.setVisibility(View.VISIBLE);
        mRadioGroupDate.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case 0:
                        timeType = "hour";
                        break;
                    case 1:
                        timeType = "day";
                        break;
                    case 2:
                        timeType = "momth";
                        break;
                }
            }
        });
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
                            checkBox.setOnCheckedChangeListener(new HistoryFragment.OnAreaCheckBoxListener());
                            areaLayout.addView(checkBox);
                        }
                        mIsArea = true;
                        if (mIsType) {
                            mProgressBarLoding.setVisibility(View.GONE);
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
                            checkBox.setOnCheckedChangeListener(new HistoryFragment.OnTypeCheckBoxListener());
                            typeLayout.addView(checkBox);
                        }
                        mIsType = true;
                        if (mIsArea) {
                            mProgressBarLoding.setVisibility(View.GONE);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, null);
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


    private void initDatePicker() {
        searchYear = Calendar.getInstance().get(Calendar.YEAR);
        searchMonth = Calendar.getInstance().get(Calendar.MONTH)+1;
        searchDay = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day){

                if(position == 1){
                    searchYear = year;
                    searchMonth = month;
                    searchDay = day;
                    startDate = searchYear+"-"+searchMonth+"-"+searchDay;
                    mTextViewDateStart.setText(startDate);
                }else if(position == 2){
                    if(DateUtils.getLongByString(startDate) < DateUtils.getLongByString(endDate)){
                        endDate = searchYear+"-"+searchMonth+"-"+searchDay;
                        mTextViewDateEnd.setText(endDate);
                    }
                }
            }
        }, searchYear, searchMonth, searchDay);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.text_view_pollutants:
                break;
            case R.id.text_view_screening:
                mPopupWindow.showAsDropDown(mTextViewScreening);
                break;
            case R.id.text_view_history_date_start:
                datePickerDialog.show();
                position = 1;
                break;
            case R.id.text_view_history_date_end:
                datePickerDialog.show();
                position = 2;
                break;
            case R.id.button_fenbutu:
                mButtonFenbutu.setBackgroundResource(R.drawable.bg_button_history_left_focus);
                mButtonChazhitu.setBackgroundColor(Color.parseColor("#FFFFFF"));
                mButtonWeixingtu.setBackgroundResource(R.drawable.bg_button_history_right);
                mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
                if(!isHeatMap) {
                    if (heatmap != null) heatmap.removeHeatMap();
                    drawMarker(-1,true);
                }
                isHeatMap = true;
                break;
            case R.id.button_chazhitu:
                mButtonFenbutu.setBackgroundResource(R.drawable.bg_button_history_left);
                mButtonChazhitu.setBackgroundColor(Color.parseColor("#0066FF"));
                mButtonWeixingtu.setBackgroundResource(R.drawable.bg_button_history_right);
                mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
                if(isHeatMap) {
                    drawDataMarker();
                }
                isHeatMap = false;
                break;
            case R.id.button_weixingtu:
                mButtonFenbutu.setBackgroundResource(R.drawable.bg_button_history_left);
                mButtonChazhitu.setBackgroundColor(Color.parseColor("#FFFFFF"));
                mButtonWeixingtu.setBackgroundResource(R.drawable.bg_button_history_right_focus);
                mBaiduMap.setMapType(BaiduMap.MAP_TYPE_SATELLITE);
                if(!isHeatMap) {
                    if (heatmap != null) heatmap.removeHeatMap();
                    drawMarker(-1,true);
                }
                isHeatMap = true;
                break;
            case R.id.image_view_pro:
                playPro();
                break;
            case R.id.image_view_pause:
                playPause();
                break;
            case R.id.image_view_next:
                playNext();
                break;
        }

    }


    public void drawDataMarker(){
        for (Marker marker : mMarkers) {
            marker.remove();
        }
        mBaiduMap.clear();
        mMarkers.clear();

        if (randomList.size() == 0) return;
        for (int i=0;i<randomList.size();i++) {
            heatmap = new HeatMap.Builder()
                    .data(randomList)
                    .gradient(gradient)
                    .radius(20)
                    .build();
            mBaiduMap.addHeatMap(heatmap);
        }
    }


    public void drawMarker(int current, boolean isRefresh) {
        mProgressBarLoding.setVisibility(View.VISIBLE);
        int pos = 0;
        if(isRefresh) {
            for (Marker marker : mMarkers) {
                marker.remove();
            }
            mBaiduMap.clear();
        }
        mMarkers.clear();
        randomList.clear();
        pos = current==-1?0:current;
        listSize = current==-1?listSize:current+1;
        for (int i = pos; pos < listSize; pos++) {
            if(pos+1<=listSize && mHistoryDatas.get(pos).getTime().equals(mHistoryDatas.get(pos+1).getTime())) {
                mHistoryDatas.get(pos).setPollutant(mPollutantValue.get(pos));
                LatLng point = new LatLng(mHistoryDatas.get(pos).getLat(), mHistoryDatas.get(pos).getLng());//构建Marker图标
                randomList.add(point);
                Bitmap bitmap = mLogic.createBitmap(mPollutantValue.get(pos));
                BitmapDescriptor bmp = BitmapDescriptorFactory.fromBitmap(bitmap);//构建MarkerOption，用于在地图上添加Marker
                OverlayOptions option = new MarkerOptions()
                        .position(point)
                        .icon(bmp);//在地图上添加Marker，并显示
                mMarkers.add((Marker) mBaiduMap.addOverlay(option));
            }else{
                break;
            }
        }
        mProgressBarLoding.setVisibility(View.GONE);
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
        listSize = mPollutantValue.size();
    }


    private void playPro() {
        for (Marker marker : mMarkers) {
            marker.remove();
        }
        mBaiduMap.clear();
        mMarkers.clear();
        mImageViewNext.setImageResource(R.drawable.jdt_right2);
        mImageViewPro.setImageResource(R.drawable.jdt_left2);
        mImageViewNext.setEnabled(true);
        pause();
        position = 0;
        currentDate();
        initData();
    }


    private void playNext() {
        if(currentDateName()){
            mImageViewNext.setImageResource(R.drawable.jdt_right);
            mImageViewNext.setEnabled(false);
        }else{
            mImageViewNext.setEnabled(true);
            mImageViewNext.setImageResource(R.drawable.jdt_right2);
        }
        for (Marker marker : mMarkers) {
            marker.remove();
        }
        mBaiduMap.clear();
        mMarkers.clear();
        pause();
        position = 0;
        currentDate();
        initData();
    }


    private void playPause() {
        if(isRunning){
            pause();
        }else{
            player();
        }
    }


    private boolean currentDateName(){
        if(timeType.equals("momth")){
            return searchYear == Calendar.getInstance().get(Calendar.YEAR);
        }else if(timeType.equals("day")){
            return searchYear == Calendar.getInstance().get(Calendar.YEAR) && searchMonth == Calendar.getInstance().get(Calendar.MONTH);
        }else if(timeType.equals("hour")){
            return searchYear == Calendar.getInstance().get(Calendar.YEAR) && searchDay == Calendar.getInstance().get(Calendar.DAY_OF_MONTH) && searchMonth == Calendar.getInstance().get(Calendar.MONTH);
        }
        return false;
    }


    private void currentDate(){
        endDate = searchYear+"-"+searchMonth+"-"+searchDay;
        if(timeType.equals("momth")){
            searchYear -= 1;
        }else if(timeType.equals("day")){
            searchMonth -= 1;
        }else if(timeType.equals("hour")){
            searchDay -= 1;
        }
        startDate = searchYear+"-"+searchMonth+"-"+searchDay;
    }


    private void pause() {
        isRunning=false;
        mImageViewPause.setImageResource(R.drawable.jdt_pl);
    }


    public void player(){
        isRunning = true;
        mImageViewPause.setImageResource(R.drawable.jdt_zanting2);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (isRunning) {
                        position++;
                        if(position+1<mHistoryDatas.size()-1) {
                            //同一时间
                            if (mHistoryDatas.get(position).getTime().equals(mHistoryDatas.get(position+1).getTime())){
                                mHandler.obtainMessage(position,false).sendToTarget();
                            }else{
                                //不同时间
                                Thread.sleep(1000);
                                mHandler.obtainMessage(position,true).sendToTarget();
                            }
                        }else {
                            //结束
                            mHandler.obtainMessage(-1,true).sendToTarget();
                        }
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.what == -1){
                pause();
                position = 0;
                mProgressBar.setProgress(0);
                return;
            }
            if((boolean)msg.obj){
                long start = DateUtils.getLongByString(mHistoryDatas.get(0).getTime());
                long pro = DateUtils.getLongByString(mHistoryDatas.get(msg.what).getTime());
                long end = DateUtils.getLongByString(mHistoryDatas.get(mHistoryDatas.size()-1).getTime());
                pro = pro-start;
                end = end-start;
                mProgressBar.setProgress((int) (pro*100/end));
                mTextViewCurrentDate.setText("当前:"+ mHistoryDatas.get(msg.what).getTime());
            }
            if (isHeatMap) {
                drawMarker(msg.what, (Boolean) msg.obj);
            } else {
                drawDataMarker();
                mTextViewCurrentDate.setText("当前:"+ mHistoryDatas.get(msg.what).getTime());
            }
        }
    };


    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }
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

}
