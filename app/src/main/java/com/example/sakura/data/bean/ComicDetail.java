package com.example.sakura.data.bean;

import androidx.annotation.NonNull;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Transient;

import java.util.List;
import org.greenrobot.greendao.annotation.Generated;

//动漫详情页
@Entity
public class ComicDetail {
    @Id
    private String title;
    private String imgUrl;
    private String desc;
    //详情页面url

    private String href;
    @Transient
    private List<ComicDir> dirs;

    public String getHref() {
        return href;
    }

    public void
    setHref(String href) {
        this.href = href;
    }


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

    @Generated(hash = 7345692)
    public ComicDetail(String title, String imgUrl, String desc, String href) {
        this.title = title;
        this.imgUrl = imgUrl;
        this.desc = desc;
        this.href = href;
    }

    @Generated(hash = 391619515)
    public ComicDetail() {
    }

    @NonNull
    @Override
    public String toString() {
        return "href" + href + "**" +
                "title" + title + "**" +
                "imgUrl" + imgUrl + "**" +
                "desc" + desc + getDirs().toString();


    }
}
