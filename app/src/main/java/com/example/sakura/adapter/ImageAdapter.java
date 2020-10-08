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
import com.example.sakura.data.bean.Comic;
import com.youth.banner.adapter.BannerAdapter;

import java.util.List;

public class ImageAdapter extends BannerAdapter<Comic, ComicAdapter.VH> {
    Context mContext;

    public ImageAdapter(List<Comic> datas, Context context) {
        super(datas);
        mContext = context;
    }

    @Override
    public ComicAdapter.VH onCreateHolder(ViewGroup parent, int viewType) {
        return new ComicAdapter.VH(LayoutInflater.from(mContext).inflate(R.layout.banner_comic_item, parent, false));
    }

    @Override
    public void onBindView(ComicAdapter.VH holder, Comic comic, int position, int size) {
        holder.mTvComicTitle.setText(comic.getTitle());
        holder.mTvEpisode.setText(comic.getEpisode());
        Glide.with(mContext).load(comic.getImgUrl()).
                placeholder(R.drawable.a).into(holder.mIvComic);
    }


    class VH extends RecyclerView.ViewHolder {
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
