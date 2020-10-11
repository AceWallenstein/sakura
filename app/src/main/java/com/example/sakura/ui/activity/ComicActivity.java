package com.example.sakura.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.sakura.App;
import com.example.sakura.R;
import com.example.sakura.adapter.ComicDirAdapter;
import com.example.sakura.base.BaseAdapter;
import com.example.sakura.base.BaseMvpActivity;
import com.example.sakura.common.Constant;
import com.example.sakura.contact.ComicContract;
import com.example.sakura.data.bean.ComicDetail;
import com.example.sakura.data.bean.ComicDir;
import com.example.sakura.data.greendao.ComicDetailDao;
import com.example.sakura.presenter.ComicPresenter;
import com.example.sakura.utils.LoggerUtils;
import com.example.sakura.utils.SharedPreferencesUtil;

public class ComicActivity extends BaseMvpActivity<ComicPresenter> implements ComicContract.V {

    private ImageView mIvComicPic;
    private TextView mTvName;
    private TextView mTvDesc;
    private RecyclerView rvDir;
    private TextView reverse;

    private ComicDirAdapter adapter;
    private boolean reverserFlag;
    private ImageView mCollection;
    private boolean isCollected;//收藏
    private ComicDetail mComicDetail;

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);

    }

    @Override
    protected int layoutId() {
        return R.layout.activity_comic;
    }

    @Override
    protected void initView() {
        rvDir = findViewById(R.id.rv_dir);
        adapter = new ComicDirAdapter(this);
        mIvComicPic = findViewById(R.id.iv_comic_pic);
        mTvName = findViewById(R.id.tv_name);
        mTvDesc = findViewById(R.id.tv_desc);
        reverse = findViewById(R.id.reverse);
        mCollection = findViewById(R.id.collection);

        rvDir.setAdapter(adapter);
        rvDir.setLayoutManager(new GridLayoutManager(this, 4));
    }

    @Override
    protected void initListener() {
        super.initListener();
        reverse.setOnClickListener((v) -> {
            reverserFlag = !reverserFlag;
            if (reverserFlag) {
                reverse.setText("顺序");
            } else {
                reverse.setText("逆序");
            }
            adapter.reverse();
        });
        mCollection.setOnClickListener((v) -> {
            isCollected = !isCollected;
            mCollection.setSelected(isCollected);
            if (mComicDetail != null) {
                SharedPreferencesUtil.putData(mComicDetail.getTitle(), isCollected);
                ComicDetailDao comicDetailDao = App.getDaoInstant().getComicDetailDao();
                if (isCollected) {
                    LoggerUtils.d(TAG, "initListener: " + mComicDetail.getHref());
                    comicDetailDao.insert(mComicDetail);
                } else {
                    if (comicDetailDao.hasKey(mComicDetail)) {
                        comicDetailDao.delete(mComicDetail);
                    }
                }
            }
        });
    }

    @Override
    protected void loadData() {
        super.loadData();
        mPresenter.getComicDetail(getIntent().getStringExtra(Constant.PAGE_URL));
    }

    @Override
    protected ComicPresenter createPresenter() {
        return new ComicPresenter();
    }

    @Override
    public void onResult(ComicDetail comicDetail) {
        if (comicDetail != null) {
            mComicDetail = comicDetail;
            isCollected = (boolean) SharedPreferencesUtil.getData(mComicDetail.getTitle(), false);
            mCollection.setSelected(isCollected);
            mTvName.setText(comicDetail.getTitle());
            mTvDesc.setText(comicDetail.getDesc());
            Glide.with(this).load(comicDetail.getImgUrl()).placeholder(R.drawable.outman).into(mIvComicPic);
            adapter.setData(comicDetail.getDirs());
            reverse.setEnabled(true);
            mCollection.setClickable(true);
        }
        adapter.setOnClickListener(new BaseAdapter.OnclickListener<ComicDir>() {
            @Override
            public void onClick(View v, ComicDir comicDir) {
                if (comicDir != null) {
                    //去掉尾部的“/”
                    String url = Constant.BASE_URL.substring(0, Constant.BASE_URL.length() - 1);
                    Intent intent = new Intent(ComicActivity.this,
                            PlayActivity.class);
                    intent.putExtra(Constant.PLAY_URL, url + comicDir.getHref());
                    intent.putExtra(Constant.COMIC_NUM, comicDir.getNum());
                    intent.putExtra(Constant.COMIC_TITLE, comicDetail.getTitle());
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    public void onError(Throwable e) {

    }

}
