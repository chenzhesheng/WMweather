package weather.wm.com.wmweather.account.logic;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import weather.wm.com.wmweather.R;
import weather.wm.com.wmweather.common.bean.area;
import weather.wm.com.wmweather.common.bean.city;

/**
 * Created by ChenZheSheng on 2017/4/10.
 */

public class UpdataListAdapter extends RecyclerView.Adapter<UpdataListAdapter.ViewHolder> {
    private Context context;
    private List<String> data;
    private List<city> mCities;
    private List<area> mAreas;
    private ClickItemLIstener mClickItemLIstener;
    private int number;

    public UpdataListAdapter(Context context, List data,int number) {
        this.context = context;
        this.number = number;
        if(number == 0) {
            this.data = data;
        }else if (number == 1){
            this.mAreas = data;
        }else if (number == 2){
            this.mCities = data;
        }
    }

    public void setClickItemLIstener(ClickItemLIstener lIstener){
        this.mClickItemLIstener = lIstener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.layout_edit_list_item,null));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        if(number == 0){
            holder.mTextViewItem.setText(data.get(position));
        }else if(number == 1){
            holder.mTextViewItem.setText(mAreas.get(position).getName());
        }else if(number == 2){
            holder.mTextViewItem.setText(mCities.get(position).getName());
        }
        holder.mTextViewItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mClickItemLIstener.setClickItemListener(position,number);
            }
        });
    }

    @Override
    public int getItemCount() {
        if(number == 0) {
            return data.size();
        }else if(number == 1){
            return mAreas.size();
        }else if(number == 2){
            return mCities.size();
        }
        return 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        private TextView mTextViewItem;

        public ViewHolder(View itemView) {
            super(itemView);
            mTextViewItem = (TextView) itemView.findViewById(R.id.text_view_item);
        }
    }
}
