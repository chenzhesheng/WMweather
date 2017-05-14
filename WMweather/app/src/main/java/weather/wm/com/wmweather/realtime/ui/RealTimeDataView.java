package weather.wm.com.wmweather.realtime.ui;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import weather.wm.com.wmweather.R;

/**
 * Created by HelloKiki on 2017/3/15.
 */

public class RealTimeDataView extends LinearLayout {
    TextView mTextViewRank;
    TextView mTextViewAddress;
    TextView mTextViewPollutant;

    public RealTimeDataView(Context context) {
        this(context, null);
    }

    public RealTimeDataView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RealTimeDataView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflate(context, R.layout.layout_real_time_data_view, this);
        mTextViewRank = (TextView) findViewById(R.id.text_view_rank);
        mTextViewAddress = (TextView) findViewById(R.id.text_view_address);
        mTextViewPollutant = (TextView) findViewById(R.id.text_view_pollutants);
    }

    public void setRank(int rank) {
        mTextViewRank.setText(rank + "");
    }

    public void setAddress(String address) {
        mTextViewAddress.setText(address);
    }

    public void setPollutant(int value) {
        mTextViewPollutant.setText(value + "");

        if (value > 0 && value <= 50) {
            mTextViewPollutant.setBackgroundColor(Color.parseColor("#49db00"));
        } else if (value > 50 && value <= 100) {
            mTextViewPollutant.setBackgroundColor(Color.parseColor("#ffff00"));
        } else if (value > 100 && value <= 150) {
            mTextViewPollutant.setBackgroundColor(Color.parseColor("#ff6d00"));
        } else if (value > 150 && value <= 200) {
            mTextViewPollutant.setBackgroundColor(Color.parseColor("#ff0000"));
        } else if (value > 200 && value <= 300) {
            mTextViewPollutant.setBackgroundColor(Color.parseColor("#9b004c"));
        } else if (value > 300) {
            mTextViewPollutant.setBackgroundColor(Color.parseColor("#920024"));
        } else {
            mTextViewPollutant.setBackgroundColor(Color.parseColor("#49db00"));
        }
    }

}
