package com.liushuai.mylibrary.data;

import java.util.ArrayList;
import java.util.List;

/**
 * 柱状图和折线图数据\n
 * 该类完成了将Y轴的数据计算出来
 * Created by LiuShuai on 2016/9/12.
 */
public class BarAndLineChartData extends Data {

    /**
     * 左边y轴显示的内容
     */
    private List<String> mYLeftAxisString;

    /**
     * 左边y轴显示的内容
     */
    private List<String> mYRightAxisString;

    /**
     * Y轴坐标显示的个数，默认为5个
     */
    private int mYAxisCount = 5;

    public BarAndLineChartData(List<String> XAxisString, List<List<IEntity>> entities) {
        super(XAxisString, entities);
        mYLeftAxisString = new ArrayList<>(5);
        mYRightAxisString = new ArrayList<>(5);
        initYAxisString();
    }

    public BarAndLineChartData(List<String> XAxisString, float[][] fs) {
        super(XAxisString, fs);
        mYLeftAxisString = new ArrayList<>(5);
        mYRightAxisString = new ArrayList<>(5);
        initYAxisString();
    }

    /**
     * 初始化Y轴的数据
     */
    private void initYAxisString() {
        float maxValues = Float.MIN_VALUE;
        if (getEntity() != null) {
            for (int i = 0; i < getEntity().size(); i++) {
                if (getEntity().get(i).getValues() > maxValues) {
                    maxValues = getEntity().get(i).getValues();
                }
            }
            int increaseL = (int) Math.ceil(maxValues / (mYAxisCount - 1));
            int yValue = 0;
            for (int i = 0; i < mYAxisCount; i++) {
                mYLeftAxisString.add(String.valueOf(yValue));
                mYRightAxisString.add(String.valueOf(yValue));
                yValue += increaseL;
            }
        }
    }


    public List<String> getYLeftAxisString() {
        return mYLeftAxisString;
    }

    public void setYLeftAxisString(List<String> YLeftAxisString) {
        mYLeftAxisString = YLeftAxisString;
    }

    public List<String> getYRightAxisString() {
        return mYRightAxisString;
    }

    public void setYRightAxisString(List<String> YRightAxisString) {
        mYRightAxisString = YRightAxisString;
    }


}
