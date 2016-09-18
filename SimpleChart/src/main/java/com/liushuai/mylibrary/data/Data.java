package com.liushuai.mylibrary.data;

import android.graphics.Color;

import java.util.ArrayList;
import java.util.List;

/**
 * 数据抽象类
 * Created by LiuShuai on 2016/9/12.
 */
public abstract class Data implements IData {
    /**
     * x轴显示的东西
     */
    private List<String> mXAxisString;
    /**
     * 图表中的实体
     */
    private List<List<IEntity>> mEntities;

    private int[] mColors;

    public Data(List<String> XAxisString, List<List<IEntity>> entities,int[] colors) {
        mXAxisString = XAxisString;
        mEntities = entities;
        mColors = colors;
    }

    public Data(List<String> XAxisString, float[][] fs,int[] colors) {
        mXAxisString = XAxisString;
        mEntities = toEntity(fs);
        mColors = colors;
    }

    @Override
    public List<String> getXAxisString() {
        return mXAxisString;
    }

    @Override
    public void setXAxisString(List<String> xString) {
        mXAxisString = xString;
    }

    @Override
    public List<List<IEntity>> getEntity() {
        return mEntities;
    }

    @Override
    public void setEntity(List<List<IEntity>> entity) {
        mEntities = entity;
    }

    public float[][] getValues() {
        float[][] vs = new float[getEntity().size()][];

        for (int i = 0; i < vs.length; i++) {
            vs[i] = new float[getEntity().get(i).size()];
            for (int j = 0; j < getEntity().get(i).size(); j++) {
                vs[i][j] = getEntity().get(i).get(j).getValues();
            }
        }
        return vs;
    }

    public void setValues(float[][] values) {
        setEntity(toEntity(values));
    }

    /**
     * 将数组的值转化为Entity的List
     *
     * @param values
     * @return
     */
    protected List<List<IEntity>> toEntity(float[][] values) {
        List<List<IEntity>> entities = new ArrayList<>();
        List<IEntity> temp = null;
        for (int i = 0; i < values.length; i++) {
            temp = new ArrayList<>();
            for (int j = 0; j < values[i].length; j++) {
                temp.add(new Entity(j, values[i][j]));
            }
            entities.add(temp);
        }
        return entities;
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
