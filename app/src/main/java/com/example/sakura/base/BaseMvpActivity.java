package com.example.sakura.base;

import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.GridView;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sakura.R;
import com.example.sakura.adapter.PopAdapter;
import com.example.sakura.data.resp.ComicInfoResp;
import com.example.sakura.ui.activity.PlayActivity;

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
