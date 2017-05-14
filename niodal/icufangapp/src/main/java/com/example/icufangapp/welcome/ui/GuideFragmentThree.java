package com.example.icufangapp.welcome.ui;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.icufangapp.R;
import com.example.icufangapp.base.BaseFragment;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.netframe.core.Form;

/**
 * Created by ChenZheSheng on 2016/7/15*/
@ContentView(value = R.layout.fragment_guide_three)
public class GuideFragmentThree extends BaseFragment implements View.OnClickListener{
    @ViewInject(value = R.id.btn_guide_fragment_start_app)
    private Button btnStartApp;

    @Override
    public int setLayoutId() {
        return R.layout.fragment_guide_three;
    }

    @Override
    public void requestSuccess(Form form) {

    }

    @Override
    public void requestFail(Form form) {

    }

    @Override
    public void initView(View view) {
        btnStartApp.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Toast.makeText(getActivity(), "0.0", Toast.LENGTH_SHORT).show();
        Log.i("TAG","0.0.0.0.0.0.0.0");
    }

}
