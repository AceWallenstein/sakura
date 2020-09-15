package com.example.sakura.contact;

import com.example.sakura.data.bean.Comic;

import java.util.List;

public interface TimelineContract {
    interface V{
//        void showProgressBar()
        void onResult(List<List<Comic>> t);
        void onError(Throwable e);

    }
    interface P{
         void getPlayInfo(String url, String num);
    }
}
