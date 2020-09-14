package com.example.sakura.data.bean;

import androidx.annotation.NonNull;

import java.util.List;
//栏目
public class CategoryBean {
    private String name;
    private String moreUrl;
    private List<Comic> comics;

    public CategoryBean(String name, String moreUrl) {
        this.name = name;
        this.moreUrl = moreUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMoreUrl() {
        return moreUrl;
    }

    public void setMoreUrl(String moreUrl) {
        this.moreUrl = moreUrl;
    }

    public List<Comic> getComics() {
        return comics;
    }

    public void setComics(List<Comic> comics) {
        this.comics = comics;
    }

    @NonNull
    @Override
    public String toString() {
        return "name"+ name+"#"+comics.toString();
    }
}
