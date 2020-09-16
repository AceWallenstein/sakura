package com.example.sakura.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sakura.R;
import com.example.sakura.base.BaseAdapter;
import com.example.sakura.data.bean.ComicInfo;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.internal.observers.ForEachWhileObserver;

public class PopAdapter extends BaseAdapter<ComicInfo, PopAdapter.VH> {
    private List<ComicInfo> downloadList = new ArrayList<>();

    public PopAdapter(Context context) {
        super(context);
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new VH(LayoutInflater.from(mContext).inflate(R.layout.pop_rv_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        holder.tvEpisode.setText(mData.get(position).getNum());
        holder.itemView.setOnClickListener((v -> {
            if (listener != null) {
                listener.onClick(v, mData.get(position));
            }
            mData.get(position).changeFlag();
            boolean clickFlag = mData.get(position).getFlag();
            if (clickFlag) {
                holder.ivRedDot.setVisibility(View.VISIBLE);
                downloadList.add(mData.get(position));
            } else {
                holder.ivRedDot.setVisibility(View.GONE);
                if (downloadList.contains(mData.get(position)))
                    downloadList.remove(mData.get(position));
            }

        }));
    }

    public List<ComicInfo> getDownloadList() {
        return downloadList;
    }

    public void clear() {
        if (downloadList != null && downloadList.size() > 0) {
            downloadList.clear();
        }
        if (mData != null && mData.size() > 0) {
            for (ComicInfo info :
                    mData) {
                info.setFlag(false);
            }
        }
    }

    class VH extends RecyclerView.ViewHolder {
        TextView tvEpisode;
        private final ImageView ivRedDot;

        public VH(@NonNull View itemView) {
            super(itemView);
            tvEpisode = itemView.findViewById(R.id.tv_episode);
            ivRedDot = itemView.findViewById(R.id.iv_red_dot);
        }
    }
}
