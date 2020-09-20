package com.example.sakura.server;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Environment;
import android.os.IBinder;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.sakura.common.Constant;
import com.example.sakura.data.bean.ComicInfo;
import com.example.sakura.utils.DownloadManager;
import com.example.sakura.utils.DownLoadFile;
import com.example.sakura.utils.LoggerUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DownloadServer extends Service {
    private static final String TAG = "DownloadServer";
    private DownloadManager downloadManager;
    private Map<Integer, DownLoadFile> map;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new MyBind();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent.getAction() == "download") {
            String basePath;
            if(Environment.getExternalStorageState()!=Environment.MEDIA_MOUNTED) {
//                Toast.makeText(this,"没有sd卡，无法使用下载功能！",Toast.LENGTH_SHORT).show();
//                return uper.onStartCommand(intent, flags, startId);}
                basePath = this.getFilesDir().getAbsolutePath();
            }else{
                basePath = Constant.APP_PATH;
            }
            String comicName = intent.getStringExtra("comicName");
            ArrayList<ComicInfo> comicInfos = intent.getParcelableArrayListExtra("downloadData");
            String comicPath = basePath+ comicName;
            map = new HashMap<>();
            for (ComicInfo comicInfo :
                    comicInfos) {
                int i = comicInfo.getNumUrl().hashCode();
                DownLoadFile loadFile = new DownLoadFile(this, comicInfo.getNumUrl(), comicInfo.getNum() + comicPath);
                map.put(i, loadFile);
            }
            download();
            Toast.makeText(this,"开始下载",Toast.LENGTH_SHORT).show();
        }
        if (intent.getAction() == "pause") {
            ComicInfo comicInfo = intent.getParcelableExtra("comicInfo");
            if (comicInfo != null) {
                DownLoadFile downLoadFile = map.get(comicInfo.getNumUrl().hashCode());
                if (downLoadFile != null) {
                    downLoadFile.onPause();
                }
            }
        }
        if (intent.getAction() == "restart") {
            ComicInfo comicInfo = intent.getParcelableExtra("comicInfo");
            if (comicInfo != null) {
                DownLoadFile downLoadFile = map.get(comicInfo.getNumUrl().hashCode());
                if (downLoadFile != null) {
                    downLoadFile.onStart();
                }
            }
        }
        if (intent.getAction() == "cancel") {
            ComicInfo comicInfo = intent.getParcelableExtra("comicInfo");
            if (comicInfo != null) {
                DownLoadFile downLoadFile = map.get(comicInfo.getNumUrl().hashCode());
                if (downLoadFile != null) {
                    downLoadFile.cancel();
                }
            }
        }

        return super.onStartCommand(intent, flags, startId);

    }

    private void download() {
        if (map != null) {
            for (DownLoadFile downLoadFile :
                    map.values()) {
                downLoadFile.downLoad();
                downLoadFile.setOnDownLoadListener(new DownLoadFile.DownLoadListener() {
                    @Override
                    public void getProgress(int progress) {
                        LoggerUtils.d(TAG, "getProgress: ");
                    }

                    @Override
                    public void onComplete() {

                    }

                    @Override
                    public void onFailure() {

                    }
                });
            }

        }
    }

    public void setListener(ComicInfo comicInfo, DownLoadFile.DownLoadListener listener) {
        if (comicInfo != null) {
            DownLoadFile downLoadFile = map.get(comicInfo.getNumUrl().hashCode());
            if (downLoadFile != null) {
                downLoadFile.setOnDownLoadListener(listener);
            }
        }
    }
    public   List<DownLoadFile> getData(){
        if (map != null) {
            return (List<DownLoadFile>) map.values();
        }
        return null;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);

    }

    public class MyBind extends Binder {
       public DownloadServer getDownloadServer(){
            return DownloadServer.this;
        }
    }
}
