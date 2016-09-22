package com.liushuai.mylibrary.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.liushuai.mylibrary.R;
import com.liushuai.mylibrary.model.RectModel;
import com.liushuai.mylibrary.utils.ChartCalUtils;

import java.util.List;

/**
 * 自定义组合图
 * Created by LiuShuai on 2016/8/24.
 */
public class MyCombinedView extends View {

    /**
     * 默认的宽和高
     */
    public static final int DEFAULT_WIDTH = 300;
    public static final int DEFAULT_HEIGHT = 300;
    private int mWidth;
    private int mHeight;

    /**
     * y轴坐标的个数
     */
    private int yCount;
    /**
     * x轴坐标的个数
     */
    private int xCount;

    /**
     * 柱状图的第一个柱子颜色
     */
    private int mFitstRectColor;
    /**
     * 柱状图的第二个柱子颜色
     */
    private int mSecondRectColor;

    /**
     * 折线图的线的颜色
     */
    private int mLineColor;

    /**
     * 柱状图的柱子宽度
     */
    private int mRectWidth;

    /**
     * x轴文字的大小
     */
    private int mXTextSize;
    /**
     * y轴文字的大小
     */
    private int mYTextSize;

    /**
     * 背景线的颜色
     */
    private int mBackLineColor;

    /**
     * X轴是否显示
     */
    private boolean mXAxisEnable = true;
    /**
     * Y轴是否显示
     */
    private boolean mYAxisEnable = true;

    /**
     * 坐标轴的颜色
     */
    private int mAxisColor;

    /**
     * 是否画背景线
     */
    private boolean mBackLineEnable = true;

    /**
     * 坐标轴文字的颜色
     */
    private int mAxisTextColor;

    /**
     * 表格中的数据
     */
    private ChartData mChartData = null;

    /**
     * 第一个柱状图实体
     */
    private RectModel[] mFirstRects;


    /**
     * 第二个柱状图的实体
     */
    private RectModel[] mSecondRects;

    private Context mContext;

    /**
     * 距离上方的间距,由字体大小决定
     */
    private float paddingTop = 20;
    /**
     * 距离上方的间距,由字体大小决定
     */
    private float paddingBottom = 20;

    private float paddingLeft = 20;

    private float paddingRight = 20;

    private Paint mAxisPaint;
    private Paint mBgLinePaint;
    private Paint mFirstRectPaint;
    private Paint mSecondRectPaint;
    private Paint mLinePaint;
    private Paint mYAxisTextPaint;
    private Paint mXAxisTextPaint;

    private float mLeftTextWidth;
    private float mRightTextWidth;


    public MyCombinedView(Context context) {
        this(context, null);
    }

    public MyCombinedView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyCombinedView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.MyChartView, defStyleAttr, 0);
        mFitstRectColor = a.getColor(R.styleable.MyChartView_firstRectColor, Color.parseColor("#5FB1ED"));
        mSecondRectColor = a.getColor(R.styleable.MyChartView_secondRectColor, Color.parseColor("#00FF00"));
        mLineColor = a.getColor(R.styleable.MyChartView_lineColor, Color.parseColor("#0000FF"));

        mRectWidth = a.getDimensionPixelSize(R.styleable.MyChartView_rectWidth, 20);
        mXTextSize = a.getDimensionPixelSize(R.styleable.MyChartView_xTextSize, 20);
        mYTextSize = a.getDimensionPixelSize(R.styleable.MyChartView_yTextSize, 20);
        mBackLineColor = a.getColor(R.styleable.MyChartView_backLineColor, Color.GRAY);
        mBackLineEnable = a.getBoolean(R.styleable.MyChartView_backLineEnable, true);
        mXAxisEnable = a.getBoolean(R.styleable.MyChartView_xEnable, true);
        mYAxisEnable = a.getBoolean(R.styleable.MyChartView_yEnable, true);
        mAxisColor = a.getColor(R.styleable.MyChartView_axisColor, Color.BLACK);
        mAxisTextColor = a.getColor(R.styleable.MyChartView_axisTextColor, Color.BLACK);

        a.recycle();

        mAxisPaint = new Paint();
        mAxisPaint.setColor(mAxisColor);
        mAxisPaint.setStrokeWidth(2);
        mAxisPaint.setStyle(Paint.Style.FILL);


        mBgLinePaint = new Paint();
        mBgLinePaint.setColor(mBackLineColor);
        mBgLinePaint.setStrokeWidth(2);
        mBgLinePaint.setStyle(Paint.Style.FILL);

        mFirstRectPaint = new Paint();
        mFirstRectPaint.setColor(mFitstRectColor);
        mFirstRectPaint.setStrokeWidth(2);
        mFirstRectPaint.setStyle(Paint.Style.FILL);

        mSecondRectPaint = new Paint();
        mSecondRectPaint.setColor(mSecondRectColor);
        mSecondRectPaint.setStrokeWidth(2);
        mSecondRectPaint.setStyle(Paint.Style.FILL);

        mLinePaint = new Paint();
        mLinePaint.setColor(mLineColor);
        mLinePaint.setStrokeWidth(5);
        mLinePaint.setStyle(Paint.Style.FILL);
        mLinePaint.setAntiAlias(true);


        mYAxisTextPaint = new Paint();
        mYAxisTextPaint.setColor(mAxisTextColor);
        mYAxisTextPaint.setStrokeWidth(5);
        mYAxisTextPaint.setTextSize(mYTextSize);
        mYAxisTextPaint.setAntiAlias(true);
        mYAxisTextPaint.setStyle(Paint.Style.FILL);

        mXAxisTextPaint = new Paint();
        mXAxisTextPaint.setColor(mAxisTextColor);
        mXAxisTextPaint.setAntiAlias(true);
        mXAxisTextPaint.setStrokeWidth(5);
        mXAxisTextPaint.setTextSize(mXTextSize);
        mXAxisTextPaint.setStyle(Paint.Style.FILL);


    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        mWidth = measureWidth(widthMeasureSpec);
        mHeight = measureHeight(heightMeasureSpec);
        //获取文字的高度
        Paint.FontMetrics fontMetrics = mXAxisTextPaint.getFontMetrics();
        float fontTotalHeight = fontMetrics.bottom - fontMetrics.top;

        setMeasuredDimension(mWidth, mHeight + (int) paddingTop + (int) paddingBottom + (int) fontTotalHeight);
    }

    private int measureWidth(int measureSpec) {
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

    private int measureHeight(int measureSpec) {
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

    @Override
    protected void onDraw(Canvas canvas) {
        //计算两端的文字宽度
        mLeftTextWidth = getMaxTextWidth(mChartData.yLeftAxisString, mYAxisTextPaint);
        mRightTextWidth = getMaxTextWidth(mChartData.yRightAxisString, mYAxisTextPaint);
        if (mChartData != null) {
            mFirstRects = new RectModel[mChartData.mFirstBarValues.length];
            mSecondRects = new RectModel[mChartData.mSecondBarValues.length];

            //画出y轴
            //画出左边的y轴
            //每个坐标相距的距离
            float yItemHight = mHeight / (mChartData.yLeftAxisString.size() - 1);

            //画两个y轴的线
//            canvas.drawLine(mLeftTextWidth, 0, mLeftTextWidth, mHeight, mAxisPaint);
//            canvas.drawLine(mLeftTextWidth + mWidth, 0,  mLeftTextWidth + mWidth, p mHeight, mAxisPaint);
//            canvas.drawLine(mWidth, 0, mWidth, mHeight, mAxisPaint);

            mAxisPaint.setTextAlign(Paint.Align.CENTER);
            float yItemAxis = mHeight;
            for (int i = 0; i < mChartData.yLeftAxisString.size(); i++) {
                //画出x轴平行的线
                canvas.drawLine(mLeftTextWidth, paddingTop + yItemAxis, mWidth - mRightTextWidth, paddingTop + yItemAxis, mAxisPaint);
                canvas.drawText(mChartData.yLeftAxisString.get(i), 0, paddingTop + yItemAxis, mYAxisTextPaint);
                canvas.drawText(mChartData.yRightAxisString.get(i), mWidth - mRightTextWidth, paddingTop + yItemAxis, mYAxisTextPaint);
                yItemAxis -= yItemHight;
            }


            //每个坐标相距的距离
            float xItemL = (mWidth - mLeftTextWidth - mRightTextWidth) / (mChartData.xAxisString.size() + 1);
            mAxisPaint.setTextAlign(Paint.Align.CENTER);
            float xItemAxis = mLeftTextWidth + xItemL;
            //获取文字的高度（现放到ChartUtils类中）
//            Paint.FontMetrics fontMetrics = mAxisPaint.getFontMetrics();
//            float fontTotalHeight = fontMetrics.bottom - fontMetrics.top;
            for (int i = 0; i < mChartData.xAxisString.size(); i++) {
                canvas.drawText(mChartData.xAxisString.get(i), xItemAxis - mRectWidth / 2, mHeight + paddingTop + ChartCalUtils.getTextHeight(mAxisPaint) + 5, mXAxisTextPaint);
                mFirstRects[i] = new RectModel(xItemAxis - mRectWidth, paddingTop + mHeight - transValueToHeight(mChartData.mFirstBarValues[i]
                        , Float.valueOf(mChartData.yLeftAxisString.get(0)), Float.valueOf(mChartData.yLeftAxisString.get(mChartData.yLeftAxisString.size() - 1)), mHeight), xItemAxis, paddingTop + mHeight, mChartData.mFirstBarValues[i]);
                //画第一个柱子
                mFirstRects[i].draw(canvas, mFirstRectPaint);

                mSecondRects[i] = new RectModel(xItemAxis, paddingTop + mHeight - transValueToHeight(mChartData.mSecondBarValues[i]
                        , Float.valueOf(mChartData.yLeftAxisString.get(0)), Float.valueOf(mChartData.yLeftAxisString.get(mChartData.yLeftAxisString.size() - 1)), mHeight), xItemAxis + mRectWidth, paddingTop + mHeight, mChartData.mSecondBarValues[i]);
                //画第二个柱子
                mSecondRects[i].draw(canvas, mSecondRectPaint);

                //画小圆点
                canvas.drawCircle(xItemAxis, paddingTop + mHeight - transValueToHeight(mChartData.mLineValues[i], Float.valueOf(mChartData.yRightAxisString.get(0)), Float.valueOf(mChartData.yRightAxisString.get(mChartData.yLeftAxisString.size() - 1)), mHeight), 10, mLinePaint);
                if (i > 0) {
                    canvas.drawLine(xItemAxis - xItemL,
                            paddingTop + mHeight - transValueToHeight(mChartData.mLineValues[i - 1], Float.valueOf(mChartData.yRightAxisString.get(0)), Float.valueOf(mChartData.yRightAxisString.get(mChartData.yLeftAxisString.size() - 1)), mHeight),
                            xItemAxis,
                            paddingTop + mHeight - transValueToHeight(mChartData.mLineValues[i],
                                    Float.valueOf(mChartData.yRightAxisString.get(0)),
                                    Float.valueOf(mChartData.yRightAxisString.get(mChartData.yLeftAxisString.size() - 1)), mHeight),
                            mLinePaint);
                }


                xItemAxis += xItemL;
            }


        } else {
            canvas.drawText("无数据", mWidth / 2, mHeight / 2, mAxisPaint);
        }
    }


    /**
     * 将数据转化为高度
     *
     * @return
     */
    private float transValueToHeight(float v, float minV, float maxV, float sumHeight) {
        return v / (maxV - minV) * sumHeight;
    }

    /**
     * 计算几个文字的最大宽度
     *
     * @param strings
     * @param paint
     * @return
     */
    private float getMaxTextWidth(List<String> strings, Paint paint) {
        float sumTextWidth = 0;
        if (strings != null && strings.size() > 0) {
            int yMaxLength = 0;
            int yMaxIndex = 0;
            for (int i = 0; i < strings.size(); i++) {
                if (strings.get(i).length() > yMaxLength) {
                    yMaxIndex = i;
                    yMaxLength = strings.get(i).length();
                }
            }
            float[] yWidthFloats = new float[strings.get(yMaxIndex).length()];
            paint.getTextWidths(strings.get(yMaxIndex), 0, yMaxLength, yWidthFloats);
            for (float f : yWidthFloats) {
                sumTextWidth += f;
            }
        }

        return sumTextWidth;
    }

    public ChartData getChartData() {
        return mChartData;
    }

    public void setChartData(ChartData chartData) {
        mChartData = chartData;
    }

    /**
     * 组合图中的数据
     */
    public static class ChartData {
        /**
         * x轴显示的内容
         */
        List<String> xAxisString;
        /**
         * 左边y轴显示的内容
         */
        List<String> yLeftAxisString;

        /**
         * 左边y轴显示的内容
         */
        List<String> yRightAxisString;
        /**
         * 增长图中第一个柱状图的值
         */
        float[] mFirstBarValues;
        /**
         * 增长图中第二个柱状图的值
         */
        float[] mSecondBarValues;

        /**
         * 则线图中的值
         */
        float[] mLineValues;

        public ChartData(List<String> xAxisString, List<String> yLeftAxisString, List<String> yRightAxisString, float[] firstBarValues, float[] secondBarValues, float[] lineValues) {
            this.xAxisString = xAxisString;
            this.yLeftAxisString = yLeftAxisString;
            this.yRightAxisString = yRightAxisString;
            mFirstBarValues = firstBarValues;
            mSecondBarValues = secondBarValues;
            mLineValues = lineValues;
        }
    }
}
