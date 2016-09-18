package com.liushuai.mylibrary.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

/**
 * 所有图标的基类
 * Created by LiuShuai on 2016/9/12.
 */
public abstract class BaseChart extends View implements IChart {

    protected int mWidth;
    protected int mHeight;

    protected Context mContext;

    public BaseChart(Context context) {
        this(context, null);
    }

    public BaseChart(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init(context, attrs, defStyleAttr);
    }

    protected abstract void init(Context context, AttributeSet attrs, int defStyleAttr);

    public BaseChart(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    @Override
    public int getChartWidth() {
        return mWidth;
    }

    @Override
    public int getChartHeight() {
        return mHeight;
    }
}
