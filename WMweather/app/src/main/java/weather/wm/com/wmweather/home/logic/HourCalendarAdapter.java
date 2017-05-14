package weather.wm.com.wmweather.home.logic;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.andexert.calendarlistview.library.HourCalendarView;

import weather.wm.com.wmweather.R;

/**
 * Created by HelloKiki on 2017/3/17.
 */

public class HourCalendarAdapter extends RecyclerView.Adapter<HourCalendarAdapter.MyViewHolder> {

    private LayoutInflater mInflater;

    public HourCalendarAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(mInflater.inflate(R.layout.layout_hour_calendar_item, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.hourCalendarView.setTitle("2017-3-18");

    }

    @Override
    public int getItemCount() {
        return 5;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        HourCalendarView hourCalendarView;

        public MyViewHolder(View itemView) {
            super(itemView);
            hourCalendarView = (HourCalendarView) itemView.findViewById(R.id.hour_calendar_view);
        }
    }
}
