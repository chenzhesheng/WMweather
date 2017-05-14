package weather.wm.com.wmweather.statistical;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import weather.wm.com.wmweather.R;
import weather.wm.com.wmweather.statistical.ui.SiteRankingFragment;
import weather.wm.com.wmweather.statistical.ui.StatContrastFragment;
import weather.wm.com.wmweather.statistical.ui.StatHistoryFragment;

/**
 * Created by HelloKiki on 2017/3/9.
 */

public class StatisticalFragment extends Fragment implements View.OnClickListener {
    private TextView mTextViewHistory;
    private TextView mTextViewDuibi;
    private TextView mTextViewZhandian;
    private ViewPager mViewPager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_statistical, null);
        mViewPager = (ViewPager) view.findViewById(R.id.view_pager);
        mTextViewHistory = (TextView) view.findViewById(R.id.text_view_statistical_stat_histroy);
        mTextViewDuibi = (TextView) view.findViewById(R.id.text_view_statistical_stat_duibi);
        mTextViewZhandian = (TextView) view.findViewById(R.id.text_view_statistical_stat_zhandian);
        StatisticalFragment.MainFragmentAdapter fragmentAdapter = new StatisticalFragment.MainFragmentAdapter(getChildFragmentManager());
        mViewPager.setAdapter(fragmentAdapter);
        mViewPager.addOnPageChangeListener(new StatisticalFragment.PagerChangeListener());
        mViewPager.setCurrentItem(0);
        mTextViewHistory.setOnClickListener(this);
        mTextViewDuibi.setOnClickListener(this);
        mTextViewZhandian.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.text_view_statistical_stat_histroy:
                titleSelect(0);
                mViewPager.setCurrentItem(0);
                break;
            case R.id.text_view_statistical_stat_duibi:
                titleSelect(1);
                mViewPager.setCurrentItem(1);
                break;
            case R.id.text_view_statistical_stat_zhandian:
                titleSelect(2);
                mViewPager.setCurrentItem(2);
                break;
        }
    }


    class PagerChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            switch (position) {
                case 0:
                    titleSelect(0);
                    break;
                case 1:
                    titleSelect(1);
                    break;
                case 2:
                    titleSelect(2);
                    break;
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

    class MainFragmentAdapter extends FragmentStatePagerAdapter {

        public MainFragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new StatHistoryFragment();
                case 1:
                    return new StatContrastFragment();
                case 2:
                    return new SiteRankingFragment();
            }
            return null;

        }

        @Override
        public int getCount() {
            return 3;
        }
    }

    private void titleSelect(int num){
        switch (num){
            case 0:
                Drawable drawable = getResources().getDrawable(R.drawable.top_sjx);
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                mTextViewHistory.setCompoundDrawables(null,null,null,drawable);
                mTextViewDuibi.setCompoundDrawables(null,null,null,null);
                mTextViewZhandian.setCompoundDrawables(null,null,null,null);
                break;
            case 1:
                Drawable drawable1 = getResources().getDrawable(R.drawable.top_sjx);
                drawable1.setBounds(0, 0, drawable1.getMinimumWidth(), drawable1.getMinimumHeight());
                mTextViewHistory.setCompoundDrawables(null,null,null,null);
                mTextViewDuibi.setCompoundDrawables(null,null,null,drawable1);
                mTextViewZhandian.setCompoundDrawables(null,null,null,null);
                break;
            case 2:
                Drawable drawable2 = getResources().getDrawable(R.drawable.top_sjx);
                drawable2.setBounds(0, 0, drawable2.getMinimumWidth(), drawable2.getMinimumHeight());
                mTextViewHistory.setCompoundDrawables(null,null,null,null);
                mTextViewDuibi.setCompoundDrawables(null,null,null,null);
                mTextViewZhandian.setCompoundDrawables(null,null,null,drawable2);
                break;
        }
    }
}