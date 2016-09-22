package com.liushuai.mylibrary.data;

import java.util.List;

/**
 * the data of the PieChart
 * Created by LiuShuai on 2016/9/19.
 */
public class PieChartData extends Data {

    /**
     * the color of per item
     */
    private int[] colors;

    public PieChartData(List<String> XAxisString, List<List<IEntity>> entities, int[] cs) {
        super(XAxisString, entities);
        colors = cs;
    }

    public PieChartData(List<String> XAxisString, float[][] fs, int[] cs) {
        super(XAxisString, fs);
        colors = cs;
    }

    @Override
    public int[] getColors() {
        return colors;
    }

    @Override
    public void setColors(int[] cs) {
        colors = cs;
    }
}
