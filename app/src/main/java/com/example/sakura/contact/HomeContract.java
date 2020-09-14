package com.example.sakura.contact;

import com.example.sakura.data.bean.CategoryBean;

import java.util.List;

public interface HomeContract {
    interface V{
//        void showProgressBar()
        void onResult(List<CategoryBean> beans);
        void onError(Throwable e);

    }
    interface P{
        void getComic();
    }
}
