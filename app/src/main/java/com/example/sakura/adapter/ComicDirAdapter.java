package com.example.sakura.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sakura.R;
import com.example.sakura.base.BaseAdapter;
import com.example.sakura.data.bean.ComicDir;

import java.util.Collections;

public class ComicDirAdapter extends BaseAdapter<ComicDir, ComicDirAdapter.VH> {

    public ComicDirAdapter(Context context) {
        super(context);
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new VH(LayoutInflater.from(mContext).inflate(R.layout.episode_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        super.onBindViewHolder(holder, position);
        holder.tvEpisode.setText(mData.get(position).getNum());
    }

    public void reverse() {
        if(mData!=null&&mData.size()>0){
            Collections.reverse(mData);
            notifyDataSetChanged();
        }
    }

    class VH extends RecyclerView.ViewHolder {
        TextView tvEpisode;
        public VH(@NonNull View itemView) {
            super(itemView);
            tvEpisode = itemView.findViewById(R.id.tv_episode);
        }
    }
}
