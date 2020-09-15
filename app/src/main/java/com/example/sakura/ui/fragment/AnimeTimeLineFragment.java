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

import java.util.List;

public class AnimeTimeLineFragment extends BaseMvpFragment<TimeLinePresenter> implements TimelineContract.V, TabLayout.OnTabSelectedListener {

    private TabLayout tabTimeline;
    private List<List<Comic>> result;
    private RecyclerView rvComicContent;
    private TimelineAdapter adapter;

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
        adapter.setData(result.get(0));
    }

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        if (result != null && result.size() > 0) {
            switch (tab.getPosition()) {
                case 0:
                    adapter.setData(result.get(0));
                    break;
                case 1:
                    adapter.setData(result.get(1));
                    break;
                case 2:
                    adapter.setData(result.get(2));
                    break;
                case 3:
                    adapter.setData(result.get(3));
                    break;
                case 4:
                    adapter.setData(result.get(4));
                    break;
                case 5:
                    adapter.setData(result.get(5));
                    break;
                case 6:
                    adapter.setData(result.get(6));
                    break;
                case 7:
                    adapter.setData(result.get(7));
                    break;
            }
        }
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
}
