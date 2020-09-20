package com.example.sakura.utils;

import android.content.Context;
import android.os.Environment;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

public class DownloadManager {
    String path;
    private Context mContext;
    private RandomAccessFile out;
    private ProgressListener listener;
    private Map<String,DownloadThread> threadMap;

    public DownloadManager(Context context,ProgressListener listener)
    {
        this.mContext = context;
        this.listener = listener;
    }
    public void startDownload(String threadName,String fileName, String url ) {
        if(Environment.getExternalStorageState()!=Environment.MEDIA_MOUNTED){
            Toast.makeText(mContext,"没有sd卡，无法使用下载功能！",Toast.LENGTH_SHORT).show();
            return;
        }else {
            String path = Environment.getExternalStorageDirectory() + "SAKURA";
            DownloadThread thread = new DownloadThread(threadName,path,fileName,url);
            thread.start();
            threadMap.put(threadName,thread);
        }
    }
    public void pauseDownload(String threadName){
        threadMap.get(threadName).stop();
    }

    class DownloadThread extends Thread {
        String path;
        String fileName;
        String url;

        public DownloadThread(String name,String path, String fileName, String url) {
            super(name);
            this.path = path;
            this.fileName = fileName;
            this.url = url;
        }

        @Override
        public void run() {
            super.run();
            download(path,fileName,url);
        }

        private void download(String path, String fileName, String url) {
            File file = new File(path, "\\" + fileName);
            try {
                if (!file.exists()) {
                    file.getParentFile().mkdirs();
                    file.createNewFile();
                    System.out.println("创建了没");
                }

                long size = file.length();
                HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
                connection.setRequestProperty("range", "bytes=" + size + "-");
                if (connection.getResponseCode() == 206) {
                    int contentLength = connection.getContentLength();
                    System.out.println(contentLength);
                    InputStream in = connection.getInputStream();
                    out = new RandomAccessFile(file, "rw");
                    out.seek(size);
                    byte[] buf = new byte[4 * 1024];
                    long sum = size;
                    int len = -1;
                    while ((len = in.read(buf)) != -1) {
                        sum += len;
                        System.out.println("*******" + sum);
                        listener.progressChanged(sum,size,len);
                        out.write(buf, 0, len);
                    }
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();

            }
        }

    }
    interface ProgressListener{
        /**
         *
         * @param sum  下载的进度
         * @param size  总文件大小
         * @param len   是否下载完毕 -1为下载完毕
         */
        void progressChanged(long sum, long size, int len);
    }


}

