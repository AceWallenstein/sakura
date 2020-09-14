package com.example.sakura.contact;

import com.example.sakura.bean.CategoryBean;
import com.example.sakura.bean.ComicDetail;

import java.util.List;

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
