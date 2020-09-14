package com.example.sakura.base;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public abstract class BaseMvpFragment<P extends BasePresenter> extends BaseFragment {

    protected P mPresenter;
    private boolean isVisible;
    private boolean isPrepare;
    private boolean isLoaded;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(layoutId(), container, false);
        mPresenter = createPresenter();
        mPresenter.attachView(this);
        init(view);
        isPrepare = true;
        return view;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();
        initListener();
    }

    public void  lazyLoad(){
        if(isVisible||isPrepare||!isLoaded)
            return;
        onLazyLoad();
        isLoaded = true;
    }

    public   void onLazyLoad(){

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser){
            isVisible = true;
        }else {
            isVisible = false;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.detachView(this);
    }

    protected abstract int layoutId();

    protected abstract void init(View view);

    protected abstract void initData();

    protected abstract void initListener();

    protected abstract P createPresenter();


}
