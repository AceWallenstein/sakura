package com.example.sakura.data.bean;

import androidx.annotation.NonNull;
//栏目下动漫信息
public class Comic {
    private String title;
    private String url;
    private String imgUrl;
    //最新动漫级数
    private String episode;

    public Comic(String title, String url, String imgUrl) {
        this.title = title;
        this.url = url;
        this.imgUrl = imgUrl;
    }

    public Comic(String title, String url, String imgUrl, String episode) {
        this.title = title;
        this.url = url;
        this.imgUrl = imgUrl;
        this.episode = episode;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getEpisode() {
        return episode;
    }

    public void setEpisode(String episode) {
        this.episode = episode;
    }

    @NonNull
    @Override
    public String toString() {
        return "title" + title + "#" +
                "url" + url + "#" +
                "imgUrl" + imgUrl + "#" +
                "episode" + episode;

    }
}
