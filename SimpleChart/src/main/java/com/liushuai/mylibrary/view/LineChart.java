package com.liushuai.mylibrary.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;

import com.liushuai.mylibrary.R;
import com.liushuai.mylibrary.utils.ChartCalUtils;

/**
 * 折线图
 * Created by LiuShuai on 2016/9/12.
 */
public class LineChart extends BaseBarAndLineChart {

    /**
     * 线的颜色
     */
    private int mLineColor;

    /**
     * 线的宽度
     */
    private int mLineWidth;

    private Paint mLinePaint;

    public LineChart(Context context) {
        super(context);
    }

    public LineChart(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void initStyle(TypedArray array) {
        mLineColor = array.getColor(R.styleable.MyIncreaseChartView_lineColor, Color.parseColor("#0000FF"));
    }

    @Override
    protected void initPaint() {
        //折线图的线画笔
        mLinePaint = new Paint();
        mLinePaint.setColor(mLineColor);
        mLinePaint.setStrokeWidth(5);
        mLinePaint.setStyle(Paint.Style.FILL);
        mLinePaint.setAntiAlias(true);
    }


    public LineChart(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void drawPerX(float xItemAxis, float xItemL, int i, Canvas canvas) {
        //画x轴的数据
        canvas.drawText(mChartData.getXAxisString().get(i), xItemAxis, mHeight + paddingTop + ChartCalUtils.getTextHeight(mAxisPaint) + 5, mXAxisTextPaint);

        for (int i1 = 0; i1 < mChartData.getValues().length; i1++) {
            //根据不同的图改变颜色
            if (i1 < mChartData.getColors().length) {
                mLinePaint.setColor(mChartData.getColors()[i1]);
            }
            //画小圆点
            canvas.drawCircle(xItemAxis, paddingTop + mHeight - ChartCalUtils.transValueToHeight(mChartData.getValues()[i1][i],
                    Float.valueOf(mChartData.getYRightAxisString().get(0)), Float.valueOf(mChartData.getYRightAxisString().get(mChartData.getYLeftAxisString().size() - 1)), mHeight),
                    10, mLinePaint);
            if (i > 0) {
                canvas.drawLine(xItemAxis - xItemL,
                        paddingTop + mHeight - ChartCalUtils.transValueToHeight(mChartData.getValues()[i1][i - 1], Float.valueOf(mChartData.getYRightAxisString().get(0)), Float.valueOf(mChartData.getYRightAxisString().get(mChartData.getYLeftAxisString().size() - 1)), mHeight),
                        xItemAxis,
                        paddingTop + mHeight - ChartCalUtils.transValueToHeight(mChartData.getValues()[i1][i],
                                Float.valueOf(mChartData.getYRightAxisString().get(0)),
                                Float.valueOf(mChartData.getYRightAxisString().get(mChartData.getYLeftAxisString().size() - 1)), mHeight),
                        mLinePaint);
            }
        }

    }

    @Override
    protected void beforeDraw() {

    }
}
