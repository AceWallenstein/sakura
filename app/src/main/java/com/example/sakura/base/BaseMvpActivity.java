package com.example.sakura.base;

import android.os.Bundle;

import androidx.annotation.Nullable;

public abstract class BaseMvpActivity<P extends BasePresenter> extends BaseActivity {

    protected P mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData(savedInstanceState);
        setContentView(layoutId());
        mPresenter = createPresenter();
        if (mPresenter != null) {
            mPresenter.attachView(this);
        }
        initView();
        initListener();
    }



    @Override
    protected void onStart() {
        super.onStart();
        loadData();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {

            mPresenter.detachView(this);
        }
    }

    protected  void initData(Bundle savedInstanceState){}

    protected  void loadData(){}


    protected abstract int layoutId();

    protected abstract void initView();

    protected void initListener() {

    }

    protected abstract P createPresenter();

}
