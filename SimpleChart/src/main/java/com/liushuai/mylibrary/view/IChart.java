package com.liushuai.mylibrary.view;


import com.liushuai.mylibrary.data.IData;

/**
 * 图标接口
 * Created by LiuShuai on 2016/9/12.
 */
public interface IChart {
    int getChartWidth();

    int getChartHeight();

    IData getData();

    void setData(IData data);
}
