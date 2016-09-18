package com.liushuai.myapplication.utils;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

/**
 * 封装Toast的工具类
 * Created by LiuShuai on 2016/4/5.
 */
public class ToastUtils {
    /**
     * 普通Toast显示-短时间显示
     *
     * @param context
     * @param text
     */
    public static void showShortToast(Context context, String text) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }

    /**
     * 普通Toast显示-长时间显示
     *
     * @param context
     * @param text
     */
    public static void showLongToast(Context context, String text) {
        Toast.makeText(context, text, Toast.LENGTH_LONG).show();
    }

    /**
     * 显示在中间的Toast
     *
     * @param context
     * @param text
     */
    public static void showCenterToast(Context context, String text) {
        Toast toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();

    }
}
