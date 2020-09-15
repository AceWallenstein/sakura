package com.example.sakura.ui.activity;

import android.content.res.Configuration;
import android.view.View;
import android.view.WindowManager;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sakura.R;
import com.example.sakura.adapter.PlayDirAdapter;
import com.example.sakura.base.BaseAdapter;
import com.example.sakura.base.BaseMvpActivity;
import com.example.sakura.common.Constant;
import com.example.sakura.contact.PlayContract;
import com.example.sakura.data.bean.ComicInfo;
import com.example.sakura.presenter.PlayPresenter;
import com.example.sakura.data.resp.ComicInfoResp;
import com.example.sakura.widget.CustomVideoView;

public class PlayActivity extends BaseMvpActivity<PlayPresenter> implements PlayContract.V {
    private TextView mTvTitle;
    private CustomVideoView mVvPlayer;
    private RecyclerView mRvDir;
    private PlayDirAdapter adapter;
    private ConstraintLayout.LayoutParams layoutParams;


    @Override
    protected int layoutId() {
        return R.layout.activity_play;
    }

    @Override
    protected void initView() {
        mTvTitle = findViewById(R.id.tv_title);
        mVvPlayer = findViewById(R.id.vv_player);
        mRvDir = findViewById(R.id.rv_dir);
        mRvDir.setLayoutManager(new GridLayoutManager(this, 4));
        adapter = new PlayDirAdapter(this);
        mRvDir.setAdapter(adapter);

        MediaController mediaController = new MediaController(this);
        mVvPlayer.setMediaController(mediaController);
        mediaController.setMediaPlayer(mVvPlayer);


    }

    @Override
    protected void initListener() {
        mVvPlayer.setOnPreparedListener((mp) -> mVvPlayer.start());
    }

    @Override
    protected void loadData() {
        super.loadData();
        String num = getIntent().getStringExtra(Constant.COMIC_NUM);
        mTvTitle.setText(getIntent().getStringExtra(Constant.COMIC_TITLE) + "   " +
                num);
        mPresenter.getPlayInfo(getIntent().getStringExtra(Constant.PLAY_URL), num);
    }

    @Override
    protected PlayPresenter createPresenter() {
        return new PlayPresenter();
    }

    @Override
    public void onResult(ComicInfoResp t) {
        mVvPlayer.setVideoPath(t.getCurrentNumPath());
        adapter.setData(t.getComicInfoList());
        adapter.setOnClickListener(new BaseAdapter.OnclickListener<ComicInfo>() {
            @Override
            public void onClick(View v, ComicInfo info) {
                if (mVvPlayer != null) {
                    mVvPlayer.stopPlayback();
                    mVvPlayer.setVideoPath(info.getNumUrl());
                }
            }
        });
    }

    @Override
    public void onError(Throwable e) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mVvPlayer.stopPlayback();
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        //我布局中videoView的父控件是RelativeLayout所以使用RelativeLayout.LayoutParams
        layoutParams = (ConstraintLayout.LayoutParams) mVvPlayer.getLayoutParams();
        if (newConfig.orientation == 2)// 横屏
        {
            setFullScreenPlay();
        } else {
//            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
//            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            WindowManager wm = (WindowManager) getSystemService(WINDOW_SERVICE);
            android.view.Display display = wm.getDefaultDisplay();
            int width_01 = display.getWidth();
            int height_01 = width_01*9/16;//视频比例。
            layoutParams.width = width_01;
            layoutParams.height = height_01;//恢复控件的高强
            mTvTitle.setVisibility(View.VISIBLE);
            mVvPlayer.setLayoutParams(layoutParams);

        }

    }

    private void setFullScreenPlay() {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);//设置videoView全屏播放
//        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);//设置videoView横屏播放
        if (this.getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        //获取屏幕的宽高 height_01 ，width_01
        WindowManager wm = (WindowManager) getSystemService(WINDOW_SERVICE);
        android.view.Display display = wm.getDefaultDisplay();
        int height_01 = display.getHeight();
        int width_01 = display.getWidth();
        layoutParams.height = height_01;//设置 当控件的高强
        layoutParams.width = width_01;
        mVvPlayer.setLayoutParams(layoutParams); // 使设置好的布局参数应用到控件
        mTvTitle.postDelayed(()->{
           mTvTitle.setVisibility(View.GONE);
        },1000);
    }
}
