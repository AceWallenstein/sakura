package com.example.sakura.base;

import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseAdapter<T,VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {
    protected Context mContext;
    protected List<T> mData = new ArrayList<>();
    protected OnclickListener listener;

    public void setOnClickListener(OnclickListener listener) {
        this.listener = listener;
    }

    public void setData(List<T> mData) {
        this.mData = mData;
        notifyDataSetChanged();
    }

    public BaseAdapter(Context context) {
        mContext = context;
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        holder.itemView.setOnClickListener((v) -> {
            if (listener != null) {
                listener.onClick(v,mData.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void  setOnclickListenr(OnclickListener listenr)
    {
        this.listener = listenr;
    }
    public interface OnclickListener<T> {
       void onClick(View v,T t);
    }
}
