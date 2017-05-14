package weather.wm.com.wmweather.statistical.logic;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import weather.wm.com.wmweather.R;
import weather.wm.com.wmweather.common.bean.SiteRank;

/**
 * Created by ChenZheSheng on 2017/3/19.
 */

public class StatRanKingAdapter extends RecyclerView.Adapter<StatRanKingAdapter.StatViewHolder> {
    private List<SiteRank> data;
    private Context mContext;

    public StatRanKingAdapter(List<SiteRank> data, Context context) {
        if(data == null){
            data = new ArrayList<>();
        }
        this.data = data;
        mContext = context;
    }

    @Override
    public StatViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        StatViewHolder holder = new StatViewHolder(LayoutInflater.from(mContext).inflate(R.layout.layout_stat_ranking,null));
        return holder;
    }

    @Override
    public void onBindViewHolder(StatViewHolder holder, int position) {
        SiteRank siteRank = data.get(position);
        holder.mTextViewRank.setText(siteRank.getRank());
        holder.mTextViewAddress.setText(siteRank.getAddress());
        holder.mTextViewPollutants.setText(siteRank.getPollutants());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class  StatViewHolder extends RecyclerView.ViewHolder{
        private TextView mTextViewRank;
        private TextView mTextViewAddress;
        private TextView mTextViewPollutants;

        public StatViewHolder(View itemView) {
            super(itemView);
            mTextViewRank = (TextView) itemView.findViewById(R.id.text_view_rank);
            mTextViewAddress = (TextView) itemView.findViewById(R.id.text_view_address);
            mTextViewPollutants = (TextView) itemView.findViewById(R.id.text_view_pollutants);
        }
    }
}
