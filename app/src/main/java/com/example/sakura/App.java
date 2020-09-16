package com.example.sakura;

import android.app.Application;

import io.reactivex.plugins.RxJavaPlugins;

import static io.reactivex.internal.functions.Functions.emptyConsumer;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        RxJavaPlugins.setErrorHandler(emptyConsumer());

    }
}
