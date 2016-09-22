package com.liushuai.mylibrary.view;

import android.content.ContentUris;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;

import com.liushuai.mylibrary.R;
import com.liushuai.mylibrary.data.IData;
import com.liushuai.mylibrary.data.IEntity;
import com.liushuai.mylibrary.data.PieChartData;
import com.liushuai.mylibrary.utils.ChartCalUtils;

import java.util.List;

/**
 * char of draw pie
 * Created by LiuShuai on 2016/9/19.
 */
public class PieChart extends BaseChart {
    private static final String TAG = "PieChart";

    /**
     * flag that the data if the data is percent enable
     */
    private boolean mPercentDataEnable;

    private PieChartData mPieChartData;

    /**
     * text size of the x-axis
     */
    private int mAxisTextSize;
    /**
     * text color of the x-axis
     */
    private int mAxisTextColor;
    /**
     * text size of the label
     */
    private int mLabelTextSize;
    /**
     * text color of the label
     */
    private int mLabelTextColor;

    /**
     * the width of the arc
     */
    private float mArcWidth = 150;

    /**
     * center text
     */
    private CharSequence mCenterText;

    /**
     * the text size of the center text
     */
    private int mCenterTextSize = 20;

    /**
     * the text color of the center text
     */
    private int mCenterTextColor;

    private Paint mPiePaint;
    private Paint mAxisTextPaint;
    private Paint mLabelTextPaint;
    private Paint mCenterTextPaint;

    public PieChart(Context context) {
        super(context);
    }

    public PieChart(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public PieChart(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    protected void init(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.MyChartView, defStyleAttr, 0);
        mAxisTextSize = a.getDimensionPixelSize(R.styleable.MyChartView_xTextSize, 20);
        mAxisTextColor = a.getColor(R.styleable.MyChartView_axisTextColor, Color.BLACK);
        mLabelTextSize = a.getDimensionPixelSize(R.styleable.MyChartView_labelTextSize, 20);
        mLabelTextColor = a.getColor(R.styleable.MyChartView_labelTextColor, Color.BLACK);
        mCenterText = a.getString(R.styleable.MyChartView_centerText);
        if (mCenterText == null) {
            mCenterText = "";
        }
        mCenterTextSize = a.getDimensionPixelSize(R.styleable.MyChartView_centerTextSize, 20);
        mCenterTextColor = a.getColor(R.styleable.MyChartView_centerTextColor, Color.BLACK);

        a.recycle();

        mPiePaint = new Paint();
        mPiePaint.setStrokeWidth(150);
        mPiePaint.setStyle(Paint.Style.STROKE);
        mPiePaint.setAntiAlias(true);

        mAxisTextPaint = new Paint();
        mAxisTextPaint.setTextSize(mAxisTextSize);
        mAxisTextPaint.setColor(mAxisTextColor);
        mAxisTextPaint.setAntiAlias(true);

        mLabelTextPaint = new Paint();
        mLabelTextPaint.setTextSize(mLabelTextSize);
        mLabelTextPaint.setColor(mLabelTextColor);
        mLabelTextPaint.setAntiAlias(true);

        mCenterTextPaint = new Paint();
        mCenterTextPaint.setAntiAlias(true);
        mCenterTextPaint.setColor(mCenterTextColor);
        mCenterTextPaint.setTextSize(mCenterTextSize);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        mWidth = measureWidth(widthMeasureSpec);
        mHeight = measureHeight(heightMeasureSpec);

        setMeasuredDimension(mWidth, mHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (mPieChartData != null) {
            //首先计算每一部分的角度
            if (mPieChartData.getEntity().size() > 0) {
                List<IEntity> es = mPieChartData.getEntity().get(0);
                float[] increasePercent = new float[es.size()];
                float sum = 0;
                for (IEntity i : es) {
                    sum += i.getValues();
                }


                float currAngle = 0;
                for (int i = 0; i < es.size(); i++) {
                    increasePercent[i] = es.get(i).getValues() / sum;
                    mPiePaint.setColor(mPieChartData.getColors()[i]);

                    canvas.drawArc(new RectF(mArcWidth, mArcWidth, mWidth - mArcWidth, mHeight - mArcWidth)
                            , currAngle, (increasePercent[i] * 360), false, mPiePaint);
                    currAngle += (increasePercent[i] * 360);
                }

                //draw the center text
                canvas.drawText(mCenterText.toString(), mWidth / 2 - ChartCalUtils.getTextWidth(mCenterText.toString(), mCenterTextPaint) / 2, mHeight / 2 + ChartCalUtils.getTextHeight(mCenterTextPaint) / 2, mCenterTextPaint);

            } else {
                Log.d(TAG, "the pie chart data is empty!");
            }
        } else {
            canvas.drawText("无数据", mWidth / 2, mHeight / 2, mAxisTextPaint);
        }
    }

    @Override
    public IData getData() {
        return mPieChartData;
    }

    @Override
    public void setData(IData data) {
        if (data instanceof PieChartData) {
            mPieChartData = (PieChartData) data;
        }
    }

    public boolean isPercentDataEnable() {
        return mPercentDataEnable;
    }

    public void setPercentDataEnable(boolean percentDataEnable) {
        mPercentDataEnable = percentDataEnable;
    }

    public int getCenterTextSize() {
        return mCenterTextSize;
    }

    public void setCenterTextSize(int centerTextSize) {
        mCenterTextSize = centerTextSize;
    }

    public int getCenterTextColor() {
        return mCenterTextColor;
    }

    public void setCenterTextColor(int centerTextColor) {
        mCenterTextColor = centerTextColor;
    }

    public CharSequence getCenterText() {
        return mCenterText;
    }

    public void setCenterText(CharSequence centerText) {
        mCenterText = centerText;
    }
}
