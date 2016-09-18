package com.liushuai.mylibrary.data;

import android.graphics.Color;

import java.util.List;

/**
 * 图表数据接口
 * Created by LiuShuai on 2016/9/12.
 */
public interface IData {

    List<String> getXAxisString();

    void setXAxisString(List<String> xString);

    /**
     * 返回值的类型决定了有几个共同的属性
     *
     * @return
     */
    List<List<IEntity>> getEntity();

    void setEntity(List<List<IEntity>> entity);

    /**
     * 获得颜色
     *
     * @return
     */
    int[] getColors();

    /**
     * 设置颜色
     */
    void setColors(int[] cs);

}
