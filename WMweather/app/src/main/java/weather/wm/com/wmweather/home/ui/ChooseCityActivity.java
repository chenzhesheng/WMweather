package weather.wm.com.wmweather.home.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

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
import weather.wm.com.wmweather.common.bean.city;
import weather.wm.com.wmweather.common.units.RequestUtils;
import weather.wm.com.wmweather.common.units.SharedPreferenceUtils;
import weather.wm.com.wmweather.common.units.UrlUtils;
import weather.wm.com.wmweather.home.logic.ChooseCityAdapter;

public class ChooseCityActivity extends AppCompatActivity implements View.OnClickListener, ChooseCityAdapter.OnItemClickListener {

    private RecyclerView mRecyclerView;
    private List<city> mProvinces = new ArrayList<>();
    private List<city> mCitys = new ArrayList<>();
    private ChooseCityAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_city);
        findViewById(R.id.text_view_back).setOnClickListener(this);
        mAdapter = new ChooseCityAdapter(this, mCitys);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(this);
        loadProvinces();
    }

    public void loadProvinces() {
        Map<String, String> header = new HashMap<>();
        header.put("token", UrlUtils.TOKEN);
        RequestUtils.post(UrlUtils.PROVINCE, header, null, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("2017","PROVINCE->"+response);
                try {
                    JSONObject object = new JSONObject(response);
                    if (object.getLong("status") == 2000000) {
                        JSONArray array = object.getJSONArray("data");
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject data = array.getJSONObject(i);
                            city city = new city();
                            city.setId(data.getInt("id"));
                            city.setName(data.getString("name"));
                            mProvinces.add(city);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if (mProvinces.size() > 0)
                    loadCity(mProvinces.get(0).getId());

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
    }

    private void loadCity(int id) {
        Map<String, String> header = new HashMap<>();
        header.put("token", UrlUtils.TOKEN);
        Map<String, String> body = new HashMap<>();
        body.put("province", id + "");
        RequestUtils.post(UrlUtils.CITY, header, body, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("2017","city->"+response);
                try {
                    JSONObject object = new JSONObject(response);
                    if (object.getLong("status") == 2000000) {
                        JSONArray array = object.getJSONArray("data");
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject data = array.getJSONObject(i);
                            city city = new city();
                            city.setId(data.getInt("id"));
                            city.setName(data.getString("name"));
                            mCitys.add(city);
                        }
                    }
                    mAdapter.notifyDataSetChanged();
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

    @Override
    public void onClick(View view) {
        finish();
    }

    @Override
    public void onItemClick(int position) {
        city city=  mCitys.get(position);
        SharedPreferenceUtils.setCurrentCityId(this,city.getId()+"");
        SharedPreferenceUtils.setCurrentCityName(this,city.getName());
//        EventBus.getDefault().post(new CityEvent());
        finish();
    }
}
