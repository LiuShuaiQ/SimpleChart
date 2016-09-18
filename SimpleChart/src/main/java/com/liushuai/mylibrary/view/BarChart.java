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
    private RectModel[] mRectModels;

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
        canvas.drawText(mChartData.getXAxisString().get(i), xItemAxis - mBarWidth / 2, mHeight + paddingTop + ChartCalUtils.getTextHeight(mAxisPaint) + 5, mXAxisTextPaint);
        mRectModels[i] = new RectModel(xItemAxis - mBarWidth, paddingTop + mHeight - ChartCalUtils.transValueToHeight(mChartData.getValues()[i]
                , Float.valueOf(mChartData.getYLeftAxisString().get(0)), Float.valueOf(mChartData.getYLeftAxisString().get(mChartData.getYLeftAxisString().size() - 1)), mHeight), xItemAxis, paddingTop + mHeight, mChartData.getValues()[i]);
        //画第一个柱子
        mRectModels[i].draw(canvas, mBarPaint);
    }

    @Override
    protected void beforeDraw() {
        mRectModels = new RectModel[mChartData.getValues().length];
    }
}
