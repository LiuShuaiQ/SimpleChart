package com.liushuai.myapplication;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.View;

import com.liushuai.myapplication.activity.BarAndLineChartActivity;
import com.liushuai.myapplication.activity.BarChartActivity;
import com.liushuai.myapplication.activity.BaseActivity;
import com.liushuai.myapplication.activity.BigPanelViewActivity;
import com.liushuai.myapplication.activity.IncreaseChartActivity;
import com.liushuai.myapplication.activity.LineChartActivity;
import com.liushuai.myapplication.activity.PieChartActivity;

public class MainActivity extends BaseActivity {


    /**
     * 跳到柱状图页面
     *
     * @param v
     */
    public void barChartClick(View v) {
        intentTO(BarChartActivity.class);
    }

    /**
     * 仪表盘视图
     *
     * @param v
     */
    public void panelViewClick(View v) {
        intentTO(BigPanelViewActivity.class);
    }


    /**
     * 折线图
     *
     * @param view
     */
    public void lineChartClick(View view) {
        intentTO(LineChartActivity.class);
    }

    /**
     * 扇形图
     *
     * @param view
     */
    public void pieChartClick(View view) {
        intentTO(PieChartActivity.class);
    }

    /**
     * the combined chart of bae and line
     *
     * @param view
     */
    public void barAndLineChartClick(View view) {
        intentTO(BarAndLineChartActivity.class);
    }

    /**
     * increase chart
     *
     * @param view
     */
    public void increaseChart(View view) {
        intentTO(IncreaseChartActivity.class);
    }

    private void intentTO(Class<?> cls) {
        Intent i = new Intent(this, cls);
        startActivity(i);
    }

    @Override
    public void beforeSetView() {

    }

    @NonNull
    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void afterSetView() {

    }
}
