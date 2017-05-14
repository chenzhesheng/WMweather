package weather.wm.com.wmweather.realtime.logic;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import weather.wm.com.wmweather.R;
import weather.wm.com.wmweather.common.bean.RealTimeData;
import weather.wm.com.wmweather.common.bean.RealTimeRankData;
import weather.wm.com.wmweather.realtime.ui.RealTimeDataView;

/**
 * Created by HelloKiki on 2017/3/15.
 */

public class RealTimeDataAdapter extends RecyclerView.Adapter<RealTimeDataAdapter.MyViewHolder> {

    private List<RealTimeRankData> mDataList;
    private LayoutInflater mInflater;
    private OnItemClickListener mListener;

    public RealTimeDataAdapter(Context context, List<RealTimeRankData> dataList) {
        if (dataList != null) {
            mDataList = dataList;
        } else {
            mDataList = new ArrayList<>();
        }
        mInflater = LayoutInflater.from(context);

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(mInflater.inflate(R.layout.layout_real_time_data_item, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        RealTimeRankData data = mDataList.get(position);
        holder.realTimeDataView.setRank(position+1);
        holder.realTimeDataView.setAddress(data.getStationName());
        holder.realTimeDataView.setPollutant(data.getValue());
        holder.realTimeDataView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListener != null) {
                    mListener.onItemClick(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        RealTimeDataView realTimeDataView;

        public MyViewHolder(View itemView) {
            super(itemView);
            realTimeDataView = (RealTimeDataView) itemView.findViewById(R.id.real_time_data_view);
        }
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mListener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

}
