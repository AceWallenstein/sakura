package com.example.sakura.base;

public abstract class BasePresenter<T> {


    private T mView;

    public void attachView(T view) {
        this.mView = view;
    }

    public boolean isAttached(){
        return this.mView != null;
    }
    public T getView(){
        return mView;
    }

    public void detachView(T activity) {
        if (mView != null) {
            mView = null;
        }

    }
}
