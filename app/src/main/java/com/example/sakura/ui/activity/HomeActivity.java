package com.example.sakura.ui.activity;

import android.os.Bundle;

import com.example.sakura.R;
import com.example.sakura.base.BaseMvpActivity;
import com.example.sakura.base.BasePresenter;
import com.example.sakura.ui.fragment.HomeFragment;

public class HomeActivity extends BaseMvpActivity{
    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @Override
    protected int layoutId() {
        return R.layout.activity_home;
    }

    @Override
    protected void initView() {
        getSupportFragmentManager().beginTransaction().replace(
                R.id.content,new HomeFragment()).commit();
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }
}
