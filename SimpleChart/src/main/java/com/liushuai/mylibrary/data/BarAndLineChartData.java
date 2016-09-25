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

//    private ValueFormatter mValueFormatter;

    /**
     * Y轴坐标显示的个数，默认为5个
     */
    private int mYAxisCount = 5;
    private int[] mColors;

    public BarAndLineChartData(List<String> XAxisString, List<List<IEntity>> entities, int[] colors) {
        super(XAxisString, entities);
        mColors = colors;
        mYLeftAxisString = new ArrayList<>(5);
        mYRightAxisString = new ArrayList<>(5);
        initYAxisString();
    }

    public BarAndLineChartData(List<String> XAxisString, float[][] fs, int[] colors) {
        super(XAxisString, fs);
        mColors = colors;
        mYLeftAxisString = new ArrayList<>(5);
        mYRightAxisString = new ArrayList<>(5);
        initYAxisString();
    }

    /**
     * 初始化Y轴的数据<br/>
     * <p>
     * 调用完这个之后你应该调用更新函数：refreshYText()
     */
    private void initYAxisString() {
        float maxValues = Float.MIN_VALUE;
        if (getEntity() != null) {
            for (int i = 0; i < getEntity().size(); i++) {
                for (int j = 0; j < getEntity().get(i).size(); j++) {
                    if (getEntity().get(i).get(j).getValues() > maxValues) {
                        maxValues = getEntity().get(i).get(j).getValues();
                    }
                }

            }
            int increaseL = (int) Math.ceil(maxValues / (mYAxisCount - 1));
            int yValue = 0;
            for (int i = 0; i < mYAxisCount; i++) {
//                if (mYAxisTextSetter != null) {
//                    yValue = mYAxisTextSetter.setYValues(i, yValue);
//                    mYLeftAxisString.add(mYAxisTextSetter.setLeftYAxisText(yValue));
//                    mYRightAxisString.add(mYAxisTextSetter.setRightYAxisText(yValue));
//                } else {
                    mYLeftAxisString.add(String.valueOf(yValue));
                    mYRightAxisString.add(String.valueOf(yValue));
//                }

                yValue += increaseL;
            }
        }
    }

    /**
     * 更新Y轴的数据
     */
    public void refreshYText() {
        initYAxisString();
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

    @Override
    public int[] getColors() {
        return mColors;
    }

    @Override
    public void setColors(int[] colors) {
        mColors = colors;
    }


}

