package weather.wm.com.wmweather.statistical.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RadioGroup;
import android.widget.Spinner;

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
import java.util.List;
import java.util.Random;

import weather.wm.com.wmweather.R;
import weather.wm.com.wmweather.common.view.FlowRadioGroup;

/**
 * Created by ChenZheSheng on 2017/3/16.
 */

public class StatContrastFragment extends Fragment implements RadioGroup.OnCheckedChangeListener{
    private BarChart mBarChart;
    private LineChart mLineChart;
    private RadioGroup mRadioGroupDate;
    private RadioGroup mRadioGroupChart;
    private FlowRadioGroup mRadioGroupAQI;
    private Spinner mSpinnerA;
    private Spinner mSpinnerB;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fragment_stat_contrast,null);

        mRadioGroupDate = (RadioGroup) view.findViewById(R.id.radio_group_stat_contrast_date);
        mRadioGroupChart = (RadioGroup) view.findViewById(R.id.radio_button_stat_chart);
        mRadioGroupAQI = (FlowRadioGroup) view.findViewById(R.id.radio_group_stat_area);
        mSpinnerA = (Spinner) view.findViewById(R.id.spinner_A);
        mSpinnerB = (Spinner) view.findViewById(R.id.spinner_B);

        mBarChart = (BarChart) view.findViewById(R.id.bar_chart);
        mLineChart = (LineChart) view.findViewById(R.id.line_chart);

        mRadioGroupDate.setOnCheckedChangeListener(this);
        mRadioGroupChart.setOnCheckedChangeListener(this);
        mRadioGroupAQI.setOnCheckedChangeListener(this);

        initSpinner();
        initBarChart();
        initLineChart();
        return view;
    }

    private void initSpinner() {
        List sites = new ArrayList<>();
        sites.add("A区域");
        sites.add("B区域");
        ArrayAdapter arrayAdapter = new ArrayAdapter(getActivity(),android.R.layout.simple_list_item_1,sites);
        mSpinnerA.setAdapter(arrayAdapter);
        mSpinnerB.setAdapter(arrayAdapter);
    }

    private void initBarChart() {
        ArrayList<BarEntry> entries1 = new ArrayList<>();
        ArrayList<BarEntry> entries2 = new ArrayList<>();
        ArrayList<String> xValue = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < 12; i++) {
            xValue.add((i + 1) + "日");
            entries1.add(new BarEntry(i,random.nextInt(1000)));
            entries2.add(new BarEntry(i,random.nextInt(1000)));
        }

        BarDataSet set1 = new BarDataSet(entries1, "one");
        BarDataSet set2 = new BarDataSet(entries2, "two");
        set1.setColor(Color.GREEN);
        set2.setColor(Color.BLUE);

        ArrayList<IBarDataSet> isets = new ArrayList<>();
        isets.add(set1);
        isets.add(set2);

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
        ArrayList<Entry> entries1 = new ArrayList<>();
        ArrayList<Entry> entries2 = new ArrayList<>();
        ArrayList<String> xValue = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < 12; i++) {
            xValue.add((i + 1) + "日");
            entries1.add(new BarEntry(i,random.nextInt(1000)));
            entries2.add(new BarEntry(i,random.nextInt(1000)));
        }

        LineDataSet set1 = new LineDataSet(entries1, "one");
        LineDataSet set2 = new LineDataSet(entries2, "two");
        set1.setColor(Color.GREEN);
        set2.setColor(Color.BLUE);

        ArrayList<ILineDataSet> isets = new ArrayList<>();
        isets.add(set1);
        isets.add(set2);

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
        }
    }
}
