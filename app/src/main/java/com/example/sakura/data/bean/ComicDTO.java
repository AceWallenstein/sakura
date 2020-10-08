package com.example.sakura.data.bean;

import java.util.List;

public class ComicDTO {
    List<Comic> bannerList;
    List<CategoryBean> beans;
    public void setBannerList(List<Comic> bannerList) {
        this.bannerList = bannerList;
    }

    public void setBeans(List<CategoryBean> beans) {
        this.beans = beans;
    }

    public List<CategoryBean> getBeans() {
        return beans;
    }

    public List<Comic> getBannerList() {
        return bannerList;
    }
}
