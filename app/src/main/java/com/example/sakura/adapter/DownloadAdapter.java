package com.example.sakura.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sakura.R;
import com.example.sakura.server.DownloadServer;
import com.example.sakura.utils.DownLoadFile;
import com.ixuea.android.downloader.domain.DownloadInfo;

import java.util.ArrayList;
import java.util.List;

public class DownloadAdapter extends RecyclerView.Adapter<DownloadAdapter.VH> {
    private Context mContext;
    private List<DownLoadFile> mDownloadList = new ArrayList<>();
    private DownLoadFile downLoadFile ;
    private int status = DownloadInfo.STATUS_NONE;

    public DownloadAdapter(Context context) {
        mContext = context;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    private void startDownload() {
        Intent intent = new Intent(mContext, DownloadServer.class);
        intent.setAction("restart");
        mContext.startService(intent);
    }

    private void pauseDownload() {
        Intent intent = new Intent(mContext, DownloadServer.class);
        intent.setAction("pause");
        mContext.startService(intent);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        downLoadFile = mDownloadList.get(position);
        holder.progressBar.setMax(downLoadFile.getFileLength());

        downLoadFile.setOnDownLoadListener(new DownLoadFile.DownLoadListener() {
            @Override
            public void getProgress(int progress) {
                holder.progressBar.setProgress(progress);
            }

            @Override
            public void onComplete() {

            }

            @Override
            public void onFailure() {

            }
        });


    }


    @Override
    public int getItemCount() {
        return mDownloadList == null ? 0 : mDownloadList.size();

    }

    public void setData(List<DownLoadFile> data) {
        this.mDownloadList = data;
        notifyDataSetChanged();
    }

    class VH extends RecyclerView.ViewHolder {
        Button bt_download_button;
        TextView tv_download_info;
        ProgressBar progressBar;
        TextView tv_name_download;

        public VH(@NonNull View itemView) {
            super(itemView);
            bt_download_button = itemView.findViewById(R.id.bt_download_button);
            tv_download_info = itemView.findViewById(R.id.tv_download_info);
            progressBar = itemView.findViewById(R.id.progressBar);
            tv_name_download = itemView.findViewById(R.id.tv_name_download);
        }
    }
}
