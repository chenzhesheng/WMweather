package com.example.jikeu.myorder.view.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ChenZheSheng on 2016/8/14.
 */

public class OrderAdapter extends FragmentPagerAdapter {
    private List<Fragment> fragments;

    public OrderAdapter(FragmentManager fm) {
        super(fm);
    }

    public OrderAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        if(fragments != null&&fragments.size()>0){
            this.fragments = fragments;
        }else{
            this.fragments = new ArrayList<>();
        }
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}
