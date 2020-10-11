package com.example.sakura.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sakura.App;
import com.example.sakura.R;
import com.example.sakura.adapter.ComicCollectedAdapter;
import com.example.sakura.base.BaseAdapter;
import com.example.sakura.base.BaseFragment;
import com.example.sakura.common.Constant;
import com.example.sakura.data.bean.ComicDetail;
import com.example.sakura.data.greendao.ComicDetailDao;
import com.example.sakura.ui.activity.ComicActivity;

import org.greenrobot.greendao.query.Query;

import java.util.List;

public class MineFragment extends BaseFragment {

    private TextView mTitle;
    private RecyclerView rvCollectedList;
    private ComicCollectedAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_mine, container, false);
        initView(v);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initListener();

    }


    @Override
    public void onResume() {
        super.onResume();
        refreshData();
    }

    private void refreshData() {
        ComicDetailDao comicDetailDao = App.getDaoInstant().getComicDetailDao();
        List<ComicDetail> comicDetails = comicDetailDao.loadAll();
        adapter.setData(comicDetails);
    }

    private void initView(View v) {
        mTitle = v.findViewById(R.id.title);
        rvCollectedList = v.findViewById(R.id.rv_collect_list);
        rvCollectedList.setLayoutManager(new GridLayoutManager(getContext(), 3));
        adapter = new ComicCollectedAdapter(getContext());
        rvCollectedList.setAdapter(adapter);
    }

    private void initListener() {
        adapter.setOnClickListener(new BaseAdapter.OnclickListener<ComicDetail>() {

            @Override
            public void onClick(View v, ComicDetail comicDetail) {
                String pageUrl = comicDetail.getHref();
                Intent intent = new Intent(mActivity, ComicActivity.class);
                intent.putExtra(Constant.PAGE_URL, pageUrl);
                startActivity(intent);
            }
        });
        adapter.setOnLongClickListener(new BaseAdapter.OnLongClickListener<ComicDetail>() {

            @Override
            public void onLongClick(View v, ComicDetail comicDetail) {
                // TODO: 2020/10/11 显示删除按钮，动态修改数据。
            }
        });

    }

}
