package weather.wm.com.wmweather.home.ui;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.greenrobot.event.EventBus;
import weather.wm.com.wmweather.R;
import weather.wm.com.wmweather.common.bean.Pollutants;
import weather.wm.com.wmweather.common.units.RequestUtils;
import weather.wm.com.wmweather.common.units.SharedPreferenceUtils;
import weather.wm.com.wmweather.common.units.UrlUtils;
import weather.wm.com.wmweather.home.logic.HomeLogic;
import weather.wm.com.wmweather.home.logic.HomePollutantsAdapter;

/**
 * Created by HelloKiki on 2017/3/9.
 */

public class HomeFragment extends Fragment implements View.OnClickListener {

    private TextView mTextViewCity;
    private ProgressBar mProgressBar;
    private TextView mTextViewRank;
    private TextView mTextViewCalendar;
    private TextView mTextViewValue;
    private TextView mTextViewResult;
    private TextView mTextViewWind;
    private RecyclerView mRecyclerView;
    private List<Pollutants> mPollutantsList=new ArrayList<>();

    private HomePollutantsAdapter mAdapter;
    private HomeLogic mLogic;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, null);
//        EventBus.getDefault().register(this);
        mProgressBar = (ProgressBar) view.findViewById(R.id.progress_bar);
        mTextViewRank = (TextView) view.findViewById(R.id.text_view_rank);
        view.findViewById(R.id.text_view_calendar).setOnClickListener(this);
        mTextViewResult = (TextView) view.findViewById(R.id.text_view_result);
        mTextViewWind = (TextView) view.findViewById(R.id.text_view_wind);
        mTextViewValue = (TextView) view.findViewById(R.id.text_view_value);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_pollutants);
        mTextViewCity= (TextView) view.findViewById(R.id.text_view_city);
        mTextViewRank.setOnClickListener(this);
        mTextViewCity.setOnClickListener(this);

        mTextViewCity.setText(SharedPreferenceUtils.getCurrentCityName(getActivity()));
        mLogic = new HomeLogic();

        mRecyclerView.addItemDecoration(new SpaceItemDecoration(30));
        mAdapter= new HomePollutantsAdapter(getActivity(), mPollutantsList);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        mRecyclerView.setAdapter(mAdapter);

        loadData();
        return view;
    }

    private void loadData() {
        mProgressBar.setVisibility(View.VISIBLE);
        Map<String, String> header = new HashMap<>();
        header.put("token", UrlUtils.TOKEN);
        Map<String, String> body = new HashMap<>();
        body.put("city", SharedPreferenceUtils.getCurrentCityName(getActivity())) ;

        RequestUtils.post(UrlUtils.MAIN, header, body, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object = new JSONObject(response);
                    if (object.getLong("status") == 2000000) {
                        int aqifirst = 1;
                        JSONObject data = object.getJSONObject("data");
                        if (!TextUtils.isEmpty(data.getString("aqiFirst")))
                            aqifirst = Integer.parseInt(data.getString("aqiFirst"));
                        initData(data.getInt("pm25"), data.getInt("pm10"), data.getInt("so2")
                                , data.getInt("o3"), data.getInt("co"), data.getInt("no2"), aqifirst);
                        mTextViewValue.setText(data.getInt("aqi") + "");
                        mTextViewResult.setText(mLogic.getResultForValue(data.getInt("aqi")));
                        mTextViewResult.setBackgroundColor(mLogic.getColorForValue(data.getInt("aqi")));
                        mTextViewWind.setText(data.getString("windPower") + "风" + data.getString("windPowerValue") + "级  温度" + data.getString("temperature") + "℃ 湿度"+data.getString("humidity")+"%");
                        mTextViewRank.setText("在全国338个城市中排名" + data.getString("commonOrderAqi") + "位>>");
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


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.text_view_rank:
                startActivity(new Intent(getActivity(), CityRankActivity.class));
                break;
            case R.id.text_view_calendar:
                startActivity(new Intent(getActivity(), CalendarActivity.class));
                break;
            case R.id.text_view_city:
                startActivity(new Intent(getActivity(), ChooseCityActivity.class));
                break;
        }
    }

    private void initData(int pm25, int pm10, int so2, int o3, int co, int no2, int first) {
        if(mPollutantsList!=null){
            mPollutantsList.clear();
        }else{
            mPollutantsList = new ArrayList<>();
        }

        Pollutants p1 = new Pollutants();
        Pollutants p2 = new Pollutants();
        Pollutants p3 = new Pollutants();
        Pollutants p4 = new Pollutants();
        Pollutants p5 = new Pollutants();
        Pollutants p6 = new Pollutants();

        p1.setValue(pm25 + "um/m2");
        p1.setTypeEn("PM2.5");
        p1.setType("细颗粒物");

        p2.setValue(pm10 + "um/m2");
        p2.setTypeEn("PM10");
        p2.setType("可吸入颗粒物");

        p3.setValue(co + "um/m2");
        p3.setTypeEn("CO");
        p3.setType("一氧化碳");

        p4.setValue(no2 + "um/m2");
        p4.setTypeEn("NO2");
        p4.setType("二氧化氮");

        p5.setValue(so2 + "um/m2");
        p5.setTypeEn("SO2");
        p5.setType("二氧化硫");

        p6.setValue(o3 + "um/m2");
        p6.setTypeEn("O3");
        p6.setType("臭氧");

        switch (first) {
            case 1:
                p1.setType("首要污染物");
                p1.setFirst(true);
                break;
            case 2:
                p2.setType("首要污染物");
                p2.setFirst(true);
                break;
            case 3:
                p3.setType("首要污染物");
                p3.setFirst(true);
                break;
            case 4:
                p4.setType("首要污染物");
                p4.setFirst(true);
                break;
            case 5:
                p5.setType("首要污染物");
                p5.setFirst(true);
                break;
            case 6:
                p6.setType("首要污染物");
                p6.setFirst(true);
                break;
        }

        mPollutantsList.add(p1);
        mPollutantsList.add(p2);
        mPollutantsList.add(p3);
        mPollutantsList.add(p4);
        mPollutantsList.add(p5);
        mPollutantsList.add(p6);

        mAdapter.notifyDataSetChanged();

    }

    public class SpaceItemDecoration extends RecyclerView.ItemDecoration {

        private int space;

        public SpaceItemDecoration(int space) {
            this.space = space;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            if (parent.getChildPosition(view) != 0) {
                outRect.top = space;
                outRect.bottom = space;
            }
        }
    }

//    @Subscribe
//    public void onEventMainThread(CityEvent event){
//        Log.e("2017","city_choose_1");
//        mTextViewCity.setText(SharedPreferenceUtils.getCurrentCityName(getActivity()));
//        loadData();
//    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
