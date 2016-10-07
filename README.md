# SimpleChart
This is the library of a simple chart.
It is has same simple chart ,such as BarChart,LineChart,PanelView and CombinedChart.

最近将Android中的仪表盘，柱状图，折线图和一些混合图进行了封装，在自己进行封装的过程中，遇到了许多的bugs，也遇到了许多在程序结构设计中的问题，在这过程中我参考了一些大神写的框架，MPCharts以及hellocharts，这些Android上的图表框架已经非常成熟，我写一个自己的图表框架主要目的是当一些图表不能在这些框架里找到时使用一下，现在的1.0版本还有许多的不足的地方。
1、仪表盘视图。
![这里写图片描述](http://img.blog.csdn.net/20161007162458182)
2、柱状图。
![这里写图片描述](http://img.blog.csdn.net/20161007162851471)
3、折线图。
![这里写图片描述](http://img.blog.csdn.net/20161007162921535)
4、柱状图和折线图混合。
![这里写图片描述](http://img.blog.csdn.net/20161007163003325)
5、增长图。
![这里写图片描述](http://img.blog.csdn.net/20161007163048184)


例子（以柱状图为例）：
布局：

```
<?xml version="1.0" encoding="utf-8"?>
<RelativeLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:paddingBottom="@dimen/activity_vertical_margin"
    android:paddingLeft="@dimen/activity_horizontal_margin"
    android:paddingRight="@dimen/activity_horizontal_margin"
    android:paddingTop="@dimen/activity_vertical_margin"
    tools:context="com.liushuai.myapplication.activity.BarChartActivity">

    <com.liushuai.mylibrary.view.BarChart
        android:id="@+id/bar_chart"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_margin="30dp"
        app:axisTextColor="#808080"
        app:firstRectColor="#d7adad"
        app:rectWidth="30px"
        app:xEnable="true"
        app:yEnable="true"
        />
</RelativeLayout>

```

然后是主Activity中的代码，这里面我用到了ButterKnife框架。

```
package com.liushuai.myapplication.activity;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.liushuai.myapplication.R;
import com.liushuai.mylibrary.data.BarAndLineChartData;
import com.liushuai.mylibrary.data.IEntity;
import com.liushuai.mylibrary.listener.OnValueClickListener;
import com.liushuai.mylibrary.view.BarChart;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * 柱状图
 */
public class BarChartActivity extends BaseActivity {

    @Bind(R.id.bar_chart)
    BarChart mBarChart;

    @Override
    public void beforeSetView() {

    }

    @NonNull
    @Override
    public int getLayoutId() {
        return R.layout.activity_bar_chart;
    }

    @Override
    public void afterSetView() {

        List<String> xs = new ArrayList<>();
        xs.add("1月");
        xs.add("2月");
        xs.add("3月");
        xs.add("4月");
        xs.add("5月");
        xs.add("6月");
        float[][] fs1 = new float[2][];
        fs1[0] = new float[]{40, 44, 24, 98, 76, 34};
        fs1[1] = new float[]{32, 43, 65, 76, 87, 21};

        int[] cs = {Color.parseColor("#3f69c3"),
                Color.parseColor("#3fb88c")};

        BarAndLineChartData chartData = new BarAndLineChartData(xs, fs1, cs);
        mBarChart.setData(chartData);
        //设置X轴是否进行旋转，即 X 轴倾斜显示
        mBarChart.setRotateXText(true);
        //设置背景线的颜色
        mBarChart.setBackLineColor(Color.parseColor("#a0a0a0"));
        //设置坐标轴线的颜色
        mBarChart.setAxisColor(Color.parseColor("#a0a0a0"));
        mBarChart.invalidate();
        //图标的值点击事件
        mBarChart.setOnValueClickListener(new OnValueClickListener() {
            @Override
            public void onBarValueClick(int index, IEntity entity) {
                Toast.makeText(mContext, "BarChart click--->" + index, Toast.LENGTH_SHORT).show();
            }
        });
    }
}

```

这里面的全部代码我都放到github上了，github地址：https://github.com/LiuShuaiQ/SimpleChart
