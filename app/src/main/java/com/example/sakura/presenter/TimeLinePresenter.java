package com.example.sakura.presenter;

import com.example.sakura.base.BaseExcept;
import com.example.sakura.base.BaseObserver;
import com.example.sakura.base.BasePresenter;
import com.example.sakura.data.bean.Comic;
import com.example.sakura.server.ComicServer;
import com.example.sakura.ui.fragment.AnimeTimeLineFragment;
import com.example.sakura.utils.LoggerUtils;

import java.util.List;

public class TimeLinePresenter extends BasePresenter<AnimeTimeLineFragment> {


    public void getTimeLineInfo() {
        new ComicServer().getTimeLineInfo( new BaseObserver<List<List<Comic>>>() {
            @Override
            public void onNext(List<List<Comic>> t) {
                super.onNext(t);
                LoggerUtils.d(TAG, "onNext: "+t);
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
