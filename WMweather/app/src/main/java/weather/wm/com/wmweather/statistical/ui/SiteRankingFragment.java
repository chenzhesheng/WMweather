package weather.wm.com.wmweather.statistical.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RadioGroup;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import weather.wm.com.wmweather.R;
import weather.wm.com.wmweather.common.SpaceItemDecoration;
import weather.wm.com.wmweather.common.bean.SiteRank;
import weather.wm.com.wmweather.common.view.FlowRadioGroup;
import weather.wm.com.wmweather.statistical.logic.StatRanKingAdapter;

/**
 * Created by ChenZheSheng on 2017/3/16.
 */

public class SiteRankingFragment extends Fragment implements RadioGroup.OnCheckedChangeListener{
    private FlowRadioGroup mRadioGroupAQI;
    private RecyclerView mRecyclerViewList;
    private Spinner mSpinner;
    private StatRanKingAdapter mAdapter;
    private List<SiteRank> mSiteRanks;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fragment_site_ranking,null);

        mRadioGroupAQI = (FlowRadioGroup) view.findViewById(R.id.radio_group_stat_area);
        mRecyclerViewList = (RecyclerView) view.findViewById(R.id.recycler_view_site_ranking);
        mSpinner = (Spinner) view.findViewById(R.id.spinner);

        mRadioGroupAQI.setOnCheckedChangeListener(this);

        initSpinner();
        initRecyclerView();
        return view;
    }

    private void initSpinner() {
        List sites = new ArrayList<>();
        sites.add("A区域");
        sites.add("B区域");
        ArrayAdapter arrayAdapter = new ArrayAdapter(getActivity(),android.R.layout.simple_list_item_1,sites);
        mSpinner.setAdapter(arrayAdapter);
    }

    private void initRecyclerView() {
        mSiteRanks = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            mSiteRanks.add(new SiteRank("A","东四环监控点","255"));
        }
        mAdapter = new StatRanKingAdapter(mSiteRanks,getActivity());
        mRecyclerViewList.addItemDecoration(new SpaceItemDecoration(10));
        mRecyclerViewList.setLayoutManager(new GridLayoutManager(getActivity(),1));
        mRecyclerViewList.setAdapter(mAdapter);

    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        switch (i){
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
