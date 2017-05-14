package weather.wm.com.wmweather.common.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import weather.wm.com.wmweather.R;

/**
 * Created by HelloKiki on 2017/3/12.
 */

public class PollutantsItemView extends LinearLayout {

    public PollutantsItemView(Context context) {
        this(context, null);
    }

    public PollutantsItemView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PollutantsItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflate(context, R.layout.layout_pollutants_item_view, this);

    }
}
