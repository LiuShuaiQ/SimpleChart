package com.liushuai.mylibrary.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

/**
 * 所有图标的基类
 * Created by LiuShuai on 2016/9/12.
 */
public abstract class BaseChart extends View implements IChart {

    /**
     * default the chart width and height
     * (默认的宽和高)
     */
    public static final int DEFAULT_WIDTH = 300;
    public static final int DEFAULT_HEIGHT = 300;
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

    /**
     * It will be invoke in the constructor
     *
     * @param context
     * @param attrs
     * @param defStyleAttr
     */
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


    protected int measureWidth(int measureSpec) {
        int result = 0;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize;
        } else {
            result = DEFAULT_WIDTH;
            if (specMode == MeasureSpec.AT_MOST)
                result = Math.min(result, specSize);
        }
        return result;
    }

    protected int measureHeight(int measureSpec) {
        int result = 0;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize;
        } else {
            result = DEFAULT_HEIGHT;
            if (specMode == MeasureSpec.AT_MOST)
                result = Math.min(result, specSize);
        }
        return result;
    }

}
