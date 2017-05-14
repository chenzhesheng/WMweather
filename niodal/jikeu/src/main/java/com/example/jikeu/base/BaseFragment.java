package com.example.jikeu.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

@Deprecated
public abstract class BaseFragment extends Fragment{
    protected Toast toast;
    private TextView tvToast;
    public Activity context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(setLayoutId(),container,false);
        initView(view);
        return view;
    }

    public abstract int setLayoutId();


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        context = activity;
    }

    public abstract void initView(View view);
}
