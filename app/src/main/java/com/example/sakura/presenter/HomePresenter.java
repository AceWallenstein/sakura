package com.example.sakura.presenter;

import com.example.sakura.base.BaseObserver;
import com.example.sakura.base.BasePresenter;
import com.example.sakura.data.bean.CategoryBean;
import com.example.sakura.contact.HomeContract;
import com.example.sakura.data.bean.ComicDTO;
import com.example.sakura.server.ComicServer;
import com.example.sakura.ui.fragment.HomeFragment;

import java.util.List;

public class HomePresenter extends BasePresenter<HomeFragment> implements HomeContract.P {
    public void getComic() {
        new ComicServer().getComic(new BaseObserver<ComicDTO>() {
            @Override
            public void onNext(ComicDTO beans) {
                super.onNext(beans);
                if (getView() != null) {
                    getView().onResult(beans);
                }
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                if (getView() != null) {
                    getView().onError(e);
                }
            }
        });


    }

}
