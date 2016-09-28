package com.liushuai.mylibrary.listener;

import com.liushuai.mylibrary.data.Entity;
import com.liushuai.mylibrary.data.IEntity;

/**
 * 柱状图中的柱子数据点击事件
 * Created by LiuShuai on 2016/9/28.
 */

public interface OnValueClickListener {
    /**
     * @param index  在哪个柱子下
     * @param entity 柱子的数据
     */
    void onBarValueClick(int index, IEntity entity);
}
