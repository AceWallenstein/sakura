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
    protected OnLongClickListener onLongClickListener;

    public void setOnClickListener(OnclickListener listener) {
        this.listener = listener;
    }
    public void setOnLongClickListener(OnLongClickListener onLongClickListener) {
        this.onLongClickListener = onLongClickListener;
    }

    public void setData(List<T> data) {
        this.mData = data;
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
        holder.itemView.setOnLongClickListener((v)->{
            onLongClickListener.onLongClick(v,mData.get(position));
            return true;
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
    public interface OnLongClickListener<T>{
        void onLongClick(View v,T t);
    }
}
