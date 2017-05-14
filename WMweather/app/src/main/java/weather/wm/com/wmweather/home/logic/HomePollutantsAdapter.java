package weather.wm.com.wmweather.home.logic;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.RelativeSizeSpan;
import android.text.style.SuperscriptSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import weather.wm.com.wmweather.R;
import weather.wm.com.wmweather.common.bean.Pollutants;
import weather.wm.com.wmweather.common.units.StringUtils;

/**
 * Created by HelloKiki on 2017/3/12.
 */

public class HomePollutantsAdapter extends RecyclerView.Adapter<HomePollutantsAdapter.MyViewHolder> {

    private LayoutInflater mInflater;
    private List<Pollutants> mPollutantsList;

    public HomePollutantsAdapter(Context context, List<Pollutants> pollutants) {
        mInflater = LayoutInflater.from(context);
        if (pollutants != null) {
            mPollutantsList = pollutants;
        } else {
            mPollutantsList = new ArrayList<>();
        }

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(mInflater.inflate(R.layout.layout_pollutants_item_view, null));
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Pollutants pollutants = mPollutantsList.get(position);
        SpannableString sValue = new SpannableString(pollutants.getValue());
        sValue.setSpan(new SuperscriptSpan(), sValue.length() - 1, sValue.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        sValue.setSpan(new RelativeSizeSpan(0.5f), sValue.length() - 1, sValue.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        if (sValue.length() > 5) {
            sValue.setSpan(new RelativeSizeSpan(1.5f), 0, sValue.length() - 5, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }

        SpannableString sType = new SpannableString(pollutants.getTypeEn());
        if (StringUtils.IsSubscript(pollutants.getTypeEn())) {
        //    sType.setSpan(new SubscriptSpan(), sType.length() - 1, sType.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            sType.setSpan(new RelativeSizeSpan(0.5f), sType.length() - 1, sType.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }

        holder.mTextViewValue.setText(sValue);
        holder.mTextViewTypeEn.setText(sType);
        holder.mTextViewType.setText(pollutants.getType());
        if (pollutants.isFirst()) {
            holder.mTextViewType.setTextColor(Color.parseColor("#ffee00"));
        }
    }

    @Override
    public int getItemCount() {
        return mPollutantsList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView mTextViewValue;
        TextView mTextViewTypeEn;
        TextView mTextViewType;

        public MyViewHolder(View itemView) {
            super(itemView);
            mTextViewValue = (TextView) itemView.findViewById(R.id.text_view_value);
            mTextViewTypeEn = (TextView) itemView.findViewById(R.id.text_view_type_en);
            mTextViewType = (TextView) itemView.findViewById(R.id.text_view_type);
        }
    }
}
