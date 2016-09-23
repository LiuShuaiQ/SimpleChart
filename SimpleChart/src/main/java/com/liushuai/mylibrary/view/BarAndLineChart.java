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
 * the chart of the bar and line chart<br/>
 * and the line data should before of the bar chart , and should invoke the method of the 'setLineDataNum'
 * <p>
 * Created by LiuShuai on 2016/9/23.
 */

public class BarAndLineChart extends BaseBarAndLineChart {
    /**
     * the color of bar
     */
    private int mBarColor;

    /**
     * the width of the bar
     */
    private int mBarWidth;

    private Paint mBarPaint;
    /**
     * the color of the line
     */
    private int mLineColor;

    /**
     * the width of the line
     */
    private int mLineWidth;

    private Paint mLinePaint;
    /**
     * 柱子每个实体
     */
    private RectModel[][] mRectModels;

    /**
     * the num of the line data
     */
    private int mLineDataNum = 0;


    public BarAndLineChart(Context context) {
        super(context);
    }

    public BarAndLineChart(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public BarAndLineChart(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void initStyle(TypedArray array) {
        mLineColor = array.getColor(R.styleable.MyChartView_lineColor, Color.parseColor("#0000FF"));
        mBarColor = array.getColor(R.styleable.MyChartView_firstRectColor, Color.parseColor("#0000FF"));
        mBarWidth = array.getDimensionPixelSize(R.styleable.MyChartView_rectWidth, 20);

    }

    @Override
    protected void initPaint() {
        //the paint of the line chart
        mLinePaint = new Paint();
        mLinePaint.setColor(mLineColor);
        mLinePaint.setStrokeWidth(5);
        mLinePaint.setStyle(Paint.Style.FILL);
        mLinePaint.setAntiAlias(true);

        //the paint of the bar chart
        mBarPaint = new Paint();
        mBarPaint.setColor(mBarColor);
        mBarPaint.setStrokeWidth(2);
        mBarPaint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void drawPerX(float xItemAxis, float xItemL, int i, Canvas canvas) {

        if (mLineDataNum > mChartData.getValues().length) {
            mLineDataNum = mChartData.getValues().length;
        }


        //draw the barChart
        float xIndex = xItemAxis - (mBarWidth * (mChartData.getValues().length - mLineDataNum) / 2);
        for (int j = mLineDataNum; j < mChartData.getValues().length; j++) {
            //多个柱状图时需要分颜色画
            if (j < mChartData.getValues().length) {
                mBarPaint.setColor(mChartData.getColors()[j]);
            }
            mRectModels[j][i] = new RectModel(xIndex, paddingTop + mHeight - ChartCalUtils.transValueToHeight(mChartData.getValues()[j][i]
                    , Float.valueOf(mChartData.getYLeftAxisString().get(0)), Float.valueOf(mChartData.getYLeftAxisString().get(mChartData.getYLeftAxisString().size() - 1)), mHeight), xIndex + mBarWidth, paddingTop + mHeight, mChartData.getValues()[j][i]);
            mRectModels[j][i].draw(canvas, mBarPaint);

            xIndex += mBarWidth;
        }

        //draw the lineChart
        for (int i1 = 0; i1 < mLineDataNum; i1++) {
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
        mRectModels = new RectModel[mChartData.getValues().length][];
        for (int i = 0; i < mRectModels.length; i++) {
            mRectModels[i] = new RectModel[mChartData.getValues()[i].length];
        }
    }

    public int getLineDataNum() {
        return mLineDataNum;
    }

    public void setLineDataNum(int lineDataNum) {
        mLineDataNum = lineDataNum;
    }
}
