package com.liushuai.myapplication.activity;

import android.graphics.Color;
import android.support.annotation.NonNull;

import com.liushuai.myapplication.R;
import com.liushuai.mylibrary.data.BarAndLineChartData;
import com.liushuai.mylibrary.formatter.PuppetAxisFormatter;
import com.liushuai.mylibrary.view.LineChart;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * 折线图测试类
 * Line Chart Test Class
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
        xs.add("1");
        xs.add("2");
        xs.add("3");
        xs.add("4");
        xs.add("5");
        xs.add("6");
        float[][] fs = new float[2][];
        fs[0] = new float[]{40, 44, 24, 98, 76, 34};
        fs[1] = new float[]{32, 43, 65, 76, 87, 21};

        int[] cs = {Color.parseColor("#3f69c3"),
                Color.parseColor("#3fb88c")};


        BarAndLineChartData chartData = new BarAndLineChartData(xs, fs, cs);
        mLineChart.setData(chartData);
        mLineChart.setRotateXText(false);
        mLineChart.setBackLineColor(Color.parseColor("#a0a0a0"));
        mLineChart.setAxisColor(Color.parseColor("#a0a0a0"));
        mLineChart.setRightYAxisEnable(false);
        mLineChart.setYLeftValueAxisFormatter(new PuppetAxisFormatter() {
            @Override
            public String format(String formatString) {
                return formatString+"%";
            }
        });
        mLineChart.setXValueAxisFormatter(new PuppetAxisFormatter() {
            @Override
            public String format(String formatString) {
                return formatString+"月";
            }
        });
        mLineChart.invalidate();
    }
}
