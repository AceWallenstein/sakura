package com.example.sakura.server;

import android.util.Log;

import com.example.sakura.base.BaseExcept;
import com.example.sakura.base.BaseObserver;
import com.example.sakura.data.bean.CategoryBean;
import com.example.sakura.data.bean.Comic;
import com.example.sakura.data.bean.ComicDTO;
import com.example.sakura.data.bean.ComicDetail;
import com.example.sakura.data.bean.ComicDir;
import com.example.sakura.data.bean.ComicInfo;
import com.example.sakura.common.Constant;
import com.example.sakura.data.resp.ComicInfoResp;
import com.example.sakura.utils.LoggerUtils;

import org.json.JSONArray;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class ComicServer {
    private static final String TAG = "ComicServer";
    private Document doc;
    private Connection conn;
    private Document pageDoc;

    //当前播放集
    public String currentNumPath;
    private Document playDoc;


    /**
     * 获取动漫基本信息。
     *
     * @param observer
     */
    public void getComic(Observer observer) {
        List<CategoryBean> beans = new ArrayList<>();
        Observable.create((ObservableEmitter<ComicDTO> emitter) -> {
            try {
                if (doc == null) {
                    doc = getDoc(Constant.BASE_URL);
                }
                Element heroWrap = doc.select("div.foucs").get(0).child(0);
                LoggerUtils.d(TAG, "heroWrap"+heroWrap);
                List<Comic> bannerList = new ArrayList<>();
                for (Element ul:
                doc.select("div.foucs").get(0).child(0).children()) {
                    for (Element li:
                    ul.children()) {
                        String title = li.child(0).attr("title");
                        String href = li.child(0).attr("href");
                        String imgSrc = li.child(0).child(0).attr("src");
                        Comic comic = new Comic(title, href, imgSrc);
                        bannerList.add(comic);
                        LoggerUtils.d(TAG, "getComic:biaoti"+title);
                    }
                }
                Elements ele = doc.select("div#contrainer");
                int i = 0;
                Elements imgs = ele.select("div.imgs");
                for (Element tame :
                        ele.select("div.tame")) {
                    String name = tame.child(0).text();
                    String moreUrl = tame.child(1).child(0).attr("href");
                    CategoryBean categoryBean = new CategoryBean(name, moreUrl);
                    Elements imgList = imgs.get(i).child(0).select("li");
                    List<Comic> comics = new ArrayList<>();
                    for (Element img :
                            imgList) {
                        String title = img.child(1).text();
                        String url = img.child(0).attr("href");
                        String imgUrl = img.child(0).child(0).attr("src");
                        String episode = img.child(2).text();
                        Comic comic = new Comic(title, url, imgUrl, episode);
                        Log.d(TAG, "getComic: " + comic.toString());
                        comics.add(comic);
                    }
                    categoryBean.setComics(comics);
                    i++;
                    beans.add(categoryBean);
                }
                ComicDTO comicDTO = new ComicDTO();
                comicDTO.setBeans(beans);
                comicDTO.setBannerList(bannerList);
                emitter.onNext(comicDTO);
            } catch (IOException e) {
                e.printStackTrace();
                emitter.onError(e);
            }
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io()).subscribe(observer);
    }

    /**
     * 目录页面的动漫信息
     *
     * @param pageUrl  目录页面url
     * @param observer
     */
    public void getComicDetail(String pageUrl, BaseObserver<ComicDetail> observer) {
        Observable.create((ObservableOnSubscribe<ComicDetail>) emitter -> {
            try {
                if (pageDoc == null) {
                    pageDoc = getDoc(pageUrl);
                }
                Element tpicele = pageDoc.select("div.tpic").get(0);
                String title = tpicele.child(0).child(0).text();
                Log.d(TAG, "getComicDetail: " + title);
                String imgSrc = tpicele.child(1).attr("src");
                String info = pageDoc.select("div.info").text();
                ComicDetail comicDetail = new ComicDetail(title, imgSrc, info);
                //目录
                Element dirEle = pageDoc.select("div#play_0").get(0);
                List<ComicDir> dirs = new ArrayList<>();
                for (Element a :
                        dirEle.select("a")) {
                    ComicDir comicDir = new ComicDir();
                    Log.d(TAG, "getComicDetail: " + a.text());
                    comicDir.setNum(a.text());
                    comicDir.setHref(a.attr("href"));
                    dirs.add(comicDir);
                }
                comicDetail.setDirs(dirs);
                emitter.onNext(comicDetail);
            } catch (IOException e) {
                e.printStackTrace();
                emitter.onError(e);
            }

        }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io()).subscribe(observer);
    }

    private Document getDoc(String url) throws IOException {
        conn = Jsoup.connect(url).header("Accept", " text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3")
                .header("Accept-Language", "zh-CN,zh;q=0.9")
                .header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/78.0.3904.108 Safari/537.36");
        return conn.get();
    }

    /**
     * 获取播放页面信息
     *
     * @param playUrl
     */
    public void getPlayInfo(String playUrl, String num, Observer<ComicInfoResp> observer) {
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) {
                try {
                    if (pageDoc == null) {
                        playDoc = getDoc(playUrl);
                    }
                    Elements playerEle = playDoc.select("div.player");
//                    Log.d(TAG, "subscribe: " + playerEle);
                    //<script type="text/javascript" src="/playdata/79/1615.js?42555.57"></script>
                    String pattern = "<script type=\"text/javascript\" src=\"(.+)?\"></script>";
                    Pattern p = Pattern.compile(pattern);
                    Matcher m = p.matcher(playerEle.toString());
                    if (m.find()) {
                        String result = m.group(1);
                        result = result.substring(1);
                        emitter.onNext(result);
                    } else {
                        emitter.onError(new BaseExcept(-1, "链接未获取成功"));
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    emitter.onError(e);
                }
            }
        }).flatMap(new Function<String, Observable<ComicInfoResp>>() {
                       private HttpURLConnection httpConn;
                       private BufferedReader reader;
                       private InputStreamReader inputStreamReader;
                       private InputStream inputStream;

                       @Override
                       public Observable<ComicInfoResp> apply(String s) {
//                           Log.d(TAG, "SSSSS: "+s);
                           try {
                               URL url = new URL(Constant.BASE_URL + s);
                               httpConn = (HttpURLConnection) url.openConnection();
                               if (httpConn.getResponseCode() == Constant.OK) {
                                   inputStream = httpConn.getInputStream();
                                   inputStreamReader = new InputStreamReader(inputStream, "GBK");
                                   reader = new BufferedReader(inputStreamReader);
                                   String tempStr;
                                   StringBuilder sb = new StringBuilder();
                                   while ((tempStr = reader.readLine()) != null) {
                                       sb.append(tempStr);
                                   }
//                                   src2=text[18::].split(',urlinfo')[0]
                                   String result = sb.toString();
                                   result = result.substring(18).split(",urlinfo")[0];
                                   JSONArray jsonArray = new JSONArray(result);
                                   JSONArray array = (JSONArray) jsonArray.get(0);
                                   array.remove(0);
                                   JSONArray jsonList = (JSONArray) array.get(0);
                                   List<ComicInfo> comicInfos = new ArrayList<>();
                                   for (int i = 0; i < jsonList.length(); i++) {
                                       String o = (String) jsonList.get(i);
                                       String[] split = o.split("\\$");
                                       ComicInfo info = new ComicInfo();
                                       info.setNum(split[0]);
                                       if (num.equals(split[0]))
                                           currentNumPath = split[1];
                                       info.setNumUrl(split[1]);
                                       comicInfos.add(info);
                                   }
                                   ComicInfoResp resp = new ComicInfoResp(currentNumPath, comicInfos);
                                   return Observable.just(resp);
                               }
                           } catch (IOException e) {
                               e.printStackTrace();
                               Observable.error(e);
                           } catch (Exception e) {
                               e.printStackTrace();

                           } finally {
                               if (httpConn != null)
                                   httpConn.disconnect();
                           }
                           return Observable.error(new BaseExcept(-1, "未获取连接"));
                       }
                   }
        ).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io()).subscribe(observer);


    }

    public void getTimeLineInfo(Observer<List<List<Comic>>> observer) {
        Observable.create(new ObservableOnSubscribe<List<List<Comic>>>() {
            @Override
            public void subscribe(ObservableEmitter<List<List<Comic>>> emitter) throws Exception {
                try {
                    if (doc == null) {
                        doc = getDoc(Constant.BASE_URL);
                    }
                    Element listEle = doc.select("div.tists").get(0);
                    List<List<Comic>> comicUlist = new ArrayList<>();
                    for (int i = 0; i < listEle.children().size(); i++) {
                        //每周anime列表
                        Element ulEle = listEle.children().get(i);
                        List<Comic> comicList = new ArrayList<>();
                        for (int j = 0; j < ulEle.children().size(); j++) {
                            Element liEle = ulEle.children().get(j);
                            String episode = liEle.child(0).child(0).text();
                            String title = liEle.child(1).text();
                            String url = liEle.child(1).attr("href");
                            Comic comic = new Comic(title, url, episode);
                            comicList.add(comic);
                        }
                        comicUlist.add(comicList);
                        emitter.onNext(comicUlist);
                    }
                }catch(IOException e)
                {
                    e.printStackTrace();
                    emitter.onError(e);
                }
            }
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io()).subscribe(observer);;


    }
}
