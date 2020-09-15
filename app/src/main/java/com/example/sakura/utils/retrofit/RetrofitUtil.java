package com.example.sakura.utils.retrofit;

import com.example.sakura.BuildConfig;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitUtil {
    public static Retrofit bind(String BASE_URL) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(getClient())
                .build();

        return retrofit;
    }

    public static Retrofit bind(String BASE_URL, Gson gson) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(getClient())
                .build();

        return retrofit;
    }

    public static Retrofit bindWithInterceptor(String BASE_URL, Interceptor interceptor) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(getClientWithHeader()
                        //编码拦截器。
                        .addInterceptor(new EncodingInterceptor("GBK"))
                        .addInterceptor(interceptor).build())
                .build();
        return retrofit;
    }

    private static OkHttpClient.Builder getClientWithHeader() {
        OkHttpClient.Builder builder = new OkHttpClient().newBuilder()
                .connectTimeout(10, TimeUnit.SECONDS)//设置超时时间
                .readTimeout(10, TimeUnit.SECONDS)//设置读取超时时间
                .writeTimeout(10, TimeUnit.SECONDS);
        if(BuildConfig.APP_ISDEBUG){
            builder.addInterceptor(new LoggingInterceptor());
        }
        return builder;
    }

    private static OkHttpClient getClient() {
        OkHttpClient.Builder builder = new OkHttpClient().newBuilder()
                .connectTimeout(10, TimeUnit.SECONDS)//设置超时时间
                .readTimeout(10, TimeUnit.SECONDS)//设置读取超时时间
                .writeTimeout(10, TimeUnit.SECONDS)
                .addInterceptor(new Interceptor() {
                    @NotNull
                    @Override
                    public Response intercept(@NotNull Chain chain) throws IOException {
                        Request request = chain.request();
                        Request newRequest = request.newBuilder().addHeader(
                                "User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/80.0.3987.132 Safari/537.36"
                        ).build();
                        return chain.proceed(newRequest);
                    }
                })
                ;//设置写入超时时间

        if(BuildConfig.APP_ISDEBUG){
            builder.addInterceptor(new LoggingInterceptor());
        }
        OkHttpClient mOkHttpClient = builder.build();
        return mOkHttpClient;
    }

}