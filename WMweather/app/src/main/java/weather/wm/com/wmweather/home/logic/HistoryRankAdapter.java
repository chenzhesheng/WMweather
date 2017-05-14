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
import weather.wm.com.wmweather.common.units.DateUtils;

/**
 * Created by HelloKiki on 2017/3/16.
 */

public class HistoryRankAdapter extends RecyclerView.Adapter<HistoryRankAdapter.MyViewHolder> {

    private List<Rank> mRankLists;
    private LayoutInflater mInflater;

    public HistoryRankAdapter(Context context, List<Rank> ranks) {
        if (ranks != null) {
            mRankLists = ranks;
        } else {
            mRankLists = new ArrayList<>();
        }
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(mInflater.inflate(R.layout.layout_history_rank_item, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Rank rank = mRankLists.get(position);
        holder.textViewRank.setText(rank.getRank() + "");
        holder.textViewAQI.setText(rank.getAqi()+"");
        holder.textViewPollutant.setText(rank.getFirstAqi());
        holder.textViewTime.setText(rank.getTime().split(" ")[0]);

        holder.textViewAQI.setBackgroundColor(ColorUtils.getColorForValue(rank.getAqi()));
    }

    @Override
    public int getItemCount() {
        return mRankLists.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textViewRank;
        TextView textViewAQI;
        TextView textViewPollutant;
        TextView textViewTime;

        public MyViewHolder(View itemView) {
            super(itemView);
            textViewRank = (TextView) itemView.findViewById(R.id.text_view_rank);
            textViewAQI = (TextView) itemView.findViewById(R.id.text_view_aqi);
            textViewPollutant = (TextView) itemView.findViewById(R.id.text_view_pollutant);
            textViewTime = (TextView) itemView.findViewById(R.id.text_view_time);
        }
    }
}
