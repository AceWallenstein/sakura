package com.example.sakura.bean;

import androidx.annotation.NonNull;

import java.util.List;
//动漫详情页
public class ComicDetail {
    private String title;
    private String imgUrl;
    private String desc;
    private List<ComicDir> dirs;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public List<ComicDir> getDirs() {
        return dirs;
    }

    public void setDirs(List<ComicDir> dirs) {
        this.dirs = dirs;
    }

    public ComicDetail(String title, String imgUrl, String desc) {
        this.title = title;
        this.imgUrl = imgUrl;
        this.desc = desc;
    }

    @NonNull
    @Override
    public String toString() {
        return "title"+ title+"**"+
                "imgUrl"+ imgUrl+"**"+
                "desc"+ desc+getDirs().toString();


    }
}
