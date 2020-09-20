package com.example.sakura.widget;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.sakura.R;

public class CommonHeaderBar extends FrameLayout {
    private boolean isShowBack;
    private String titleText;
    private ImageView ivBackHeader;
    private TextView tvTitleHeader;

    public CommonHeaderBar(@NonNull Context context) {
        this(context, null);
    }

    public CommonHeaderBar(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CommonHeaderBar(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.HeaderBar);
        isShowBack = typedArray.getBoolean(R.styleable.HeaderBar_isShowBack, true);
        titleText = typedArray.getString(R.styleable.HeaderBar_titleText);
        View view = inflate(context, R.layout.header_bar, this);
        initView(view);
        typedArray.recycle();
    }

    private void initView(View view) {
        ivBackHeader = view.findViewById(R.id.iv_back_header);
        tvTitleHeader = view.findViewById(R.id.tv_title_header);
        if (isShowBack) {
            ivBackHeader.setVisibility(VISIBLE);
        } else {
            ivBackHeader.setVisibility(GONE);
        }
        if (titleText != null)
            tvTitleHeader.setText(titleText);
        ivBackHeader.setOnClickListener((v) -> {
            if (getContext() instanceof Activity) {
                ((Activity) getContext()).finish();
            }
        });
    }

}

