package com.liushuai.mylibrary.model;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

/**
 * 正方形实体
 * Created by LiuShuai on 2016/8/11.
 */
public class RectModel {
    private float mLeftTopX;   //左上角的x坐标
    private float mLeftTopY;   //左上角的y坐标
    private float mRightBottomX;   //右下角的x坐标
    private float mRightBottomY;   //右下角的y坐标

    private boolean isTouched = false;  //是否被接触
    private float mValue;   //此部分中的值


    public RectModel(float leftTopX, float leftTopY, float rightBottomX, float rightBottomY) {
        this(leftTopX, leftTopY, rightBottomX, rightBottomY, 0);
    }

    public RectModel(float leftTopX, float leftTopY, float rightBottomX, float rightBottomY, float value) {
        mLeftTopX = leftTopX;
        mLeftTopY = leftTopY;
        mRightBottomX = rightBottomX;
        mRightBottomY = rightBottomY;
        mValue = value;
    }

    /**
     * x,y坐标是否在这个矩形内
     *
     * @param x
     * @param y
     * @return
     */
    public boolean isPointIn(float x, float y) {
        boolean result = false;
        if (x > mLeftTopX && x < mRightBottomX && y > mLeftTopY && y < mRightBottomY) {
            result = true;
        }
        return result;
    }

    /**
     * 返回RectF
     *
     * @return
     */
    public RectF toRectF() {
        return new RectF(mLeftTopX, mLeftTopY, mRightBottomX, mRightBottomY);
    }

    /**
     * 画出这个图形
     *
     * @param canvas
     * @param paint
     */
    public void draw(Canvas canvas, Paint paint) {
        if (canvas != null)
            canvas.drawRect(this.toRectF(), paint);
    }


    /**
     * 获得这个矩形的宽度
     *
     * @return 矩形的宽度
     */
    public float getWidth() {
        return mRightBottomX - mLeftTopX;
    }

    /**
     * 获得这个矩形的高度
     *
     * @return 矩形的高度
     */
    public float getLength() {
        return mRightBottomY - mLeftTopY;
    }

    public float getLeftTopX() {
        return mLeftTopX;
    }

    public float getLeftTopY() {
        return mLeftTopY;
    }

    public float getRightBottomX() {
        return mRightBottomX;
    }

    public float getRightBottomY() {
        return mRightBottomY;
    }

    public boolean isTouched() {
        return isTouched;
    }

    public float getValue() {
        return mValue;
    }

    public void setLeftTopX(float leftTopX) {
        mLeftTopX = leftTopX;
    }

    public void setLeftTopY(float leftTopY) {
        mLeftTopY = leftTopY;
    }

    public void setRightBottomX(float rightBottomX) {
        mRightBottomX = rightBottomX;
    }

    public void setRightBottomY(float rightBottomY) {
        mRightBottomY = rightBottomY;
    }

    public void setTouched(boolean touched) {
        isTouched = touched;
    }

    public void setValue(float value) {
        mValue = value;
    }

}
