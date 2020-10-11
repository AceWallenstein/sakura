package com.example.sakura;

import android.app.Application;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;

import com.example.sakura.data.greendao.DaoMaster;
import com.example.sakura.data.greendao.DaoSession;
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
    private static DaoSession daoSession;

    @Override
    public void onCreate() {
        super.onCreate();
        RxJavaPlugins.setErrorHandler(emptyConsumer());
        SharedPreferencesUtil.getInstance(this,getString(R.string.app_name));
//        startService(new Intent(this, DownloadServer.class));
        setupDatabase();
    }

    private void setupDatabase() {
        //创建数据库study.db"
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "sakura.db", null);
        //获取可写数据库
        SQLiteDatabase db = helper.getWritableDatabase();
        //获取数据库对象
        DaoMaster daoMaster = new DaoMaster(db);
        //获取Dao对象管理者
        daoSession = daoMaster.newSession();
    }

    public static DaoSession getDaoInstant() {
        return daoSession;
    }


}
