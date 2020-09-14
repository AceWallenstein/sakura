package com.example.sakura.contact;

import com.example.sakura.data.bean.ComicDetail;

public interface ComicContract {
    interface V{
//        void showProgressBar()
        void onResult(ComicDetail comicDetail);
        void onError(Throwable e);

    }
    interface P{
        void getComicDetail(String pageUrl);
    }
}
