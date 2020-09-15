package com.example.sakura.ui.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;

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
import com.example.sakura.utils.LoggerUtils;

import java.util.List;

public class SearchActivity extends BaseMvpActivity<SearchPresenter> implements SearchContract.V {

    private EditText mSearch;
    private RecyclerView rvResult;
    private SearchAdapter adapter;

    @Override
    protected int layoutId() {
        return R.layout.activity_search;
    }

    @Override
    protected void initView() {
        mSearch = findViewById(R.id.search);
        mSearch.setText(getIntent().getStringExtra(Constant.SEARCH_WORD));
        rvResult = findViewById(R.id.rv_result);
        adapter = new SearchAdapter(this);
        rvResult.setAdapter(adapter);
        rvResult.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
    }

    @Override
    protected void initListener() {
        super.initListener();
        mSearch.setOnKeyListener((v, keyCode, event) -> {
            // 这两个条件必须同时成立，如果仅仅用了enter判断，就会执行两次
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
                // 执行发送消息等操作
                if (TextUtils.isEmpty(mSearch.getText().toString()))
                    return false;
                load(mSearch.getText().toString());
                return true;
            }
            return false;
        });


    }

    private void load(String searchword) {
        mPresenter.search(searchword);
    }

    @Override
    protected void loadData() {
        super.loadData();
        if (TextUtils.isEmpty(getIntent().getStringExtra(Constant.SEARCH_WORD)))
            return;
        load(getIntent().getStringExtra(Constant.SEARCH_WORD));
    }

    @Override
    protected SearchPresenter createPresenter() {
        return new SearchPresenter();
    }

    @Override
    public void onResult(List<ComicDetail> comicDetails) {
        if (comicDetails != null) {

            adapter.setData(comicDetails);
        }
        adapter.setOnClickListener(new BaseAdapter.OnclickListener<ComicDetail>() {
            @Override
            public void onClick(View v, ComicDetail comicDetails) {
                Intent intent = new Intent(SearchActivity.this, ComicActivity.class);
                String pageUrl = Constant.BASE_URL.substring(0,Constant.BASE_URL.length() - 1)+comicDetails.getHref();
                intent.putExtra(Constant.PAGE_URL,pageUrl);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onError(Throwable e) {

    }
}
