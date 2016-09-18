package com.liushuai.mylibrary.utils;

import android.graphics.Paint;

import java.util.List;

/**
 * 对于图标计算的实用类
 * Created by LiuShuai on 2016/9/12.
 */
public class ChartCalUtils {

    /**
     * 将数据转化为高度
     *
     * @return
     */
    public static float transValueToHeight(float v, float minV, float maxV, float sumHeight) {
        return v / (maxV - minV) * sumHeight;
    }

    /**
     * 计算几个文字的最大宽度
     *
     * @param strings
     * @param paint
     * @return
     */
    public static float getMaxTextWidth(List<String> strings, Paint paint) {
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

    /**
     * 获取文字的高度
     *
     * @param paint
     * @return
     */
    public static float getTextHeight(Paint paint) {
        //获取文字的高度
        Paint.FontMetrics fontMetrics = paint.getFontMetrics();
        float fontTotalHeight = fontMetrics.bottom - fontMetrics.top;
        return fontTotalHeight;
    }
}
