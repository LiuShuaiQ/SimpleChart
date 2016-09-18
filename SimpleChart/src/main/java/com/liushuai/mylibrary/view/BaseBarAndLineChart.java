package com.liushuai.mylibrary.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;

import com.liushuai.mylibrary.R;
import com.liushuai.mylibrary.data.BarAndLineChartData;
import com.liushuai.mylibrary.data.IData;
import com.liushuai.mylibrary.utils.ChartCalUtils;

/**
 * 柱状图和折线图的基类
 * Created by LiuShuai on 2016/9/12.
 */
public abstract class BaseBarAndLineChart extends BaseChart {

    /**
     * 默认的宽和高
     */
    public static final int DEFAULT_WIDTH = 300;
    public static final int DEFAULT_HEIGHT = 300;

    /**
     * y轴坐标的个数
     */
    protected int yCount;
    /**
     * x轴坐标的个数
     */
    protected int xCount;

    /**
     * x轴文字的大小
     */
    protected int mXTextSize;
    /**
     * y轴文字的大小
     */
    protected int mYTextSize;

    /**
     * 背景线的颜色
     */
    protected int mBackLineColor;

    /**
     * X轴是否显示
     */
    protected boolean mXAxisEnable = true;
    /**
     * Y轴是否显示
     */
    protected boolean mYAxisEnable = true;

    /**
     * 坐标轴的颜色
     */
    protected int mAxisColor;

    /**
     * 是否画背景线
     */
    protected boolean mBackLineEnable = true;

    /**
     * 坐标轴文字的颜色
     */
    protected int mAxisTextColor;

    /**
     * 字和线之间的间距
     */
    protected int textLineSpace = 5;

    /**
     * 距离上方的间距,由字体大小决定
     */
    protected float paddingTop = 20;
    /**
     * 距离上方的间距,由字体大小决定
     */
    protected float paddingBottom = 20;

    protected float paddingLeft = 20;

    protected float paddingRight = 20;

    protected Paint mAxisPaint;
    protected Paint mBgLinePaint;
    protected Paint mYAxisTextPaint;
    protected Paint mXAxisTextPaint;

    protected float mLeftTextWidth;
    protected float mRightTextWidth;

    protected BarAndLineChartData mChartData;

    public BaseBarAndLineChart(Context context) {
        this(context, null);
    }

    public BaseBarAndLineChart(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public BaseBarAndLineChart(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    @Override
    protected void init(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.MyIncreaseChartView, defStyleAttr, 0);

        mXTextSize = a.getDimensionPixelSize(R.styleable.MyIncreaseChartView_xTextSize, 20);
        mYTextSize = a.getDimensionPixelSize(R.styleable.MyIncreaseChartView_yTextSize, 20);
        mBackLineColor = a.getColor(R.styleable.MyIncreaseChartView_backLineColor, Color.GRAY);
        mBackLineEnable = a.getBoolean(R.styleable.MyIncreaseChartView_backLineEnable, true);
        mXAxisEnable = a.getBoolean(R.styleable.MyIncreaseChartView_xEnable, true);
        mYAxisEnable = a.getBoolean(R.styleable.MyIncreaseChartView_yEnable, true);
        mAxisColor = a.getColor(R.styleable.MyIncreaseChartView_axisColor, Color.BLACK);
        mAxisTextColor = a.getColor(R.styleable.MyIncreaseChartView_axisTextColor, Color.BLACK);

        initStyle(a);

        a.recycle();

        mAxisPaint = new Paint();
        mAxisPaint.setColor(mAxisColor);
        mAxisPaint.setStrokeWidth(2);
        mAxisPaint.setStyle(Paint.Style.FILL);


        mBgLinePaint = new Paint();
        mBgLinePaint.setColor(mBackLineColor);
        mBgLinePaint.setStrokeWidth(2);
        mBgLinePaint.setStyle(Paint.Style.FILL);


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

        initPaint();

    }

    /**
     * 初始化样式，放到xml中的样式
     *
     * @param array
     */
    protected abstract void initStyle(TypedArray array);

    /**
     * 初始化画笔
     */
    protected abstract void initPaint();

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        mWidth = measureWidth(widthMeasureSpec);
        mHeight = measureHeight(heightMeasureSpec);
        //获取文字的高度
        float fontTotalHeight = ChartCalUtils.getTextHeight(mXAxisTextPaint);

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
        mLeftTextWidth = ChartCalUtils.getMaxTextWidth(mChartData.getYLeftAxisString(), mYAxisTextPaint);
        mRightTextWidth = ChartCalUtils.getMaxTextWidth(mChartData.getYRightAxisString(), mYAxisTextPaint);
        if (mChartData != null) {
//            mFirstRects = new RectModel[mChartData.mFirstBarValues.length];
            beforeDraw();

            //画出y轴
            //画出左边的y轴
            //每个坐标相距的距离
            float yItemHight = mHeight / (mChartData.getYLeftAxisString().size() - 1);

            //画两个y轴的线
            canvas.drawLine(mLeftTextWidth + textLineSpace, 0, mLeftTextWidth + textLineSpace, mHeight + paddingTop, mAxisPaint);
            canvas.drawLine(mWidth - mRightTextWidth - textLineSpace, 0, mWidth - mRightTextWidth - textLineSpace, mHeight + paddingTop, mAxisPaint);


            mAxisPaint.setTextAlign(Paint.Align.CENTER);
            float yItemAxis = mHeight;
            for (int i = 0; i < mChartData.getYLeftAxisString().size(); i++) {
                //画出x轴平行的线
                canvas.drawLine(mLeftTextWidth + textLineSpace, paddingTop + yItemAxis, mWidth - mRightTextWidth - textLineSpace, paddingTop + yItemAxis, mAxisPaint);
                canvas.drawText(mChartData.getYLeftAxisString().get(i), 0, paddingTop + yItemAxis, mYAxisTextPaint);
                canvas.drawText(mChartData.getYRightAxisString().get(i), mWidth - mRightTextWidth, paddingTop + yItemAxis, mYAxisTextPaint);
                yItemAxis -= yItemHight;
            }


            //每个坐标相距的距离
            float xItemL = (mWidth - mLeftTextWidth - mRightTextWidth) / (mChartData.getXAxisString().size() + 1);
            mAxisPaint.setTextAlign(Paint.Align.CENTER);
            float xItemAxis = mLeftTextWidth + xItemL;
            //获取文字的高度
            Paint.FontMetrics fontMetrics = mAxisPaint.getFontMetrics();
            float fontTotalHeight = fontMetrics.bottom - fontMetrics.top;
            for (int i = 0; i < mChartData.getXAxisString().size(); i++) {
//                canvas.drawText(mChartData.getXAxisString().get(i), xItemAxis - mRectWidth / 2, mHeight + paddingTop + fontTotalHeight + textLineSpace, mXAxisTextPaint);
//                mFirstRects[i] = new RectModel(xItemAxis - mRectWidth / 2,
//                        paddingTop + mHeight - ChartCalUtils.transValueToHeight(mChartData.mFirstBarValues[i]
//                                , Float.valueOf(mChartData.yLeftAxisString.get(0)),
//                                Float.valueOf(mChartData.yLeftAxisString.get(mChartData.yLeftAxisString.size() - 1)), mHeight), xItemAxis + mRectWidth / 2, paddingTop + mHeight, mChartData.mFirstBarValues[i]);
//                //画第一个柱子
//                mFirstRects[i].draw(canvas, mFirstRectPaint);
                drawPerX(xItemAxis, xItemL, i, canvas);
                xItemAxis += xItemL;
            }


        } else {
            canvas.drawText("无数据", mWidth / 2, mHeight / 2, mAxisPaint);
        }
    }

    /**
     * 画每个X轴中的内容，包括X轴的字和X轴上的数据实体
     *
     * @param xItemAxis
     * @param xItemL
     * @param index
     * @param canvas
     */
    protected abstract void drawPerX(float xItemAxis, float xItemL, int index, Canvas canvas);

    /**
     * 在画图之前的操作
     */
    protected abstract void beforeDraw();

    @Override
    public IData getData() {
        return mChartData;
    }

    @Override
    public void setData(IData data) {
        if (data instanceof BarAndLineChartData)
            mChartData = (BarAndLineChartData) data;
    }
}
