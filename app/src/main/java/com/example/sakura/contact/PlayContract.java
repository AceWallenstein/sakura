package com.example.sakura.contact;

import com.example.sakura.bean.CategoryBean;
import com.example.sakura.bean.ComicInfo;
import com.example.sakura.resp.ComicInfoResp;

import java.util.List;

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
