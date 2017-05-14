package weather.wm.com.wmweather.statistical.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;
import java.util.Random;

import weather.wm.com.wmweather.R;
import weather.wm.com.wmweather.common.view.FlowRadioGroup;

/**
 * Created by ChenZheSheng on 2017/3/16.
 */

public class StatHistoryFragment extends Fragment implements RadioGroup.OnCheckedChangeListener{
    private BarChart mBarChart;
    private LineChart mLineChart;
    private RadioGroup mRadioGroupDate;
    private RadioGroup mRadioGroupChart;
    private FlowRadioGroup mRadioGroupAQI;
    private RadioGroup mRadioGroupPollutants;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fragment_stat_history,null);

        mRadioGroupDate = (RadioGroup) view.findViewById(R.id.radio_group_stat_history_date);
        mRadioGroupChart = (RadioGroup) view.findViewById(R.id.radio_button_stat_chart);
        mRadioGroupAQI = (FlowRadioGroup) view.findViewById(R.id.radio_group_stat_area);
        mRadioGroupPollutants = (RadioGroup) view.findViewById(R.id.radio_group_stat_pollutants);

        mBarChart = (BarChart) view.findViewById(R.id.bar_chart);
        mLineChart = (LineChart) view.findViewById(R.id.line_chart);

        mRadioGroupDate.setOnCheckedChangeListener(this);
        mRadioGroupChart.setOnCheckedChangeListener(this);
        mRadioGroupAQI.setOnCheckedChangeListener(this);
        mRadioGroupPollutants.setOnCheckedChangeListener(this);

        initBarChart();
        initLineChart();
        return view;
    }

    private void initBarChart() {
        ArrayList<BarEntry> entries = new ArrayList<>();
        ArrayList<String> xValue = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < 12; i++) {
            xValue.add((i + 1) + "日");
            entries.add(new BarEntry(i,random.nextInt(1000)));
        }

        BarDataSet set = new BarDataSet(entries, "");
        set.setColor(Color.GREEN);

        ArrayList<IBarDataSet> isets = new ArrayList<>();
        isets.add(set);

        BarData data = new BarData(isets);
        mBarChart.setData(data);

        mBarChart.getLegend().setPosition(Legend.LegendPosition.ABOVE_CHART_LEFT);
        mBarChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        mBarChart.getXAxis().setDrawGridLines(false);
        mBarChart.getAxisRight().setEnabled(false);
        mBarChart.getAxisLeft().setAxisMinimum(0f);

        mBarChart.animateXY(1000, 2000);

    }


    private void initLineChart() {
        ArrayList<Entry> entries = new ArrayList<>();
        ArrayList<String> xValue = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < 12; i++) {
            xValue.add((i + 1) + "日");
            entries.add(new BarEntry(i,random.nextInt(1000)));
        }

        LineDataSet set = new LineDataSet(entries, "");
        set.setColor(Color.GREEN);

        ArrayList<ILineDataSet> isets = new ArrayList<>();
        isets.add(set);

        LineData data = new LineData(isets);
        mLineChart.setData(data);

        mLineChart.getLegend().setPosition(Legend.LegendPosition.ABOVE_CHART_LEFT);
        mLineChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        mLineChart.getXAxis().setDrawGridLines(false);
        mLineChart.getAxisRight().setEnabled(false);
        mLineChart.getAxisLeft().setAxisMinimum(0f);

        mLineChart.animateXY(1000,2000);
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        switch (i){
            case R.id.radio_button_bar_chart:
                mBarChart.setVisibility(View.VISIBLE);
                mLineChart.setVisibility(View.GONE);
                break;
            case R.id.radio_button_Line_chart:
                mLineChart.setVisibility(View.VISIBLE);
                mBarChart.setVisibility(View.GONE);
                break;
            case R.id.radio_button_AQI:
                break;
            case R.id.radio_button_PM2:
                break;
            case R.id.radio_button_PM10:
                break;
            case R.id.radio_button_CO:
                break;
            case R.id.radio_button_NO2:
                break;
            case R.id.radio_button_SO2:
                break;
            case R.id.radio_button_O3:
                break;
            case R.id.radio_button_map_A:
                break;
            case R.id.radio_button_map_B:
                break;
        }
    }
}
