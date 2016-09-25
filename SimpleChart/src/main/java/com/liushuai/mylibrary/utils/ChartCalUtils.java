package com.liushuai.mylibrary.utils;

import android.graphics.Paint;

import com.liushuai.mylibrary.formatter.AxisFormatter;

import java.util.ArrayList;
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
     * @param strings
     * @param paint
     * @return
     */
    public static float getMaxTextWidth(List<String> strings, Paint paint){
        return  getMaxTextWidth(strings,paint,null);
    }

    /**
     * 计算几个文字的最大宽度
     *
     * @param strings
     * @param paint
     * @param axisFormatter
     * @return
     */
    public static float getMaxTextWidth(List<String> strings, Paint paint, AxisFormatter axisFormatter) {
        List<String> strings1 = new ArrayList<>(strings.size());
        if (axisFormatter != null){
            for (int i=0;i<strings.size();i++){
                strings1.add(i, axisFormatter.format(strings.get(i)));
            }
        }else {
            strings1 = strings;
        }
        float sumTextWidth = 0;
        if (strings1 != null && strings1.size() > 0) {
            int yMaxLength = 0;
            int yMaxIndex = 0;
            for (int i = 0; i < strings1.size(); i++) {
                if (strings1.get(i).length() > yMaxLength) {
                    yMaxIndex = i;
                    yMaxLength = strings1.get(i).length();
                }
            }
            float[] yWidthFloats = new float[strings1.get(yMaxIndex).length()];
            paint.getTextWidths(strings1.get(yMaxIndex), 0, yMaxLength, yWidthFloats);
            for (float f : yWidthFloats) {
                sumTextWidth += f;
            }
        }

        return sumTextWidth;
    }

    /**
     * 获取文字的宽度
     * @param text
     * @param paint
     * @return
     */
    public static float getTextWidth(String text, Paint paint){
        return getTextWidth(text,paint,null);
    }

    /**
     * 获取文字的宽度
     *
     * @param text
     * @param paint
     * @param axisFormatter
     * @return
     */
    public static float getTextWidth(String text, Paint paint, AxisFormatter axisFormatter) {
        String text1;
        if (axisFormatter !=null){
            text1 = axisFormatter.format(text);
        }else {
            text1 = text;
        }
        float result = 0;
        float[] ws = new float[text1.length()];
        paint.getTextWidths(text1, ws);
        for (float f : ws) {
            result += f;
        }
        return result;
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
