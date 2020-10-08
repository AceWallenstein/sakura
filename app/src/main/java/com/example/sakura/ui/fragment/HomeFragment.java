package com.example.sakura.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.sakura.R;
import com.example.sakura.adapter.ComicAdapter;
import com.example.sakura.adapter.ImageAdapter;
import com.example.sakura.base.BaseAdapter;
import com.example.sakura.base.BaseMvpFragment;
import com.example.sakura.common.Constant;
import com.example.sakura.contact.HomeContract;
import com.example.sakura.data.bean.CategoryBean;
import com.example.sakura.data.bean.Comic;
import com.example.sakura.data.bean.ComicDTO;
import com.example.sakura.presenter.HomePresenter;
import com.example.sakura.ui.activity.ComicActivity;
import com.example.sakura.ui.activity.SearchActivity;
import com.youth.banner.Banner;
import com.youth.banner.adapter.BannerImageAdapter;
import com.youth.banner.holder.BannerImageHolder;
import com.youth.banner.indicator.CircleIndicator;
import com.youth.banner.listener.OnBannerListener;

import java.util.List;

public class HomeFragment extends BaseMvpFragment<HomePresenter> implements HomeContract.V {
    Button button;
    private TextView tvCategory;
    private TextView tvMore;
    private RecyclerView rvContent;
    private LinearLayout comicContainer;
    private EditText mSearch;
    private ImageView mSetting;
    private Banner mBanner;

    @Override
    protected int layoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void init(View view) {
        mBanner = view.findViewById(R.id.banner);
        mBanner.addBannerLifecycleObserver(this);//添加生命周期观察者//
        comicContainer = view.findViewById(R.id.content_comic);
        mSearch = view.findViewById(R.id.search);
        mSearch.setFocusableInTouchMode(false);//不可编辑
        mSearch.setKeyListener(null);//不可粘贴，长按不会弹出粘贴框
        mSetting = view.findViewById(R.id.setting);
    }

    @Override
    protected void initData() {
        mPresenter.getComic();
    }

    @Override
    public void onLazyLoad() {

    }

    @Override
    public void initListener() {
        mSearch.setOnClickListener((v)->{
            startActivity(new Intent(mActivity,SearchActivity.class));
        });
//        mSearch.setOnKeyListener((v, keyCode, event) -> {
//            if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
//                // 执行发送消息等操作
//                if (TextUtils.isEmpty(mSearch.getText().toString()))
//                    return false;
//                Intent intent = new Intent(mActivity, SearchActivity.class);
//                intent.putExtra(Constant.SEARCH_WORD, mSearch.getText()
//                        .toString());
//                startActivity(intent);
//                return true;
//            }
////            return false;
//
//        });
        mSetting.setOnClickListener((v) -> toast("进入设置"));
    }

    @Override
    protected HomePresenter createPresenter() {
        return new HomePresenter();
    }

    @Override
    public void onResult(ComicDTO comicDTO) {
        List<CategoryBean> beans = comicDTO.getBeans();
        for (CategoryBean bean :
                beans) {
            loadComicView(bean);
        }
            initBanner(comicDTO.getBannerList());
    }

    private void initBanner(List<Comic> comicList) {
        ImageAdapter imageAdapter = new ImageAdapter(comicList, getContext());
        mBanner.setAdapter(imageAdapter).setIndicator(new CircleIndicator(getContext()));
        mBanner.setOnBannerListener(new OnBannerListener<Comic>(){
                                        @Override
                                        public void OnBannerClick(Comic data, int position) {
                                            Intent intent = new Intent(getContext(), ComicActivity.class);
                                            intent.putExtra(Constant.PAGE_URL,data.getUrl());
                                            startActivity(intent);
                                        }
                                    }
          );
        mBanner.start();
    }

    private void loadComicView(CategoryBean bean) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.comic_contrainer, null);
        tvCategory = view.findViewById(R.id.tv_category);
        tvMore = view.findViewById(R.id.tv_more);
        rvContent = view.findViewById(R.id.rv_content);
        tvCategory.setText(bean.getName());
        ComicAdapter comicAdapter = new ComicAdapter(getContext());
        comicAdapter.setData(bean.getComics());
        rvContent.setAdapter(comicAdapter);
        rvContent.setLayoutManager(new LinearLayoutManager(mActivity, RecyclerView.HORIZONTAL, false));
        tvMore.setOnClickListener((v) -> toast(bean.getMoreUrl()));
        comicAdapter.setOnClickListener(new BaseAdapter.OnclickListener<Comic>() {
            @Override
            public void onClick(View v, Comic comic) {
                String pageUrl = Constant.BASE_URL.substring(0, Constant.BASE_URL.length() - 1) + comic.getUrl();
                Log.d(TAG, pageUrl);
                Intent intent = new Intent(mActivity, ComicActivity.class);
                intent.putExtra(Constant.PAGE_URL, pageUrl);
                startActivity(intent);
            }
        });
        comicContainer.addView(view);
    }

    @Override
    public void onError(Throwable e) {

    }

}
