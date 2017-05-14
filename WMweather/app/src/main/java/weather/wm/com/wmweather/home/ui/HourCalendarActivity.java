package weather.wm.com.wmweather.home.ui;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.andexert.calendarlistview.library.HourCalendarView;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import weather.wm.com.wmweather.R;
import weather.wm.com.wmweather.common.SpaceItemDecoration;
import weather.wm.com.wmweather.common.bean.Rank;
import weather.wm.com.wmweather.common.units.DateUtils;
import weather.wm.com.wmweather.common.units.RequestUtils;
import weather.wm.com.wmweather.common.units.SharedPreferenceUtils;
import weather.wm.com.wmweather.common.units.UrlUtils;
import weather.wm.com.wmweather.home.logic.HourCalenDarLogic;
import weather.wm.com.wmweather.home.logic.HourCalendarAdapter;

public class HourCalendarActivity extends AppCompatActivity implements View.OnClickListener {

    private ProgressBar mProgressBar;
    private Spinner mSpinner;
    private HourCalendarView mHourView;
    private List<String> mTypes;

    private List<Rank> mRankDatas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hour_calendar);
        findViewById(R.id.text_view_back).setOnClickListener(this);
        mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);
        mHourView = (HourCalendarView) findViewById(R.id.hour_calendar_view);
        mSpinner = (Spinner) findViewById(R.id.spinner);
        initSpinner();
        int year = getIntent().getIntExtra("year", 0);
        int month = getIntent().getIntExtra("month", 0);
        int day = getIntent().getIntExtra("day", 0);
        mHourView.setTitle(year + "-" + (month + 1) + "-" + day);
        String currentDay = year + "-" + (month + 1) + "-" + day + " 00:00:00";
        String nextDay = DateUtils.getCalenderAfter(year, month, day, 1) + " 00:00:00";
        Log.e("2017", "currentDay=" + currentDay + " nextDay=" + nextDay);
        loadData(SharedPreferenceUtils.getCurrentCityName(HourCalendarActivity.this), currentDay, nextDay, "hour");
    }

    private void loadData(String city, String start, String end, String day) {
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
                        JSONArray array = object.getJSONArray("data");
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
                            mRankDatas.add(rank);
                        }
                        for (int i = 0; i < mRankDatas.size(); i++) {
                            Rank rank = mRankDatas.get(i);
                            mHourView.setData(HourCalenDarLogic.getHourByDate(rank.getTime()), rank.getAqi());
                        }
                        mHourView.build();

                    } else {
                        Toast.makeText(HourCalendarActivity.this, "数据异常", Toast.LENGTH_SHORT).show();
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
                selectData(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public void selectData(int select){
        Log.e("2017","i="+select);
        mHourView.initData();
        for (int i = 0; i < mRankDatas.size(); i++) {
            Rank rank = mRankDatas.get(i);
            switch (select) {
                case 0:
                    mHourView.setData(HourCalenDarLogic.getHourByDate(rank.getTime()), rank.getAqi());
                    break;
                case 1:
                    mHourView.setData(HourCalenDarLogic.getHourByDate(rank.getTime()), rank.getPm25());
                    break;
                case 2:
                    mHourView.setData(HourCalenDarLogic.getHourByDate(rank.getTime()), rank.getPm10());
                    break;
                case 3:
                    mHourView.setData(HourCalenDarLogic.getHourByDate(rank.getTime()), rank.getCo());
                    break;
                case 4:
                    mHourView.setData(HourCalenDarLogic.getHourByDate(rank.getTime()), rank.getSo2());
                    break;
                case 5:
                    mHourView.setData(HourCalenDarLogic.getHourByDate(rank.getTime()), rank.getNo2());
                    break;
                case 6:
                    mHourView.setData(HourCalenDarLogic.getHourByDate(rank.getTime()), rank.getO3());
                    break;
            }
        }
        mHourView.build();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.text_view_back:
                finish();
                break;
        }
    }
}
