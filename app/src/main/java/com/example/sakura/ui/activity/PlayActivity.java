package com.example.sakura.ui.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.os.Parcelable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sakura.R;
import com.example.sakura.adapter.PlayDirAdapter;
import com.example.sakura.adapter.PopAdapter;
import com.example.sakura.base.BaseAdapter;
import com.example.sakura.base.BaseMvpActivity;
import com.example.sakura.common.Constant;
import com.example.sakura.contact.PlayContract;
import com.example.sakura.data.bean.ComicInfo;
import com.example.sakura.presenter.PlayPresenter;
import com.example.sakura.data.resp.ComicInfoResp;
import com.example.sakura.server.DownloadServer;
import com.example.sakura.utils.DrawableUtil;
import com.example.sakura.utils.SharedPreferencesUtil;
import com.example.sakura.widget.CustomVideoView;
import com.example.sakura.widget.LimitHeightRecyclerView;

import java.util.ArrayList;

public class PlayActivity extends BaseMvpActivity<PlayPresenter> implements PlayContract.V {
    private TextView mTvTitle;
    private CustomVideoView mVvPlayer;
    private RecyclerView mRvDir;
    private PlayDirAdapter adapter;
    private ConstraintLayout.LayoutParams layoutParams;
    private ImageView ivDownload;


    private LimitHeightRecyclerView rvPop;
    private PopAdapter popAdapter;
    private PopupWindow popupWindow;
    private ComicInfoResp mComicInfo;


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
        ivDownload = findViewById(R.id.iv_download);

    }



    @Override
    protected void initListener() {
        mVvPlayer.setOnPreparedListener((mp) -> mVvPlayer.start());
        ivDownload.setOnClickListener((v) -> {
            initPopWindow();
        });
        new DrawableUtil(mTvTitle, new DrawableUtil.OnDrawableListener() {
            @Override
            public void onLeft(View v, Drawable left) {
                finish();
            }

            @Override
            public void onRight(View v, Drawable right) {

            }
        });
    }
    private void initPopWindow() {
        View view = LayoutInflater.from(this).inflate(R.layout.pop_window, null);
        rvPop = view.findViewById(R.id.rv_pop);
        popAdapter = new PopAdapter(this);
        rvPop.setAdapter(popAdapter);
        rvPop.setLayoutManager(new GridLayoutManager(this, 4));
        if (mComicInfo != null) {
            popAdapter.setData(mComicInfo.getComicInfoList());
        }
        TextView tvDownload = view.findViewById(R.id.tv_download);
        TextView tvToDownload = view.findViewById(R.id.tv_to_download);
        tvDownload.setOnClickListener((v)->{
                toast("下载功能暂不可用！");
//            Intent intent = new Intent(this, DownloadServer.class);
//            intent.putExtra("comicName",getIntent().getStringExtra(Constant.COMIC_TITLE));
//            intent.putParcelableArrayListExtra("downloadData", (ArrayList<? extends Parcelable>) popAdapter.getDownloadList());
//            intent.setAction("download");
//            startService(intent);
        });
        tvToDownload.setOnClickListener((v)->{
            Intent intent = new Intent(this, HomeActivity.class);
            intent.putExtra(Constant.TODOWNLOADFRAGMENT,1);
            startActivity(intent);
        });
        popupWindow = new PopupWindow(view,
                GridView.MarginLayoutParams.MATCH_PARENT, GridView.MarginLayoutParams.WRAP_CONTENT);
        popupWindow.setAnimationStyle(R.style.Popupwindow);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);
        popupWindow.setClippingEnabled(true);
        Myy(0.5f);
        popupWindow.setOnDismissListener(() -> {
            // pop.dismiss(）方法调用时，回调该函数，点击外部时，也会回调该函数
            Myy(1);
            popAdapter.clear();
        });
    }

    /**
     *
     * @param b 透明度窗体
     */
    public void Myy(float b) {
        WindowManager.LayoutParams attributes = getWindow().getAttributes();
        attributes.alpha = b;
        getWindow().setAttributes(attributes);
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
        mComicInfo = t;
        mVvPlayer.setVideoPath(t.getCurrentNumPath());
        int currentPosition = (int) SharedPreferencesUtil.getData(mComicInfo.getCurrentNumPath(),0);
        if(currentPosition!=0)
            mVvPlayer.seekTo(currentPosition);
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
    public void onBackPressed() {
        if (popupWindow != null) {
            if (popupWindow.isShowing()) {
                popupWindow.dismiss();
                return;
        }
        }
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        int currentPosition = mVvPlayer.getCurrentPosition();
        SharedPreferencesUtil.putData(mComicInfo.getCurrentNumPath(),currentPosition);
        mVvPlayer.pause();
    }

    @Override
    protected void onStop() {
        super.onStop();
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
            int height_01 = width_01 * 9 / 16;//视频比例。
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
        mTvTitle.postDelayed(() -> {
            mTvTitle.setVisibility(View.GONE);
        }, 1000);
    }


}
