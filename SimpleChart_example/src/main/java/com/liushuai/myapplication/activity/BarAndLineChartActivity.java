package com.liushuai.myapplication.activity;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.liushuai.myapplication.R;
import com.liushuai.mylibrary.data.BarAndLineChartData;
import com.liushuai.mylibrary.view.BarAndLineChart;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

public class BarAndLineChartActivity extends BaseActivity {

    @Bind(R.id.bar_and_line_chart)
    BarAndLineChart mChart;

    @Override
    public void beforeSetView() {

    }

    @NonNull
    @Override
    public int getLayoutId() {
        return R.layout.activity_bar_and_line_chart;
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
        float[][] fs1 = new float[3][];
        fs1[0] = new float[]{40, 44, 24, 98, 76, 34};
        fs1[1] = new float[]{32, 43, 65, 76, 87, 21};
        fs1[2] = new float[]{12, 33, 22, 65, 102, 77};

        int[] cs = {
                Color.parseColor("#ffc502"),
                Color.parseColor("#3f69c3"),
                Color.parseColor("#3fb88c")};

        BarAndLineChartData chartData = new BarAndLineChartData(xs, fs1, cs);
        mChart.setData(chartData);
        mChart.setLineDataNum(1);
        mChart.setRotateXText(false);
        mChart.setBackLineColor(Color.parseColor("#a0a0a0"));
        mChart.setAxisColor(Color.parseColor("#a0a0a0"));
        mChart.invalidate();
    }
}
