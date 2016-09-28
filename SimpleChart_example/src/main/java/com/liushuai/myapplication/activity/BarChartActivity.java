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
        mBarChart.setRotateXText(true);
        mBarChart.setBackLineColor(Color.parseColor("#a0a0a0"));
        mBarChart.setAxisColor(Color.parseColor("#a0a0a0"));
        mBarChart.invalidate();

        mBarChart.setOnValueClickListener(new OnValueClickListener() {
            @Override
            public void onBarValueClick(int index, IEntity entity) {
                Toast.makeText(mContext, "BarChart click--->" + index, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
