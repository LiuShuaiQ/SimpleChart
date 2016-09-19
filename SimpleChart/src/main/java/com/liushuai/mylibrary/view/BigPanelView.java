package com.liushuai.mylibrary.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.liushuai.mylibrary.R;


/**
 * 大仪表盘 视图
 * Created by LiuShuai on 2016/8/17.
 */
public class BigPanelView extends View {
    /**
     * 默认的宽和高
     */
    public static final int DEFAULT_WIDTH = 300;
    public static final int DEFAULT_HEIGHT = 150;

    /**
     * 指针的默认颜色
     */
    public static final int DEFAULT_POINT_COLOR = Color.parseColor("#313131");
    private int mWidth;
    private int mHeight;
    /**
     * 选择的弧度
     */
    private float mSelectPercent = 0;
    /**
     * 一共的弧度
     */
    private float mMaxPercent = 180;
    /**
     * 里面小圆的半径
     */
    private int mMinCircleRadius = 30;

    /**
     * 指针的颜色
     */
    private int mPointerColor;

    //弧的宽度
    private int mArcWidth;
    //弧的颜色
    private int mArcColor;

    private Context mContext;

    private Paint mBackgroundCirclePaint;
    private Paint mSelectCirclePaint;
    private Paint mPointPaint;


    public BigPanelView(Context context) {
        this(context, null);
    }

    public BigPanelView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BigPanelView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.PanelView, defStyleAttr, 0);
        mArcColor = a.getColor(R.styleable.PanelView_arcColor, Color.parseColor("#5FB1ED"));
        mArcWidth = a.getDimensionPixelSize(R.styleable.PanelView_arcWidth, 73);
        mPointerColor = a.getColor(R.styleable.PanelView_pointerColor, DEFAULT_POINT_COLOR);
        mMinCircleRadius = a.getDimensionPixelSize(R.styleable.PanelView_pointerRadius, 30);
        mMaxPercent = a.getFloat(R.styleable.PanelView_maxPercent, 180f);
        mSelectPercent = a.getFloat(R.styleable.PanelView_selectPercent, 0f);
        mSelectPercent = mSelectPercent / mMaxPercent * 180;
        a.recycle();

        mBackgroundCirclePaint = new Paint();
        mSelectCirclePaint = new Paint();
        mPointPaint = new Paint();
        //设置背景弧的画笔
        mBackgroundCirclePaint.setStrokeWidth(mArcWidth);
        mBackgroundCirclePaint.setAntiAlias(true);
        mBackgroundCirclePaint.setStyle(Paint.Style.STROKE);
        mBackgroundCirclePaint.setColor(Color.parseColor("#C0C0C0"));
        //设置选择弧的画笔
        mSelectCirclePaint.setStrokeWidth(mArcWidth);
        mSelectCirclePaint.setAntiAlias(true);
        mSelectCirclePaint.setStyle(Paint.Style.STROKE);
        mSelectCirclePaint.setColor(mArcColor);
        //设置指针绘制的画笔
        mPointPaint.setStyle(Paint.Style.FILL);
        mPointPaint.setStrokeWidth(5);
        mPointPaint.setAntiAlias(true);
        mPointPaint.setColor(mPointerColor);
    }

    public BigPanelView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        this(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);

        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        if (widthMode == MeasureSpec.EXACTLY) {
            if (heightMode == MeasureSpec.EXACTLY) {
                if (widthSize != 2 * heightSize) {
                    mWidth = widthSize;
                    mHeight = mWidth / 2;
                } else {
                    mWidth = widthSize;
                    mHeight = heightSize;
                }

            } else {
                mWidth = widthSize;
                mHeight = mWidth / 2;

            }
        } else {
            if (heightMode == MeasureSpec.EXACTLY) {
                mHeight = heightSize;
                mWidth = 2 * mHeight;
            } else {
                mWidth = DEFAULT_WIDTH;
                mHeight = DEFAULT_HEIGHT;
            }
        }

        //注意在最后计算宽高的时候，高度要再加上底部的弧的高度
        setMeasuredDimension(mWidth, mHeight + mHeight / 2);
        mHeight = 2 * mHeight;

    }

    private float mOldPercent = 0;

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawArc(new RectF(mArcWidth, mArcWidth, mWidth - mArcWidth, mHeight - mArcWidth), 150, 240, false, mBackgroundCirclePaint);
        canvas.drawArc(new RectF(mArcWidth, mArcWidth, mWidth - mArcWidth, mHeight - mArcWidth), 180, mSelectPercent, false, mSelectCirclePaint);

        //画中间的小圆圈
        canvas.drawCircle(mWidth / 2, mHeight / 2, mMinCircleRadius, mPointPaint);

        //画指针
        //选择画布角度
        canvas.rotate(mSelectPercent, mWidth / 2, mHeight / 2);
        Path path = new Path();
        path.moveTo(mArcWidth / 2, mHeight / 2);
        path.lineTo(mWidth / 2, mHeight / 2 + mMinCircleRadius);
        path.lineTo(mWidth / 2, mHeight / 2 - mMinCircleRadius);
        path.close();
        canvas.drawPath(path, mPointPaint);

        //再旋转回来
        canvas.rotate(-1 * mSelectPercent, mWidth / 2, mHeight / 2);
        mOldPercent = mSelectPercent;
    }

    public void setWidth(int width) {
        mWidth = width;
    }

    public void setArcColor(int arcColor) {
        mArcColor = arcColor;
    }

    public void setArcWidth(int arcWidth) {
        mArcWidth = arcWidth;
    }

    public void setPointerColor(int pointerColor) {
        mPointerColor = pointerColor;
    }

    public void setMinCircleRadius(int minCircleRadius) {
        mMinCircleRadius = minCircleRadius;
    }

    public void setMaxPercent(float maxPercent) {
        mMaxPercent = maxPercent;
    }

    public void setSelectPercent(float selectPercent) {
        mSelectPercent = selectPercent;
        mSelectPercent = mSelectPercent / mMaxPercent * 180;
    }

    public void setHeight(int height) {
        mHeight = height;
    }

    public static int getDefaultWidth() {
        return DEFAULT_WIDTH;
    }

    public static int getDefaultHeight() {
        return DEFAULT_HEIGHT;
    }


    public float getSelectPercent() {
        return mSelectPercent;
    }

    public float getMaxPercent() {
        return mMaxPercent;
    }

    public int getMinCircleRadius() {
        return mMinCircleRadius;
    }

    public int getPointerColor() {
        return mPointerColor;
    }

    public int getArcWidth() {
        return mArcWidth;
    }

    public int getArcColor() {
        return mArcColor;
    }

}
