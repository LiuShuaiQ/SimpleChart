package com.liushuai.myapplication.activity;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.liushuai.myapplication.R;
import com.liushuai.mylibrary.data.BarAndLineChartData;
import com.liushuai.mylibrary.formatter.AxisFormatter;
import com.liushuai.mylibrary.view.IncreaseBarChart;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

public class IncreaseChartActivity extends BaseActivity {

    @Bind(R.id.increase_bar_chart)
    IncreaseBarChart mIncreaseBarChart;

    @Override
    public void beforeSetView() {

    }

    @NonNull
    @Override
    public int getLayoutId() {
        return R.layout.activity_increase_chart;
    }

    @Override
    public void afterSetView() {
        List<String> xs = new ArrayList<>();
        xs.add("1");
        xs.add("2");
        xs.add("3");
        xs.add("4");
        xs.add("5");
        xs.add("6");
        xs.add("7");
        xs.add("8");
        xs.add("9");
        xs.add("10");
        xs.add("11");
        xs.add("12");
        float[][] fs1 = new float[1][];
        fs1[0] = new float[]{20, 22, 12, 23, 13, 5, 12, 12, 23, 34, 45, 21};

        int[] cs = {Color.parseColor("#3f69c3"),
                Color.parseColor("#3fb88c")};

        BarAndLineChartData chartData = new BarAndLineChartData(xs, fs1, cs);
        mIncreaseBarChart.setData(chartData);
        mIncreaseBarChart.setRotateXText(false);
        mIncreaseBarChart.setBackLineColor(Color.parseColor("#a0a0a0"));
        mIncreaseBarChart.setAxisColor(Color.parseColor("#a0a0a0"));
        mIncreaseBarChart.setXValueAxisFormatter(new AxisFormatter() {
            @Override
            public String format(String formatString) {
                return formatString + "月";
            }
        });
        mIncreaseBarChart.invalidate();
    }
}
