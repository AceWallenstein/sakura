package com.example.sakura.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.sakura.R;
import com.example.sakura.base.BaseAdapter;
import com.example.sakura.data.bean.Comic;
import com.example.sakura.data.bean.ComicDetail;

public class ComicCollectedAdapter extends BaseAdapter<ComicDetail, ComicCollectedAdapter.VH> {

    public ComicCollectedAdapter(Context context) {
        super(context);
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new VH(LayoutInflater.from(mContext).inflate(R.layout.comic_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        super.onBindViewHolder(holder, position);
        holder.mTvComicTitle.setText(mData.get(position).getTitle());
        Glide.with(mContext).load(mData.get(position).getImgUrl()).
                placeholder(R.drawable.a).into(holder.mIvComic);

    }

    static class VH extends RecyclerView.ViewHolder {
        ImageView mIvComic;
        TextView mTvComicTitle;
        TextView mTvEpisode;

        public VH(@NonNull View itemView) {
            super(itemView);
            mIvComic = itemView.findViewById(R.id.iv_comic);
            mTvComicTitle = itemView.findViewById(R.id.tv_comic_title);
            mTvEpisode = itemView.findViewById(R.id.tv_episode);
        }
    }
}
