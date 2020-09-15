package com.example.sakura.server;

import android.annotation.SuppressLint;

import com.example.sakura.common.Constant;
import com.example.sakura.data.bean.ComicDetail;
import com.example.sakura.net.SearchInfo;
import com.example.sakura.utils.LoggerUtils;
import com.example.sakura.utils.retrofit.HeaderInterceptor;
import com.example.sakura.utils.retrofit.RetrofitUtil;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

public class SearchServer {
    private static final String TAG = "SearchServer";

    private Document doc;
    private Connection conn;

    @SuppressLint("CheckResult")
    public void search(String searchword, Observer< List<ComicDetail>> observer) {
        try {
            searchword = URLEncoder.encode(searchword, "GBK");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        RetrofitUtil.bindWithInterceptor(Constant.BASE_URL, new HeaderInterceptor()).create(SearchInfo.class).search(searchword)
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(new Function<ResponseBody, Observable<List<ComicDetail>>>() {
                    @Override
                    public Observable<List<ComicDetail>> apply(ResponseBody body) throws Exception {
                        Document searchDoc = Jsoup.parse(body.string());
                        Element picsEle = searchDoc.select("div.pics").get(0);
                        List<ComicDetail> searchList = new ArrayList<>();
                        for (Element li : picsEle.child(0).children()) {
                            String title = li.child(1).child(0).attr("title");
                            LoggerUtils.d(TAG, "apply: "+title);
                            String imgSrc = li.child(0).child(0).attr("src");
                            String info = li.child(4).text();
                            String href = li.child(0).attr("href");
                            ComicDetail comicDetail = new ComicDetail(title, imgSrc, info);
                            comicDetail.setHref(href);
                            searchList.add(comicDetail);
                        }
                        return Observable.just(searchList);
                    }
                })
                .subscribeOn(Schedulers.io()).subscribe(observer);


    }

    private Document getDoc(String url) throws IOException {
        conn = Jsoup.connect(url).header("Accept", " text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3")
                .header("Accept-Language", "zh-CN,zh;q=0.9")
                .header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/78.0.3904.108 Safari/537.36");
        return conn.get();
    }


}
