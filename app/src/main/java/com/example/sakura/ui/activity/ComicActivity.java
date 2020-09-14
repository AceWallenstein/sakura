package com.example.sakura.ui.activity;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.sakura.R;
import com.example.sakura.adapter.ComicDirAdapter;
import com.example.sakura.base.BaseAdapter;
import com.example.sakura.base.BaseMvpActivity;
import com.example.sakura.bean.ComicDetail;
import com.example.sakura.bean.ComicDir;
import com.example.sakura.common.Constant;
import com.example.sakura.contact.ComicContract;
import com.example.sakura.presenter.ComicPresenter;

public class ComicActivity extends BaseMvpActivity<ComicPresenter> implements ComicContract.V {

    private ImageView mIvComicPic;
    private TextView mTvName;
    private TextView mTvDesc;
    private RecyclerView rvDir;

    private ComicDirAdapter adapter;

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
        rvDir.setAdapter(adapter);
        rvDir.setLayoutManager(new GridLayoutManager(this,4));
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
            mTvName.setText(comicDetail.getTitle());
            mTvDesc.setText(comicDetail.getDesc());
            Glide.with(this).load(comicDetail.getImgUrl()).placeholder(R.drawable.outman).into(mIvComicPic);
            adapter.setData(comicDetail.getDirs());
        }
        adapter.setOnClickListener(new BaseAdapter.OnclickListener<ComicDir>() {
            @Override
            public void onClick(View v, ComicDir comicDir) {
                if (comicDir != null) {
                    //去掉尾部的“/”
                    String url = Constant.BASE_URL.substring(0, Constant.BASE_URL.length() - 1);
                    Intent intent = new Intent(ComicActivity.this,
                            PlayActivity.class);
                    intent.putExtra(Constant.PLAY_URL,url+comicDir.getHref());
                    intent.putExtra(Constant.COMIC_NUM,comicDir.getNum());
                    intent.putExtra(Constant.COMIC_TITLE,comicDetail.getTitle());
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    public void onError(Throwable e) {

    }
}
