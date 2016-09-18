package com.liushuai.mylibrary.data;

/**
 * 图中的实体
 * Created by LiuShuai on 2016/9/13.
 */
public class Entity implements IEntity {
    private int index;
    private float values;

    public Entity() {
    }

    public Entity(int index, float values) {
        this.index = index;
        this.values = values;
    }

    @Override
    public void setIndex(int index) {
        this.index = index;
    }

    @Override
    public void setValues(float values) {
        this.values = values;
    }

    @Override
    public int getIndex() {
        return index;
    }

    @Override
    public float getValues() {
        return values;
    }
}
