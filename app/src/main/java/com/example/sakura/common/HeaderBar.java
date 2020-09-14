package com.example.sakura.common;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class HeaderBar extends FrameLayout {
    public HeaderBar(@NonNull Context context) {
        super(context);
    }

    public HeaderBar(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public HeaderBar(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
