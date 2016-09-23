package com.liushuai.mylibrary.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;

import com.liushuai.mylibrary.R;
import com.liushuai.mylibrary.data.BarAndLineChartData;
import com.liushuai.mylibrary.model.RectModel;
import com.liushuai.mylibrary.utils.ChartCalUtils;

/**
 * the chart of bar is increased
 * Created by LiuShuai on 2016/9/23.
 */

public class IncreaseBarChart extends BaseBarAndLineChart {

    /**
     * 柱子的颜色
     */
    private int mBarColor;

    /**
     * 柱子的宽度
     */
    private int mBarWidth;

    private Paint mBarPaint;
    private RectModel[][] mRectModels;

    public IncreaseBarChart(Context context) {
        super(context);
    }

    public IncreaseBarChart(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public IncreaseBarChart(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void initStyle(TypedArray array) {
        mBarColor = array.getColor(R.styleable.MyChartView_firstRectColor, Color.parseColor("#0000FF"));
        mBarWidth = array.getDimensionPixelSize(R.styleable.MyChartView_rectWidth, 20);

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

        mChartData.setYAxisTextSetter(new BarAndLineChartData.YAxisTextSetter() {
            @Override
            public String setLeftYAxisText(int yValue) {
                return String.valueOf(yValue);
            }

            @Override
            public String setRightYAxisText(int yValue) {
                return String.valueOf(yValue);
            }

            @Override
            public int setYValues(int i, int yValue) {
                return yValue+calValue(i);
            }

            private int calValue(int i) {
                int result = 0;
                for (int j = 0; j < i; j++) {
                    result += mChartData.getValues()[0][i];
                }
                return result;
            }
        });

        float xIndex = xItemAxis - (mBarWidth * (mChartData.getValues().length) / 2);
        float currY = paddingTop + mHeight;
        for (int j = 0; j < mChartData.getValues().length; j++) {
            //多个柱状图时需要分颜色画
            if (j < mChartData.getValues().length) {
                mBarPaint.setColor(mChartData.getColors()[j]);
            }
            mRectModels[j][i] = new RectModel(
                    xIndex,
                    paddingTop + mHeight - ChartCalUtils.transValueToHeight(mChartData.getValues()[j][i], Float.valueOf(mChartData.getYLeftAxisString().get(0)),
                            Float.valueOf(mChartData.getYLeftAxisString().get(mChartData.getYLeftAxisString().size() - 1)), mHeight),
                    xIndex + mBarWidth,
                    paddingTop + mHeight,
                    mChartData.getValues()[j][i]);
            mRectModels[j][i].draw(canvas, mBarPaint);

            xIndex += mBarWidth;
        }
    }

    @Override
    protected void beforeDraw() {
        if (mChartData != null) {
            mRectModels = new RectModel[mChartData.getValues().length][];
            for (int i = 0; i < mChartData.getValues().length; i++) {
                mRectModels[i] = new RectModel[mChartData.getValues()[i].length];
            }
        }
    }
}
