package com.liushuai.myapplication.activity;

import android.support.annotation.NonNull;

import com.liushuai.myapplication.R;
import com.liushuai.mylibrary.data.BarAndLineChartData;
import com.liushuai.mylibrary.view.LineChart;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * 折线图测试类
 */
public class LineChartActivity extends BaseActivity {

    @Bind(R.id.line_chart)
    LineChart mLineChart;

    @Override
    public void beforeSetView() {

    }

    @NonNull
    @Override
    public int getLayoutId() {
        return R.layout.activity_line_chart;
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
        float[] fs = {200, 300, 40, 100, 233, 130};

        BarAndLineChartData chartData = new BarAndLineChartData(xs, fs);
        mLineChart.setData(chartData);
        mLineChart.invalidate();
    }
}
