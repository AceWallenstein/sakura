package com.example.sakura.data.resp;

import com.example.sakura.data.bean.ComicInfo;

import java.util.List;

public class ComicInfoResp {
    //当前集
    String currentNumPath;

    List<ComicInfo> comicInfoList;

    public String getCurrentNumPath() {
        return currentNumPath;
    }

    public void setCurrentNumPath(String currentNumPath) {
        this.currentNumPath = currentNumPath;
    }

    public List<ComicInfo> getComicInfoList() {
        return comicInfoList;
    }

    public void setComicInfoList(List<ComicInfo> comicInfoList) {
        this.comicInfoList = comicInfoList;
    }

    public ComicInfoResp(String currentNumPath, List<ComicInfo> comicInfoList) {
        this.currentNumPath = currentNumPath;
        this.comicInfoList = comicInfoList;
    }
}
