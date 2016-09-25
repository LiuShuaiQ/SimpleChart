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
import com.liushuai.mylibrary.formatter.AxisFormatter;
import com.liushuai.mylibrary.utils.ChartCalUtils;

/**
 * Base Class Of the BarChart and LineChart
 * (柱状图和折线图的基类)
 * Created by LiuShuai on 2016/9/12.
 */
public abstract class BaseBarAndLineChart extends BaseChart {

    /**
     * the number of y-axis
     * (y轴坐标的个数)
     */
    protected int yCount;
    /**
     * the number of x-axis
     * (x轴坐标的个数)
     */
    protected int xCount;

    /**
     * the text size of the x-axis text
     * (x轴文字的大小)
     */
    protected int mXTextSize;
    /**
     * the text size of the y-axis text
     * (y轴文字的大小)
     */
    protected int mYTextSize;

    /**
     * the color of the background line
     * (背景线的颜色)
     */
    protected int mBackLineColor;

    /**
     * x-axis enable
     * (X轴是否显示)
     */
    protected boolean mXAxisEnable = true;
    /**
     * left y-axis enable
     * (左边Y轴是否显示)
     */
    protected boolean mLeftYAxisEnable = true;

    /**
     * right y-axis enable
     * (右边Y轴是否显示)
     */
    protected boolean mRightYAxisEnable = true;


    /**
     * the color of axis
     * (坐标轴的颜色)
     */
    protected int mAxisColor;

    /**
     * background line enable
     * (是否画背景线)
     */
    protected boolean mBackLineEnable = true;

    /**
     * rotate x-text enable
     * (是否旋转x轴的文字)
     */
    private boolean mRotateXText = false;

    /**
     * the color of axis-text
     * (坐标轴文字的颜色)
     */
    protected int mAxisTextColor;

    /**
     * the space between word and line
     * (字和线之间的间距)
     */
    protected int textLineSpace = 5;

    /**
     * the padding of top,depend on the the text size of axis
     * (距离上方的间距,由字体大小决定)
     */
    protected float paddingTop = 20;
    /**
     * the padding of bottom,depend on the the text size of axis
     * (距离下方的间距,由字体大小决定)
     */
    protected float paddingBottom = 20;
    /**
     * the padding of left,depend on the the text size of axis
     * (距离左方的间距,由字体大小决定)
     */
    protected float paddingLeft = 20;
    /**
     * the padding of right,depend on the the text size of axis
     * (距离右方的间距,由字体大小决定)
     */
    protected float paddingRight = 20;

    protected Paint mAxisPaint;
    protected Paint mBgLinePaint;
    protected Paint mYAxisTextPaint;
    protected Paint mXAxisTextPaint;

    protected float mLeftTextWidth;
    protected float mRightTextWidth;

    private AxisFormatter mYLeftValueAxisFormatter;
    private AxisFormatter mYRightValueAxisFormatter;
    private AxisFormatter mXValueAxisFormatter;

    protected BarAndLineChartData mChartData;

    public BaseBarAndLineChart(Context context) {
        super(context);
    }

    public BaseBarAndLineChart(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public BaseBarAndLineChart(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * @param context
     * @param attrs
     * @param defStyleAttr
     */
    @Override
    protected void init(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.MyChartView, defStyleAttr, 0);

        mXTextSize = a.getDimensionPixelSize(R.styleable.MyChartView_xTextSize, 20);
        mYTextSize = a.getDimensionPixelSize(R.styleable.MyChartView_yTextSize, 20);
        mBackLineColor = a.getColor(R.styleable.MyChartView_backLineColor, Color.GRAY);
        mBackLineEnable = a.getBoolean(R.styleable.MyChartView_backLineEnable, true);
        mXAxisEnable = a.getBoolean(R.styleable.MyChartView_xEnable, true);
        mLeftYAxisEnable = a.getBoolean(R.styleable.MyChartView_leftYEnable, true);
        mRightYAxisEnable = a.getBoolean(R.styleable.MyChartView_rightYEnable, true);
        mAxisColor = a.getColor(R.styleable.MyChartView_axisColor, Color.BLACK);
        mAxisTextColor = a.getColor(R.styleable.MyChartView_axisTextColor, Color.BLACK);

        initStyle(a);

        a.recycle();

        mAxisPaint = new Paint();
        mBgLinePaint = new Paint();
        mYAxisTextPaint = new Paint();
        mXAxisTextPaint = new Paint();
        setPaint();

        //init the custom paint
        initPaint();

    }

    private void setPaint() {
        mAxisPaint.setColor(mAxisColor);
        mAxisPaint.setStrokeWidth(2);
        mAxisPaint.setStyle(Paint.Style.FILL);

        mBgLinePaint.setColor(mBackLineColor);
        mBgLinePaint.setStrokeWidth(2);
        mBgLinePaint.setStyle(Paint.Style.FILL);

        mYAxisTextPaint.setColor(mAxisTextColor);
        mYAxisTextPaint.setStrokeWidth(5);
        mYAxisTextPaint.setTextSize(mYTextSize);
        mYAxisTextPaint.setAntiAlias(true);
        mYAxisTextPaint.setStyle(Paint.Style.FILL);

        mXAxisTextPaint.setColor(mAxisTextColor);
        mXAxisTextPaint.setAntiAlias(true);
        mXAxisTextPaint.setStrokeWidth(5);
        mXAxisTextPaint.setTextSize(mXTextSize);
        mXAxisTextPaint.setStyle(Paint.Style.FILL);
    }

    /**
     * init style in xml file
     *
     * @param array
     */
    protected abstract void initStyle(TypedArray array);

    /**
     * init paint
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

    @Override
    protected void onDraw(Canvas canvas) {
        setPaint();

        //calculate left and right axis text width   (计算两端的文字宽度)
        mLeftTextWidth = ChartCalUtils.getMaxTextWidth(mChartData.getYLeftAxisString(), mYAxisTextPaint, mYLeftValueAxisFormatter);
        mRightTextWidth = ChartCalUtils.getMaxTextWidth(mChartData.getYRightAxisString(), mYAxisTextPaint, mYRightValueAxisFormatter);
        if (mChartData != null) {
            //before draw callback
            beforeDraw();

            //calculate the distance of the y-axis text     (y轴每个坐标相距的距离)
            float yItemHight = mHeight / (mChartData.getYLeftAxisString().size() - 1);

            //draw y axis line  (画两个y轴的线)
            if (mLeftYAxisEnable) {
                canvas.drawLine(mLeftTextWidth + textLineSpace, 0, mLeftTextWidth + textLineSpace, mHeight + paddingTop, mAxisPaint);
            }
            if (mRightYAxisEnable) {
                canvas.drawLine(mWidth - mRightTextWidth - textLineSpace, 0, mWidth - mRightTextWidth - textLineSpace, mHeight + paddingTop, mAxisPaint);
            }


            mAxisPaint.setTextAlign(Paint.Align.CENTER);
            float yItemAxis = mHeight;
            String yLeftText;
            String yRightText;
            for (int i = 0; i < mChartData.getYLeftAxisString().size(); i++) {
                yLeftText = mChartData.getYLeftAxisString().get(i);
                yRightText = mChartData.getYLeftAxisString().get(i);
                if (mYLeftValueAxisFormatter != null) {
                    yLeftText = mYLeftValueAxisFormatter.format(yLeftText);
                }
                if (mYRightValueAxisFormatter != null){
                    yRightText = mYRightValueAxisFormatter.format(yRightText);
                }
                if (mBackLineEnable) {
                    //draw the line of parallel to the x    (画出x轴平行的线)
                    canvas.drawLine(mLeftTextWidth + textLineSpace,
                            paddingTop + yItemAxis,
                            mWidth - mRightTextWidth - textLineSpace,
                            paddingTop + yItemAxis, mBgLinePaint);
                }
                //draw y-axis text
                if (mLeftYAxisEnable) {
                    canvas.drawText(yLeftText, 0, paddingTop + yItemAxis, mYAxisTextPaint);
                }
                if (mRightYAxisEnable) {
                    canvas.drawText(yRightText, mWidth - mRightTextWidth, paddingTop + yItemAxis, mYAxisTextPaint);
                }
                yItemAxis -= yItemHight;
            }


            //the distance of the x-axis text    (x轴每个坐标相距的距离)
            float xItemL = (mWidth - mLeftTextWidth - mRightTextWidth) / (mChartData.getXAxisString().size() + 1);
            mAxisPaint.setTextAlign(Paint.Align.CENTER);
            float xItemAxis = mLeftTextWidth + xItemL;
            String xValue;
            for (int i = 0; i < mChartData.getXAxisString().size(); i++) {
                if (mXAxisEnable) {
                    xValue = mChartData.getXAxisString().get(i);
                    if (mXValueAxisFormatter != null) {
                        xValue = mXValueAxisFormatter.format(xValue);
                    }
                    //draw the text of x-axis , if mRotateXText is true ,the rotate will be retate
                    if (mRotateXText) {
                        //draw x-axis text after canvas rotate
                        canvas.save();
                        canvas.rotate(-45,
                                xItemAxis,
                                mHeight + paddingTop + textLineSpace + ChartCalUtils.getTextHeight(mAxisPaint)
                                        + (float) Math.sqrt(ChartCalUtils.getTextWidth(mChartData.getXAxisString().get(i), mAxisPaint, mXValueAxisFormatter) / 2));
                        canvas.drawText(xValue,
                                xItemAxis - ChartCalUtils.getTextWidth(mChartData.getXAxisString().get(i), mAxisPaint, mXValueAxisFormatter) / 2,
                                mHeight + paddingTop + textLineSpace + ChartCalUtils.getTextHeight(mAxisPaint) + (float) Math.sqrt(ChartCalUtils.getTextWidth(mChartData.getXAxisString().get(i), mAxisPaint) / 2),
                                mXAxisTextPaint);
                        canvas.restore();
                    } else {
                        //draw x-axis text that canvas is not rotate
                        canvas.drawText(xValue,
                                xItemAxis - ChartCalUtils.getTextWidth(mChartData.getXAxisString().get(i), mAxisPaint, mXValueAxisFormatter) / 2,
                                mHeight + paddingTop + textLineSpace + ChartCalUtils.getTextHeight(mAxisPaint),
                                mXAxisTextPaint);
                    }
                }

                //draw the chart callback
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

    public boolean isRotateXText() {
        return mRotateXText;
    }

    public void setRotateXText(boolean rotateXText) {
        mRotateXText = rotateXText;
    }

    public int getBackLineColor() {
        return mBackLineColor;
    }

    public void setBackLineColor(int backLineColor) {
        mBackLineColor = backLineColor;
    }

    public boolean isXAxisEnable() {
        return mXAxisEnable;
    }

    public void setXAxisEnable(boolean XAxisEnable) {
        mXAxisEnable = XAxisEnable;
    }

    public boolean isLeftYAxisEnable() {
        return mLeftYAxisEnable;
    }

    public void setLeftYAxisEnable(boolean leftYAxisEnable) {
        mLeftYAxisEnable = leftYAxisEnable;
    }

    public boolean isRightYAxisEnable() {
        return mRightYAxisEnable;
    }

    public void setRightYAxisEnable(boolean rightYAxisEnable) {
        mRightYAxisEnable = rightYAxisEnable;
    }

    public int getAxisColor() {
        return mAxisColor;
    }

    public void setAxisColor(int axisColor) {
        mAxisColor = axisColor;
    }

    public boolean isBackLineEnable() {
        return mBackLineEnable;
    }

    public void setBackLineEnable(boolean backLineEnable) {
        mBackLineEnable = backLineEnable;
    }

    public int getAxisTextColor() {
        return mAxisTextColor;
    }

    public void setAxisTextColor(int axisTextColor) {
        mAxisTextColor = axisTextColor;
    }

    public int getTextLineSpace() {
        return textLineSpace;
    }

    public void setTextLineSpace(int textLineSpace) {
        this.textLineSpace = textLineSpace;
    }

    public int getYTextSize() {
        return mYTextSize;
    }

    public void setYTextSize(int YTextSize) {
        mYTextSize = YTextSize;
    }

    public int getXTextSize() {
        return mXTextSize;
    }

    public void setXTextSize(int XTextSize) {
        mXTextSize = XTextSize;
    }

    public AxisFormatter getYLeftValueAxisFormatter() {
        return mYLeftValueAxisFormatter;
    }

    public void setYLeftValueAxisFormatter(AxisFormatter YLeftValueAxisFormatter) {
        mYLeftValueAxisFormatter = YLeftValueAxisFormatter;
    }

    public AxisFormatter getYRightValueAxisFormatter() {
        return mYRightValueAxisFormatter;
    }

    public void setYRightValueAxisFormatter(AxisFormatter YRightValueAxisFormatter) {
        mYRightValueAxisFormatter = YRightValueAxisFormatter;
    }

    public AxisFormatter getXValueAxisFormatter() {
        return mXValueAxisFormatter;
    }

    public void setXValueAxisFormatter(AxisFormatter XValueAxisFormatter) {
        mXValueAxisFormatter = XValueAxisFormatter;
    }
}
