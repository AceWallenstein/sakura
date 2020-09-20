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
import com.example.sakura.data.bean.ComicDetail;

import java.util.List;

public class SearchAdapter extends BaseAdapter<ComicDetail, SearchAdapter.VH> {

    public SearchAdapter(Context context) {
        super(context);
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new VH(LayoutInflater.from(mContext).inflate(R.layout.search_comic_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        super.onBindViewHolder(holder, position);
        holder.mTvName.setText(mData.get(position).getTitle());
        holder.mTvDesc.setText(mData.get(position).getDesc());
        Glide.with(mContext).load(mData.get(position).getImgUrl()).
        placeholder(R.drawable.a).into(holder.mIvComicPic);

    }

    @Override
    public void setData(List<ComicDetail> data) {
        mData.clear();
        super.setData(data);
    }

    public void clear() {
        mData.clear();
        notifyDataSetChanged();
    }

    class VH extends RecyclerView.ViewHolder {
        private ImageView mIvComicPic;
        private TextView mTvName;
        private TextView mUpdateTime;
        private TextView mTvDesc;

        public VH(@NonNull View itemView) {
            super(itemView);
            mIvComicPic = itemView.findViewById(R.id.iv_comic_pic);
            mTvName = itemView.findViewById(R.id.tv_name);
            mUpdateTime = itemView.findViewById(R.id.update_time);
            mTvDesc = itemView.findViewById(R.id.tv_desc);

        }
    }
}
