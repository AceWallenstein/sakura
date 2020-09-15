package com.example.sakura.utils.retrofit;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;


public class HeaderInterceptor implements Interceptor {
    @NotNull
    @Override
    public Response intercept(@NotNull Chain chain) throws IOException {
        Request request = chain.request();
        Request build = request.newBuilder().addHeader("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9")
                .addHeader("Accept-Encoding","gzip, deflate")
                .addHeader("Accept-Language","zh-CN,zh;q=0.9")
                .addHeader("Cache-Control","max-age=0")
                .addHeader("Connection","keep-alive")
                .addHeader("Content-Length","35")
                .addHeader("Content-Type","application/x-www-form-urlencoded")
                .addHeader("Cookie","first_h=1593160109666; count_h=1; first_m=1593160109672; count_m=1; __music_index__=1; UM_distinctid=172efbe0d7a395-09561d517e994c-4353760-144000-172efbe0d7b51e; CNZZDATA1260742008=389617554-1593155904-null%7C1593155904; Hm_lvt_38c112aee0c8dc4d8d4127bb172cc197=1592459749,1592485557,1593157823,1593158827; Hm_lpvt_38c112aee0c8dc4d8d4127bb172cc197=1593160110; bdshare_firstime=1593160109768")
                .addHeader("Host","www.imomoe.in")
                .addHeader("Origin","http://www.imomoe.in")
                .addHeader("Referer","http://www.imomoe.in/search.asp")
                .addHeader("Upgrade-Insecure-Requests","1")
                .addHeader("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/80.0.3987.132 Safari/537.36")
                .build();
        return chain.proceed(build);
    }
//"Accept": "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9",
//        "Accept-Encoding": "gzip, deflate",
//        "Accept-Language": "zh-CN,zh;q=0.9",
//        "Cache-Control": "max-age=0",
//        "Connection": "keep-alive",
//        "Content-Length": "35",
//        "Content-Type": "application/x-www-form-urlencoded",
//        "Cookie": "first_h=1593160109666; count_h=1; first_m=1593160109672; count_m=1; __music_index__=1; UM_distinctid=172efbe0d7a395-09561d517e994c-4353760-144000-172efbe0d7b51e; CNZZDATA1260742008=389617554-1593155904-null%7C1593155904; Hm_lvt_38c112aee0c8dc4d8d4127bb172cc197=1592459749,1592485557,1593157823,1593158827; Hm_lpvt_38c112aee0c8dc4d8d4127bb172cc197=1593160110; bdshare_firstime=1593160109768",
//        "Host": "www.imomoe.in",
//        "Origin":"http://www.imomoe.in",
//        "Referer": "http://www.imomoe.in/search.asp",
//        "Upgrade-Insecure-Requests": "1",
//        "User-Agent": "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/80.0.3987.132 Safari/537.36"


}
