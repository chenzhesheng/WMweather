package weather.wm.com.wmweather.home.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import weather.wm.com.wmweather.R;
import weather.wm.com.wmweather.common.SpaceItemDecoration;
import weather.wm.com.wmweather.common.bean.Rank;
import weather.wm.com.wmweather.common.units.DateUtils;
import weather.wm.com.wmweather.common.units.RequestUtils;
import weather.wm.com.wmweather.common.units.UrlUtils;
import weather.wm.com.wmweather.home.logic.HistoryRankAdapter;

public class HistoryRankActivity extends AppCompatActivity implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {

    private TextView mTextViewTitle;
    private ProgressBar mProgressBar;
    private RecyclerView mRecyclerViewRank;
    private RadioGroup mRadioGroupDate;
    private LineChart mLineChart;
    private List<Rank> mRankList = new ArrayList<>();
    private HistoryRankAdapter mAdapter;

    private List<Rank> mRankDayList = new ArrayList<>();
    private List<Rank> mRankMonthList = new ArrayList<>();
    private List<String> mXAxisStr = new ArrayList<>();
    private XAxis mXAxis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_rank);
        findViewById(R.id.text_view_back).setOnClickListener(this);
        mTextViewTitle = (TextView) findViewById(R.id.text_view_chart_title);
        mRadioGroupDate = (RadioGroup) findViewById(R.id.radio_group_date_select);
        mRadioGroupDate.setOnCheckedChangeListener(this);
        mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);
        mLineChart = (LineChart) findViewById(R.id.line_chart);
        mRecyclerViewRank = (RecyclerView) findViewById(R.id.recycler_view_rank);
        mRecyclerViewRank.setLayoutManager(new LinearLayoutManager(this));

        initRecyclerView();
        initLineChart();
        initChartData();
        loadData("北京市", DateUtils.getDayBefore(30), DateUtils.formatYMD(System.currentTimeMillis()), "day");
        loadData("北京市", DateUtils.getMonthBefore(30), DateUtils.formatYMD(System.currentTimeMillis()), "month");

    }

    private void initRecyclerView() {
        mAdapter = new HistoryRankAdapter(this, mRankList);
        mRecyclerViewRank.addItemDecoration(new SpaceItemDecoration(10));
        mRecyclerViewRank.setAdapter(mAdapter);
    }

    private void loadData(String city, String start, String end, final String time) {
        mProgressBar.setVisibility(View.VISIBLE);
        Map<String, String> header = new HashMap<>();
        header.put("token", UrlUtils.TOKEN);
        Map<String, String> body = new HashMap<>();
        body.put("city", city);
        body.put("start", start);
        body.put("end", end);
        body.put("time", time);
        RequestUtils.post(UrlUtils.CITY_CALANDAR_RANK, header, body, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("2017", "json->" + response);
                try {
                    JSONObject object = new JSONObject(response);
                    if (object.getLong("status") == 2000000) {
                        List<Rank> rankList = new ArrayList<Rank>();
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
                            rankList.add(rank);
                        }
                        if ("day".equals(time)) {
                            mRankDayList.addAll(rankList);
                            mRankList.addAll(mRankDayList);
                            mAdapter.notifyDataSetChanged();
                            for (int i = 0; i < mRankList.size(); i++) {
                                mXAxisStr.add(mRankList.get(i).getTime().split(" ")[0]);
                            }
                            initChartData();
                        } else {
                            mRankMonthList.addAll(rankList);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (mRankDayList.size() > 0 && mRankMonthList.size() > 0) {
                    mProgressBar.setVisibility(View.GONE);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mProgressBar.setVisibility(View.GONE);
            }
        });

    }

    private void initLineChart() {
        mLineChart.setNoDataText("没有数据");
        mLineChart.setNoDataTextColor(Color.BLACK);
        mLineChart.setDrawGridBackground(false);
        mLineChart.setDrawBorders(false);
//        mLineChart.setScaleEnabled(false);
        mLineChart.getAxisRight().setEnabled(false);
        mXAxis = mLineChart.getXAxis();
        mXAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        mXAxis.setTextColor(Color.BLACK);
        mXAxis.setAvoidFirstLastClipping(true);

        YAxis yAxis = mLineChart.getAxisLeft();
        yAxis.setStartAtZero(true);
        yAxis.setTextColor(Color.BLACK);

    }

    private void initChartData() {
        ArrayList<Entry> value = new ArrayList<>();
        for (int i = 0; i < mRankList.size(); i++) {
            value.add(new Entry(i, mRankList.get(i).getRank()));
        }
        mXAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                if(value>mXAxisStr.size()){
                    return "";
                }
                if (value == Math.floor(value)) {
                    return mXAxisStr.get((int) value);
                } else {
                    return "";
                }

            }
        });
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
            set.setCircleColor(Color.BLUE);
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


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.text_view_back:
                finish();
                break;
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int j) {
        mRankList.clear();
        mXAxisStr.clear();
        switch (radioGroup.getCheckedRadioButtonId()) {
            case R.id.radio_button_rank_day:
                mRankList.addAll(mRankDayList);
                break;
            case R.id.radio_button_rank_month:
                mRankList.addAll(mRankMonthList);
                break;
        }
        mAdapter.notifyDataSetChanged();
        for (int i = 0; i < mRankList.size(); i++) {
            mXAxisStr.add(mRankList.get(i).getTime().split(" ")[0]);
        }
        initChartData();
    }
}
