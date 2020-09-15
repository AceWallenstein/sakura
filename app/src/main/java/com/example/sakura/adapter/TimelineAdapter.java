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

public class TimelineAdapter extends BaseAdapter<Comic, TimelineAdapter.VH> {

    public TimelineAdapter(Context context) {
        super(context);
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new VH(LayoutInflater.from(mContext).inflate(R.layout.timeline_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        super.onBindViewHolder(holder, position);
        holder.mTvTitle.setText(mData.get(position).getTitle());
        holder.mTvNum.setText(mData.get(position).getEpisode());
    }

    class VH extends RecyclerView.ViewHolder {
        TextView mTvTitle;
        TextView mTvNum;

        public VH(@NonNull View itemView) {
            super(itemView);
            mTvTitle = itemView.findViewById(R.id.tv_title);
            mTvNum = itemView.findViewById(R.id.tv_num);
        }
    }
}
