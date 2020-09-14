package com.example.sakura.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.sakura.R;
import com.example.sakura.base.BaseMvpActivity;
import com.example.sakura.base.BasePresenter;
import com.example.sakura.ui.fragment.HomeFragment;

public class HomeActivity extends BaseMvpActivity implements View.OnClickListener {

    private EditText mSearch;
    private ImageView mSetting;

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @Override
    protected int layoutId() {
        return R.layout.activity_home;
    }

    @Override
    protected void initView() {
        mSearch = findViewById(R.id.search);
        mSetting = findViewById(R.id.setting);
        getSupportFragmentManager().beginTransaction().replace(
                R.id.content,new HomeFragment()).commit();
    }

    @Override
    protected void initListener() {
        super.initListener();
        mSearch.setOnClickListener(this);
        mSetting.setOnClickListener(this);
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.search:
                toast(mSearch.getText().toString());
                break;
            case R.id.setting:
                toast("进入设置");

        }
    }
}
