package com.example.sakura.ui.fragment;

import android.content.Intent;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sakura.R;
import com.example.sakura.adapter.TimelineAdapter;
import com.example.sakura.base.BaseAdapter;
import com.example.sakura.base.BaseMvpFragment;
import com.example.sakura.common.Constant;
import com.example.sakura.contact.TimelineContract;
import com.example.sakura.data.bean.Comic;
import com.example.sakura.presenter.TimeLinePresenter;
import com.example.sakura.ui.activity.ComicActivity;
import com.google.android.material.tabs.TabLayout;

import java.util.Calendar;
import java.util.List;

public class AnimeTimeLineFragment extends BaseMvpFragment<TimeLinePresenter> implements TimelineContract.V, TabLayout.OnTabSelectedListener {

    private TabLayout tabTimeline;
    private List<List<Comic>> result;
    private RecyclerView rvComicContent;
    private TimelineAdapter adapter;
    //当前星期几 1-7 分别表示 天 一 二三四五六
    private int theWeekday = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
    ;
    private int position;

    @Override
    protected int layoutId() {
        return R.layout.fragment_ani_time;
    }

    @Override
    protected void init(View view) {
        tabTimeline = view.findViewById(R.id.tab_timeline);
        rvComicContent = view.findViewById(R.id.rv_comic_content);
        for (String day :
                mActivity.getResources().getStringArray(R.array.week)) {
            TabLayout.Tab tab = tabTimeline.newTab().setText(day);
            tabTimeline.addTab(tab);
        }
        rvComicContent.setLayoutManager(new LinearLayoutManager(mActivity, RecyclerView.VERTICAL, false));
        adapter = new TimelineAdapter(mActivity);
        rvComicContent.setAdapter(adapter);


    }

    @Override
    protected void initData() {
        mPresenter.getTimeLineInfo();
        initCurrentComicData();
    }

    //根据当前时间显示当天的番剧
    private void initCurrentComicData() {
        position = theWeekday - 2;
        if (position < 0) {
            position = 6;
        }
        tabTimeline.getTabAt(position).select();
    }


    @Override
    protected void initListener() {
        tabTimeline.addOnTabSelectedListener(this);
        adapter.setOnClickListener(new BaseAdapter.OnclickListener<Comic>() {
            @Override
            public void onClick(View v, Comic comic) {
                String pageUrl = Constant.BASE_URL.substring(0, Constant.BASE_URL.length() - 1) + comic.getUrl();
                Intent intent = new Intent(mActivity, ComicActivity.class);
                intent.putExtra(Constant.PAGE_URL, pageUrl);
                startActivity(intent);
            }
        });
    }

    @Override
    protected TimeLinePresenter createPresenter() {
        return new TimeLinePresenter();
    }

    @Override
    public void onResult(List<List<Comic>> t) {
        result = t;
        adapter.setData(result.get(position));
    }

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        if (result != null && result.size() > 0) {
            adapter.setData(result.get(tab.getPosition()));

        }
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
}
