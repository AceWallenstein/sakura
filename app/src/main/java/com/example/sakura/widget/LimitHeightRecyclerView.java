package com.example.sakura.widget;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

public class LimitHeightRecyclerView extends RecyclerView {
    public LimitHeightRecyclerView(@NonNull Context context) {
        super(context);
    }

    public LimitHeightRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public LimitHeightRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthSpec, int heightSpec) {
        super.onMeasure(widthSpec, heightSpec);
        int height = MeasureSpec.makeMeasureSpec(900, MeasureSpec.AT_MOST);
        super.onMeasure(widthSpec, height);
    }
}
