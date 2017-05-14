package com.example.icufangapp.welcome.ui;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ChenZheSheng on 2016/7/15.
 */

public class GuideAdapter extends FragmentPagerAdapter {
    private List<Fragment> mFragments;

    public GuideAdapter(FragmentManager fm) {
        super(fm);
    }

    public GuideAdapter(FragmentManager fm ,List<Fragment> fragments){
        this(fm);
        this.mFragments = fragments;
        if(mFragments == null){
            mFragments = new ArrayList<>();
        }
    }


    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }
}
