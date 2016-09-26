package com.liushuai.mylibrary.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.FloatProperty;

import com.liushuai.mylibrary.R;
import com.liushuai.mylibrary.data.BarAndLineChartData;
import com.liushuai.mylibrary.data.Entity;
import com.liushuai.mylibrary.data.IData;
import com.liushuai.mylibrary.data.IEntity;
import com.liushuai.mylibrary.formatter.ValueFormatter;
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

    private int preY = 0;

    @Override
    protected void drawPerX(float xItemAxis, float xItemL, int i, Canvas canvas) {

        float xIndex = xItemAxis - (mBarWidth * (mChartData.getValues().length) / 2);
        float currY = paddingTop + mHeight;
        float currHeight = 0;
        for (int j = 0; j < mChartData.getValues().length; j++) {
            currHeight = ChartCalUtils.transValueToHeight(mChartData.getValues()[j][i], Float.valueOf(mChartData.getYLeftAxisString().get(0)),
                    Float.valueOf(mChartData.getYLeftAxisString().get(mChartData.getYLeftAxisString().size() - 1)), mHeight);
            //多个柱状图时需要分颜色画
            if (j < mChartData.getValues().length) {
                mBarPaint.setColor(mChartData.getColors()[j]);
            }
            mRectModels[j][i] = new RectModel(
                    xIndex,
                    paddingTop + mHeight - currHeight,
                    xIndex + mBarWidth,
                    currY,
                    mChartData.getValues()[j][i]);
            //画增长图，对x轴进行平移
            canvas.save();
            if (i != 0) {
                canvas.translate(0, -1 *   ChartCalUtils.transValueToHeight(sumValue(j,i),Float.valueOf(mChartData.getYLeftAxisString().get(0)),
                        Float.valueOf(mChartData.getYLeftAxisString().get(mChartData.getYLeftAxisString().size() - 1)), mHeight));
            }

            mRectModels[j][i].draw(canvas, mBarPaint);
            canvas.restore();
            xIndex += mBarWidth;
            preY += currHeight;
        }
    }

    /**
     * 计算前几个的高度
     * @param index
     * @param j
     * @return
     */
    private float sumValue(int index,int j){
        float sumH = 0;
        if (index<mChartData.getValues().length){
            if (j<mChartData.getValues()[index].length){
                for (int i=0;i<j;i++){
                    sumH+=mChartData.getValues()[index][i];
                }
            }
        }
        return sumH;
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

    @Override
    public void setData(IData data) {
        super.setData(data);
        setDataMaxValue();
    }


    /**
     * 向Y轴设置最大值
     */
    private void setDataMaxValue() {
        float sum = 0;
        float maxSum = Float.MIN_VALUE;
        for (int i = 0; i < mChartData.getEntity().size(); i++) {
            sum = 0;
            for (IEntity e : mChartData.getEntity().get(i)) {
                sum += e.getValues();
            }
            if (sum > maxSum) {
                maxSum = sum;
            }
        }
        final float finalMaxSum = maxSum;
        mChartData.setYMaxValueFormatter(new ValueFormatter() {
            @Override
            public float format(int index, float formatString) {
                return finalMaxSum;
            }
        });
        mChartData.refreshYText();
    }
}
