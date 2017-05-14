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
import weather.wm.com.wmweather.common.bean.city;

/**
 * Created by HelloKiki on 2017/4/15.
 */

public class ChooseCityAdapter extends RecyclerView.Adapter<ChooseCityAdapter.MyViewHolder> {

    private List<city> mCityNameList;
    private LayoutInflater mInflater;
    private OnItemClickListener listener;

    public ChooseCityAdapter(Context context, List<city> cityNameList) {
        if (cityNameList != null) {
            mCityNameList = cityNameList;
        } else {
            mCityNameList = new ArrayList<>();
        }
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(mInflater.inflate(R.layout.layout_city_list_item, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.textView.setText(mCityNameList.get(position).getName());
        holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    listener.onItemClick(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mCityNameList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textView;

        public MyViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.text_view_city);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
