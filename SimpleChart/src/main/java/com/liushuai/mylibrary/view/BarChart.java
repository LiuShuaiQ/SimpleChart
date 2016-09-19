package com.liushuai.mylibrary.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;

import com.liushuai.mylibrary.R;
import com.liushuai.mylibrary.model.RectModel;
import com.liushuai.mylibrary.utils.ChartCalUtils;

/**
 * 柱状图
 * Created by LiuShuai on 2016/9/13.
 */
public class BarChart extends BaseBarAndLineChart {

    /**
     * 柱子的颜色
     */
    private int mBarColor;

    /**
     * 柱子的宽度
     */
    private int mBarWidth;

    private Paint mBarPaint;

    /**
     * 柱子每个实体
     */
    private RectModel[][] mRectModels;

    public BarChart(Context context) {
        super(context);
    }

    public BarChart(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public BarChart(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void initStyle(TypedArray a) {
        mBarColor = a.getColor(R.styleable.MyIncreaseChartView_firstRectColor, Color.parseColor("#0000FF"));
        mBarWidth = a.getDimensionPixelSize(R.styleable.MyIncreaseChartView_rectWidth, 20);
    }

    @Override
    protected void initPaint() {
        mBarPaint = new Paint();
        mBarPaint.setColor(mBarColor);
        mBarPaint.setStrokeWidth(2);
        mBarPaint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void drawPerX(float xItemAxis, float xItemL, int i, Canvas canvas) {

        float xIndex = xItemAxis - (mBarWidth * (mChartData.getValues().length) / 2);
        for (int j = 0; j < mChartData.getValues().length; j++) {
            //多个柱状图时需要分颜色画
            if (j < mChartData.getValues().length) {
                mBarPaint.setColor(mChartData.getColors()[j]);
            }
            mRectModels[j][i] = new RectModel(xIndex, paddingTop + mHeight - ChartCalUtils.transValueToHeight(mChartData.getValues()[j][i]
                    , Float.valueOf(mChartData.getYLeftAxisString().get(0)), Float.valueOf(mChartData.getYLeftAxisString().get(mChartData.getYLeftAxisString().size() - 1)), mHeight), xIndex + mBarWidth, paddingTop + mHeight, mChartData.getValues()[j][i]);
            mRectModels[j][i].draw(canvas, mBarPaint);

            xIndex += mBarWidth;
        }
    }

    @Override
    protected void beforeDraw() {
        mRectModels = new RectModel[mChartData.getValues().length][];
        for (int i = 0; i < mRectModels.length; i++) {
            mRectModels[i] = new RectModel[mChartData.getValues()[i].length];
        }
    }
}
