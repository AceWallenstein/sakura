package com.example.sakura.presenter;

import android.util.Log;

import com.example.sakura.base.BaseExcept;
import com.example.sakura.base.BaseObserver;
import com.example.sakura.base.BasePresenter;
import com.example.sakura.contact.PlayContract;
import com.example.sakura.resp.ComicInfoResp;
import com.example.sakura.server.ComicServer;
import com.example.sakura.ui.activity.PlayActivity;

public class PlayPresenter extends BasePresenter<PlayActivity> implements PlayContract.P {
    private static final String TAG = "PlayPresenter";

    public void getPlayInfo(String url,String num) {
        new ComicServer().getPlayInfo(url,num, new BaseObserver<ComicInfoResp>() {
            @Override
            public void onNext(ComicInfoResp t) {
                super.onNext(t);
                Log.d(TAG, "onNext: " + t);
                if (getView() != null) {
                    getView().onResult(t);
                }
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                if (e instanceof BaseExcept)
                    if (getView() != null) {
                        getView().onError(e);
                    }
            }
        });


    }

}
