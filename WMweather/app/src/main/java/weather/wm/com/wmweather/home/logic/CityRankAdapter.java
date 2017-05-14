package weather.wm.com.wmweather.home.logic;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import weather.wm.com.wmweather.R;
import weather.wm.com.wmweather.common.bean.Rank;
import weather.wm.com.wmweather.common.units.ColorUtils;

/**
 * Created by HelloKiki on 2017/3/12.
 */

public class CityRankAdapter extends RecyclerView.Adapter<CityRankAdapter.MyViewHolder> {

    private LayoutInflater mInflater;
    private List<Rank> mRankList;

    public CityRankAdapter(Context context, List<Rank> ranks) {
        mInflater = LayoutInflater.from(context);
        if (ranks != null) {
            mRankList = ranks;
        } else {
            mRankList = new ArrayList<>();
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(mInflater.inflate(R.layout.layout_city_rank_item, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Rank rank = mRankList.get(position);
        holder.textViewRank.setText(rank.getRank() + "");
        holder.textViewProvince.setText(rank.getProvince());
        holder.textViewCity.setText(rank.getCity());
        holder.textViewAqi.setText(rank.getAqi() + "");
        holder.textViewPollutants.setText(rank.getFirstAqi());
        holder.textViewAqi.setBackgroundColor(ColorUtils.getColorForValue(rank.getAqi()));
    }

    @Override
    public int getItemCount() {
        return mRankList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textViewRank;
        TextView textViewProvince;
        TextView textViewCity;
        TextView textViewAqi;
        TextView textViewPollutants;

        public MyViewHolder(View itemView) {
            super(itemView);
            textViewRank = (TextView) itemView.findViewById(R.id.text_view_rank);
            textViewProvince = (TextView) itemView.findViewById(R.id.text_view_province);
            textViewCity = (TextView) itemView.findViewById(R.id.text_view_city);
            textViewAqi = (TextView) itemView.findViewById(R.id.text_view_aqi);
            textViewPollutants = (TextView) itemView.findViewById(R.id.text_view_pollutants);
        }
    }
}
