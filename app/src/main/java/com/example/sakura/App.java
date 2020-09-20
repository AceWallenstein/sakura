package com.example.sakura;

import android.app.Application;
import android.content.Intent;

import com.example.sakura.server.DownloadServer;
import com.example.sakura.utils.SharedPreferencesUtil;
import com.example.sakura.utils.retrofit.EncodingInterceptor;
import com.ixuea.android.downloader.domain.DownloadInfo;

import java.util.List;

import io.reactivex.plugins.RxJavaPlugins;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.sakura.common.Constant.BASE_URL;
import static io.reactivex.internal.functions.Functions.emptyConsumer;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        RxJavaPlugins.setErrorHandler(emptyConsumer());
        SharedPreferencesUtil.getInstance(this,getString(R.string.app_name));
//        startService(new Intent(this, DownloadServer.class));
    }


}
