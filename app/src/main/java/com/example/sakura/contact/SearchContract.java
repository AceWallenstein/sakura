package com.example.sakura.contact;

import com.example.sakura.data.bean.ComicDetail;

import java.util.List;

public interface SearchContract {
    interface V {
        //        void showProgressBar()
        void onResult(List<ComicDetail> comicDetails);

        void onError(Throwable e);

    }

    interface P {
        void search(String searchword);
    }
}
