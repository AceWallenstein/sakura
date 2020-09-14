package com.example.sakura.presenter;

import android.util.Log;

import com.example.sakura.base.BaseObserver;
import com.example.sakura.base.BasePresenter;
import com.example.sakura.bean.ComicDetail;
import com.example.sakura.contact.ComicContract;
import com.example.sakura.server.ComicServer;
import com.example.sakura.ui.activity.ComicActivity;



public class ComicPresenter extends BasePresenter<ComicActivity> implements ComicContract.P {
    private static final String TAG = "ComicPresenter";

    public void getComicDetail(String pageUrl) {
//        Log.d(TAG, "getComicDetail: "+pageUrl);
        new ComicServer().getComicDetail(pageUrl, new BaseObserver<ComicDetail>() {
            @Override
            public void onNext(ComicDetail comicDetail) {
                Log.d(TAG, "onNext: " + comicDetail.toString());
                if (getView() != null) {
                    getView().onResult(comicDetail);
                }
            }

            @Override
            public void onError(Throwable e) {
                if (getView() != null) {
                    getView().onError(e);
                }
                Log.d(TAG, "onError: " + e);
            }
        });

    }
}
