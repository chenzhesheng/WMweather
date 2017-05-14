package weather.wm.com.wmweather.realtime.ui;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import weather.wm.com.wmweather.R;
import weather.wm.com.wmweather.common.bean.StationData;
import weather.wm.com.wmweather.common.units.RequestUtils;
import weather.wm.com.wmweather.common.units.UrlUtils;

/**
 * Created by HelloKiki on 2017/3/16.
 */

public class RealTimeDataAddressDetailsView extends LinearLayout {

    private TextView mTextViewStationName;
    private TextView mTextViewValue;
    private TextView mTextViewResult;
    private TextView mTextViewTime;
    private TextView mTextViewPM25;
    private TextView mTextViewPM10;
    private TextView mTextViewCO;
    private TextView mTextViewSO2;
    private TextView mTextViewNO2;
    private TextView mTextViewO3;
    private LineChart mLineChart;
    private ProgressBar mProgressBar;

    private List<StationData> dataList = new ArrayList<>();


    public RealTimeDataAddressDetailsView(Context context) {
        this(context, null);
    }

    public RealTimeDataAddressDetailsView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RealTimeDataAddressDetailsView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflate(context, R.layout.layout_real_time_data_address_details, this);
        mLineChart = (LineChart) findViewById(R.id.line_chart);
        mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);
        mTextViewValue = (TextView) findViewById(R.id.text_view_value);
        mTextViewStationName = (TextView) findViewById(R.id.text_view_address);
        mTextViewResult = (TextView) findViewById(R.id.text_view_result);
        mTextViewTime = (TextView) findViewById(R.id.text_view_time);
        mTextViewPM25 = (TextView) findViewById(R.id.text_view_PM2_5);
        mTextViewPM10 = (TextView) findViewById(R.id.text_view_PM10);
        mTextViewCO = (TextView) findViewById(R.id.text_view_CO);
        mTextViewSO2 = (TextView) findViewById(R.id.text_view_SO2);
        mTextViewNO2 = (TextView) findViewById(R.id.text_view_NO2);
        mTextViewO3 = (TextView) findViewById(R.id.text_view_O3);

        initLineChart();
    }

    public RealTimeDataAddressDetailsView setStationName(String name) {
        mTextViewStationName.setText(name);
        return this;
    }

    public RealTimeDataAddressDetailsView setTime(String time) {
        mTextViewTime.setText("时间：" + time + " 实时");
        return this;
    }

    public RealTimeDataAddressDetailsView setAqi(int aqi) {
        mTextViewValue.setText(aqi + "");
        mTextViewResult.setText(getResultForValue(aqi));
        mTextViewResult.setBackgroundColor(getColorForValue(aqi));
        return this;
    }

    public RealTimeDataAddressDetailsView setPM25(int pm25) {
        mTextViewPM25.setText(pm25 + "");
        return this;
    }

    public RealTimeDataAddressDetailsView setPM10(int pm10) {
        mTextViewPM10.setText(pm10 + "");
        return this;
    }

    public RealTimeDataAddressDetailsView setCO(int co) {
        mTextViewCO.setText(co + "");
        return this;
    }

    public RealTimeDataAddressDetailsView setSO2(int SO2) {
        mTextViewSO2.setText(SO2 + "");
        return this;
    }

    public RealTimeDataAddressDetailsView setNO2(int NO2) {
        mTextViewNO2.setText(NO2 + "");
        return this;
    }

    public RealTimeDataAddressDetailsView setO3(int O3) {
        mTextViewO3.setText(O3 + "");
        return this;
    }

    public void loadData(String stationId) {
        dataList.clear();
        mProgressBar.setVisibility(VISIBLE);
        Map<String, String> header = new HashMap<>();
        header.put("token", UrlUtils.TOKEN);
        Map<String, String> body = new HashMap<>();
        body.put("stationId", "80121");

        RequestUtils.post(UrlUtils.REAL_TIME_STATION_DESTAIL, header, body, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object = new JSONObject(response);
                    if (object.getLong("status") == 2000000) {
                        Log.e("2017", "24小时接口->" + response);
                        JSONArray array = object.getJSONArray("data");
                        mProgressBar.setVisibility(GONE);
                        for (int i = 0; i < array.length(); i++) {
                            StationData data = new StationData();
                            JSONObject json = array.getJSONObject(i);
                            data.setProvince(json.getString("province"));
                            data.setCity(json.getString("city"));
                            data.setAqi(json.getInt("aqi"));
                            data.setFirstAqi(json.getInt("firstAqi"));
                            data.setTime(json.getString("time"));
                            dataList.add(data);
                        }
                        initChartData();
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


    public String getResultForValue(int value) {
        if (value > 0 && value <= 50) {
            return "优";
        } else if (value > 50 && value <= 100) {
            return "良";
        } else if (value > 100 && value <= 150) {
            return "轻度污染";
        } else if (value > 150 && value <= 200) {
            return "中度污染";
        } else if (value > 200 && value <= 300) {
            return "重度污染";
        } else if (value > 300) {
            return "严重污染";
        }
        return "优";
    }

    public int getColorForValue(int value) {
        if (value > 0 && value <= 50) {
            return Color.parseColor("#49db00");
        } else if (value > 50 && value <= 100) {
            return Color.parseColor("#ffff00");
        } else if (value > 100 && value <= 150) {
            return Color.parseColor("#ff6d00");
        } else if (value > 150 && value <= 200) {
            return Color.parseColor("#ff0000");
        } else if (value > 200 && value <= 300) {
            return Color.parseColor("#9b004c");
        } else if (value > 300) {
            return Color.parseColor("#920024");
        } else {
            return Color.parseColor("#49db00");
        }
    }


    private void initLineChart() {
        mLineChart.setNoDataText("没有数据");
        mLineChart.setNoDataTextColor(Color.parseColor("#FFFFFF"));
        mLineChart.setDrawGridBackground(false);
        mLineChart.setDrawBorders(false);
//        mLineChart.setScaleEnabled(false);

        mLineChart.getAxisRight().setEnabled(false);
        XAxis xAxis = mLineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTextColor(Color.WHITE);
        xAxis.setAvoidFirstLastClipping(true);
//        xAxis.setValueFormatter(new IAxisValueFormatter() {
//            @Override
//            public String getFormattedValue(float value, AxisBase axis) {
//                return "";
//            }
//        });
        YAxis yAxis = mLineChart.getAxisLeft();
        yAxis.setStartAtZero(true);
        yAxis.setTextColor(Color.WHITE);

        initChartData();
    }

    private void initChartData() {
        ArrayList<String> x = new ArrayList<>();

        ArrayList<Entry> value = new ArrayList<>();
        for (int i = 0; i < dataList.size(); i++) {
            value.add(new Entry(i + 1, dataList.get(i).getAqi()));
        }
        if (value.size() <= 0) {
            mLineChart.clear();
            return;
        }

        LineDataSet set;

        if (mLineChart.getData() != null && mLineChart.getData().getDataSetCount() > 0) {
            set = (LineDataSet) mLineChart.getLineData().getDataSetByIndex(0);
            set.setValues(value);
            mLineChart.getData().notifyDataChanged();
            mLineChart.notifyDataSetChanged();
        } else {
            set = new LineDataSet(value, "");
            set.setCircleColor(Color.WHITE);
            set.setLineWidth(2f);
            set.setHighlightEnabled(false);
        }

        ArrayList<ILineDataSet> sets = new ArrayList<>();
        sets.add(set);
        LineData data = new LineData(sets);
        mLineChart.setData(data);
        mLineChart.animateXY(1000, 1000);
        mLineChart.invalidate();
    }
}
