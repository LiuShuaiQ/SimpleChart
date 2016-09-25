package com.liushuai.mylibrary.formatter;

/**
 * Created by LiuShuai on 2016/9/25.
 */

public interface ValueFormatter {
    /**
     * 格式化值
     * @param index
     * @param formatString
     * @return
     */
    String format(int index,float formatString);
}
