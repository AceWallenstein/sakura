package com.example.sakura.presenter;

import com.example.sakura.base.BaseObserver;
import com.example.sakura.base.BasePresenter;
import com.example.sakura.contact.SearchContract;
import com.example.sakura.data.bean.ComicDetail;
import com.example.sakura.server.SearchServer;
import com.example.sakura.ui.activity.SearchActivity;
import com.example.sakura.utils.LoggerUtils;

import java.util.List;

public class SearchPresenter extends BasePresenter<SearchActivity> implements SearchContract.P {
    private static final String TAG = "SearchPresenter";

    public void search(String searchword) {

        new SearchServer().search(searchword, new BaseObserver<List<ComicDetail>>() {
            @Override
            public void onNext(List<ComicDetail> comicDetails) {
                getView().onResult(comicDetails);
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
                LoggerUtils.e(TAG, "onError: " + e);

            }
        });

    }

}
