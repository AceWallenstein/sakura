package com.example.sakura.ui.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sakura.R;
import com.example.sakura.adapter.SearchAdapter;
import com.example.sakura.base.BaseAdapter;
import com.example.sakura.base.BaseMvpActivity;
import com.example.sakura.common.Constant;
import com.example.sakura.contact.SearchContract;
import com.example.sakura.data.bean.ComicDetail;
import com.example.sakura.presenter.SearchPresenter;
import com.example.sakura.widget.SearchView;

import java.util.List;

import scut.carson_ho.searchview.ICallBack;
import scut.carson_ho.searchview.bCallBack;

public class SearchActivity extends BaseMvpActivity<SearchPresenter> implements SearchContract.V {

    private SearchView searchView;
    private RecyclerView rvResult;
    private SearchAdapter adapter;
    private TextView tv_error;

    @Override
    protected int layoutId() {
        return R.layout.activity_search;
    }

    @Override
    protected void initView() {
        tv_error = findViewById(R.id.tv_error);
        searchView = findViewById(R.id.search);
        rvResult = findViewById(R.id.rv_result);
        adapter = new SearchAdapter(this);
        rvResult.setAdapter(adapter);
        rvResult.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
    }

    @Override
    protected void initListener() {
        super.initListener();
        searchView.setOnClickSearch(new ICallBack() {
            @Override
            public void SearchAciton(String string) {
//                if (TextUtils.isEmpty(string))
//                    return ;
                load(string);
            }
        });

        // 5. 设置点击返回按键后的操作（通过回调接口）
        searchView.setOnClickBack(new bCallBack() {
            @Override
            public void BackAciton() {
                finish();
            }
        });
//        mSearch.setOnKeyListener((v, keyCode, event) -> {
//            // 这两个条件必须同时成立，如果仅仅用了enter判断，就会执行两次
//            if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
//                // 执行发送消息等操作
//                if (TextUtils.isEmpty(mSearch.getText().toString()))
//                    return false;
//                load(mSearch.getText().toString());
//                return true;
//            }
//            return false;
//        });


    }

    private void load(String searchword) {
        mPresenter.search(searchword);
    }

//    @Override
//    protected void loadData() {
//        super.loadData();
//        if (TextUtils.isEmpty(getIntent().getStringExtra(Constant.SEARCH_WORD)))
//            return;
//        load(getIntent().getStringExtra(Constant.SEARCH_WORD));
//    }

    @Override
    protected SearchPresenter createPresenter() {
        return new SearchPresenter();
    }

    @Override
    public void onResult(List<ComicDetail> comicDetails) {
        if (comicDetails != null&&comicDetails.size()>0) {
            tv_error.setVisibility(View.GONE);
            adapter.setData(comicDetails);
        }else {
            adapter.clear();
            tv_error.setVisibility(View.VISIBLE);
        }
        adapter.setOnClickListener(new BaseAdapter.OnclickListener<ComicDetail>() {
            @Override
            public void onClick(View v, ComicDetail comicDetails) {
                Intent intent = new Intent(SearchActivity.this, ComicActivity.class);
                String pageUrl = Constant.BASE_URL.substring(0, Constant.BASE_URL.length() - 1) + comicDetails.getHref();
                intent.putExtra(Constant.PAGE_URL, pageUrl);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onError(Throwable e) {
        adapter.clear();
        tv_error.setVisibility(View.VISIBLE);
    }
}
