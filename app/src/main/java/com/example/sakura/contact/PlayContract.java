package com.example.sakura.contact;

import com.example.sakura.data.resp.ComicInfoResp;

public interface PlayContract {
    interface V{
//        void showProgressBar()
        void onResult(ComicInfoResp t);
        void onError(Throwable e);

    }
    interface P{
         void getPlayInfo(String url,String num);
    }
}
