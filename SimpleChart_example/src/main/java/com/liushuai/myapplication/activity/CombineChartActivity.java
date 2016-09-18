package com.liushuai.myapplication.activity;

import android.support.annotation.NonNull;

import com.liushuai.myapplication.R;
import com.liushuai.mylibrary.view.MyCombinedView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

public class CombineChartActivity extends BaseActivity {

    @Bind(R.id.my_combined_view_chart)
    MyCombinedView mMyCombinedView;

    @Override
    public void beforeSetView() {

    }

    @NonNull
    @Override
    public int getLayoutId() {
        return R.layout.activity_combine_chart;
    }

    @Override
    public void afterSetView() {
        List<String> lys = new ArrayList<>();
        lys.add("0");
        lys.add("30");
        lys.add("60");
        lys.add("90");
        lys.add("120");
        List<String> rys = new ArrayList<>();
        rys.add("10");
        rys.add("20");
        rys.add("30");
        rys.add("40");
        rys.add("50");

        List<String> xs = new ArrayList<>();
        xs.add("1月");
        xs.add("2月");
        xs.add("3月");
        xs.add("4月");
        xs.add("5月");
        xs.add("6月");
        float[] fs1 = {110, 44, 110, 110, 110, 110, 110, 110, 110, 110, 64, 110, 110};
        float[] fs2 = {40, 110, 100, 100, 98, 110, 87, 110, 76, 110, 110, 110, 110};
        float[] fs = {20, 30, 40, 10, 20, 30, 40, 40, 10, 20, 10, 45, 20};
        MyCombinedView.ChartData chartData = new MyCombinedView.ChartData(xs, lys, rys, fs1, fs2, fs);
        mMyCombinedView.setChartData(chartData);
        mMyCombinedView.invalidate();
    }
}
