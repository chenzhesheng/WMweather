package weather.wm.com.wmweather.home.ui;

import android.content.Intent;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.Toast;

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
import weather.wm.com.wmweather.common.units.RequestUtils;
import weather.wm.com.wmweather.common.units.UrlUtils;
import weather.wm.com.wmweather.home.logic.CityRankAdapter;

public class CityRankActivity extends AppCompatActivity implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {

    private ProgressBar mProgressBar;
    private RecyclerView mRecyclerViewRank;
    private EditText mEditTextSearch;
    private PopupWindow mPopupWindowScreening;
    private RadioGroup mRadioGroupCity;
    private RadioGroup mRadioGroupTime;
    private RadioGroup mRadioGroupPollutant;

    private CityRankAdapter mAdapter;
    private List<Rank> mRankList = new ArrayList<>();
    private String mCity = "338";
    private String mTime = "year";
    private String mKey = "";
    private String mKpi = "api";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_rank);
        findViewById(R.id.text_view_back).setOnClickListener(this);
        findViewById(R.id.text_view_screening).setOnClickListener(this);
        findViewById(R.id.text_view_history_rank).setOnClickListener(this);
        mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);
        mRecyclerViewRank = (RecyclerView) findViewById(R.id.recycler_view_rank);
        mEditTextSearch = (EditText) findViewById(R.id.edit_view_search);
        mRecyclerViewRank.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerViewRank.addItemDecoration(new SpaceItemDecoration(10));
        mAdapter = new CityRankAdapter(this, mRankList);
        mRecyclerViewRank.setAdapter(mAdapter);
        initPopupWindow();
        mEditTextSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                mKey = mEditTextSearch.getText().toString().trim();
                loadData(mCity, mTime, mKey, mKpi);
            }
        });
        loadData(mCity, mTime, mKey, mKpi);
    }

    private void initPopupWindow() {
        View view = LayoutInflater.from(this).inflate(R.layout.layout_city_rank_screening_view, null);
        mRadioGroupCity = (RadioGroup) view.findViewById(R.id.radio_group_city);
        mRadioGroupTime = (RadioGroup) view.findViewById(R.id.radio_group_time);
        mRadioGroupPollutant = (RadioGroup) view.findViewById(R.id.radio_group_pollutants);
        mRadioGroupTime.setOnCheckedChangeListener(this);
        mRadioGroupCity.setOnCheckedChangeListener(this);
        mRadioGroupPollutant.setOnCheckedChangeListener(this);
        mPopupWindowScreening = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mPopupWindowScreening.setOutsideTouchable(true);
        mPopupWindowScreening.setBackgroundDrawable(new BitmapDrawable());
        mPopupWindowScreening.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                loadData(mCity, mTime, mKey, mKpi);
            }
        });
    }

    private void loadData(String city, String time, String key, String kpi) {
        mProgressBar.setVisibility(View.VISIBLE);
        Map<String, String> header = new HashMap<>();
        header.put("token", UrlUtils.TOKEN);
        Map<String, String> body = new HashMap<>();
        body.put("city", city);
        body.put("time", time);
        body.put("key", key);
        body.put("kpi", kpi);
        RequestUtils.post(UrlUtils.CITY_RANK, header, body, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                mProgressBar.setVisibility(View.GONE);
                mRankList.clear();
                try {
                    Log.e("2017", "ss= " + response);

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
                            mRankList.add(rank);
                        }
                        Log.e("2017", "mRankList= " + mRankList.size());
                        mAdapter.notifyDataSetChanged();

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mProgressBar.setVisibility(View.GONE);
                Toast.makeText(CityRankActivity.this, "请求错误 " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.text_view_back:
                finish();
                break;
            case R.id.text_view_screening:
                mPopupWindowScreening.showAsDropDown(mEditTextSearch);
                break;
            case R.id.text_view_history_rank:
                startActivity(new Intent(this, HistoryRankActivity.class));
                break;
        }
    }


    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        switch (radioGroup.getId()) {
            case R.id.radio_group_city:
                switch (radioGroup.getCheckedRadioButtonId()) {
                    case R.id.radio_button_city_74:
                        mCity = "74";
                        break;
                    case R.id.radio_button_city_338:
                        mCity = "338";
                        break;
                    case R.id.radio_button_city_2_26:
                        mCity = "226";
                        break;
                }
                break;
            case R.id.radio_group_time:
                switch (radioGroup.getCheckedRadioButtonId()) {
                    case R.id.radio_button_rank_year:
                        mTime = "year";
                        break;
                    case R.id.radio_button_rank_month:
                        mTime = "month";
                        break;
                    case R.id.radio_button_rank_day:
                        mTime = "day";
                        break;
                    case R.id.radio_button_rank_hour:
                        mTime = "hour";
                        break;
                }
                break;
            case R.id.radio_group_pollutants:
                switch (radioGroup.getCheckedRadioButtonId()) {
                    case R.id.radio_button_aqi:
                        mKpi = "aqi";
                        break;
                    case R.id.radio_button_PM25:
                        mKpi = "pm25";
                        break;
                }
                break;
        }
    }
}
