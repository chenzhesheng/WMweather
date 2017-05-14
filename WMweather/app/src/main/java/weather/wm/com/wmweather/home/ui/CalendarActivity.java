package weather.wm.com.wmweather.home.ui;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.andexert.calendarlistview.library.DatePickerController;
import com.andexert.calendarlistview.library.DayPickerView;
import com.andexert.calendarlistview.library.SimpleMonthAdapter;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import weather.wm.com.wmweather.R;
import weather.wm.com.wmweather.common.bean.Rank;
import weather.wm.com.wmweather.common.units.DateUtils;
import weather.wm.com.wmweather.common.units.RequestUtils;
import weather.wm.com.wmweather.common.units.SharedPreferenceUtils;
import weather.wm.com.wmweather.common.units.UrlUtils;
import weather.wm.com.wmweather.home.logic.CalendarLogic;

public class CalendarActivity extends AppCompatActivity implements View.OnClickListener, DatePickerController {
    private Spinner mSpinner;
    private DayPickerView mDayPickerView;
    private ProgressBar mProgressBar;
    private List<String> mTypes;

    private CalendarLogic mLogic;

    private List<List<Rank>> mDataList = new ArrayList<>();
    private List<List<Integer>> mDataAQI = new ArrayList<>();
    private List<List<Integer>> mDataPM25 = new ArrayList<>();
    private List<List<Integer>> mDataPM10 = new ArrayList<>();
    private List<List<Integer>> mDataCO = new ArrayList<>();
    private List<List<Integer>> mDataSO2 = new ArrayList<>();
    private List<List<Integer>> mDataNO2 = new ArrayList<>();
    private List<List<Integer>> mDataO3 = new ArrayList<>();

    private int mYearBefore = 1;
    private String mTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        findViewById(R.id.text_view_back).setOnClickListener(this);
        mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);
        mSpinner = (Spinner) findViewById(R.id.spinner);
        mLogic = new CalendarLogic();
        initSpinner();
        mDayPickerView = (DayPickerView) findViewById(R.id.day_picker_view);
        mDayPickerView.setController(this);
        mDayPickerView.setOnLoadMoreListener(new DayPickerView.LoadingData() {
            @Override
            public void onLoadMore() {
                if (mProgressBar.getVisibility() != View.VISIBLE) {
                    loadData(SharedPreferenceUtils.getCurrentCityName(CalendarActivity.this), DateUtils.getYearBefore(mYearBefore), mTime, "day");
                }

            }
        });
        loadData(SharedPreferenceUtils.getCurrentCityName(CalendarActivity.this), DateUtils.getYearBefore(1), DateUtils.formatYMD(System.currentTimeMillis()), "day");
    }

    private void loadData(String city, String start, String end, String day) {
        mProgressBar.setVisibility(View.VISIBLE);
        Map<String, String> header = new HashMap<>();
        header.put("token", UrlUtils.TOKEN);
        Map<String, String> body = new HashMap<>();
        body.put("city", city);
        body.put("start", start);
        body.put("end", end);
        body.put("time", day);

        RequestUtils.post(UrlUtils.CALENDER, header, body, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object = new JSONObject(response);

                    if (object.getLong("status") == 2000000) {
                        mTime = DateUtils.getYearBefore(mYearBefore);
                        mYearBefore++;
                        mLogic.clearData();
                        JSONArray array = object.getJSONArray("data");
                        List<Rank> rankList = new ArrayList<>();
                        for (int i = 0; i < array.length(); i++) {
                            Rank rank = new Rank();
                            JSONObject json = array.getJSONObject(i);
                            if (!TextUtils.isEmpty(json.getString("commonOrderAqi")))
                                rank.setRank(Integer.parseInt(json.getString("commonOrderAqi")));
                            rank.setProvince(json.getString("province"));
                            rank.setCity(json.getString("city"));
                            rank.setFirstAqi(json.getString("firstAqi"));
                            rank.setAqi(json.getInt("aqi"));
                            rank.setPm25(json.getInt("pm25"));
                            rank.setPm10(json.getInt("pm10"));
                            rank.setCo(json.getInt("co"));
                            rank.setSo2(json.getInt("so2"));
                            rank.setNo2(json.getInt("no2"));
                            rank.setO3(json.getInt("o3"));
                            rank.setTime(json.getString("time"));
                            rankList.add(rank);
                        }
                        mLogic.addRankList(rankList);

                        int start = 0;
                        int i = 0;
                        while (true) {
                            List<Rank> list = new ArrayList<Rank>();
                            List<Integer> aqis = new ArrayList<Integer>();
                            List<Integer> pm25s = new ArrayList<Integer>();
                            List<Integer> pm10s = new ArrayList<Integer>();
                            List<Integer> cos = new ArrayList<Integer>();
                            List<Integer> so2s = new ArrayList<Integer>();
                            List<Integer> no2s = new ArrayList<Integer>();
                            List<Integer> o3s = new ArrayList<Integer>();
                            int end = mLogic.getIndex(DateUtils.getMonthOfOneBefore(i));
                            if (start >= end) {
                                break;
                            }
                            list.addAll(mLogic.selectDataForDate(start, end));
                            aqis.addAll(mLogic.selectAQIForDate(start, end));
                            pm25s.addAll(mLogic.selectPM25ForDate(start, end));
                            pm10s.addAll(mLogic.selectPM10ForDate(start, end));
                            cos.addAll(mLogic.selectCOForDate(start, end));
                            so2s.addAll(mLogic.selectSO2ForDate(start, end));
                            no2s.addAll(mLogic.selectNO2ForDate(start, end));
                            o3s.addAll(mLogic.selectO3ForDate(start, end));

                            Collections.reverse(list);
                            Collections.reverse(aqis);
                            Collections.reverse(pm25s);
                            Collections.reverse(pm10s);
                            Collections.reverse(cos);
                            Collections.reverse(so2s);
                            Collections.reverse(no2s);
                            Collections.reverse(o3s);

                            mDataList.add(list);
                            mDataAQI.add(aqis);
                            mDataPM25.add(pm25s);
                            mDataPM10.add(pm10s);
                            mDataCO.add(cos);
                            mDataSO2.add(so2s);
                            mDataNO2.add(no2s);
                            mDataO3.add(o3s);
                            start = end;
                            i++;
                        }

                        mDayPickerView.setData(mDataAQI);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                mProgressBar.setVisibility(View.GONE);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mProgressBar.setVisibility(View.GONE);
            }
        });

    }

    private void initSpinner() {
        mTypes = new ArrayList<>();
        mTypes.add("AQI");
        mTypes.add("PM2.5");
        mTypes.add("PM10");
        mTypes.add("CO");
        mTypes.add("SO2");
        mTypes.add("NO2");
        mTypes.add("O3");
        ArrayAdapter adapter = new ArrayAdapter(this, R.layout.layout_spinner_item, mTypes);
        mSpinner.setAdapter(adapter);
        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                TextView tv = (TextView) view;
                if (tv != null)
                    tv.setTextColor(Color.WHITE);
                switch (i) {
                    case 0:
                        mDayPickerView.setData(mDataAQI);
                        break;
                    case 1:
                        mDayPickerView.setData(mDataPM25);
                        break;
                    case 2:
                        mDayPickerView.setData(mDataPM10);
                        break;
                    case 3:
                        mDayPickerView.setData(mDataCO);
                        break;
                    case 4:
                        mDayPickerView.setData(mDataSO2);
                        break;
                    case 5:
                        mDayPickerView.setData(mDataNO2);
                        break;
                    case 6:
                        mDayPickerView.setData(mDataO3);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @Override
    public void onClick(View view) {
        finish();
    }

    @Override
    public int getMaxYear() {
        return Calendar.getInstance().get(Calendar.YEAR) - (mYearBefore - 1);
    }

    @Override
    public void onDayOfMonthSelected(int year, int month, int day) {
        Log.e("2017", "onDayOfMonthSelected: year->" + year + " month->" + (month + 1) + " day->" + day);
        Intent intent=new Intent(this,HourCalendarActivity.class);
        intent.putExtra("year",year);
        intent.putExtra("month",month);
        intent.putExtra("day",day);
        startActivity(intent);
    }

    @Override
    public void onDateRangeSelected(SimpleMonthAdapter.SelectedDays<SimpleMonthAdapter.CalendarDay> selectedDays) {

    }
}
