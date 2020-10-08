package com.example.sakura.contact;

import com.example.sakura.data.bean.CategoryBean;
import com.example.sakura.data.bean.ComicDTO;

import java.util.List;

public interface HomeContract {
    interface V{
//        void showProgressBar()
        void onResult(ComicDTO beans);
        void onError(Throwable e);

    }
    interface P{
        void getComic();
    }
}
