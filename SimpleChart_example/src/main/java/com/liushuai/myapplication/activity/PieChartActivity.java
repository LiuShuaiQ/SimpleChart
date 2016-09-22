package com.liushuai.myapplication.activity;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.RelativeSizeSpan;
import android.view.TextureView;

import com.liushuai.myapplication.R;
import com.liushuai.mylibrary.data.PieChartData;
import com.liushuai.mylibrary.view.PieChart;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * 饼图测试类
 */
public class PieChartActivity extends BaseActivity {

    private static final String TAG = "PieChartActivity";

    @Bind(R.id.pie_chart)
    PieChart mPieChart;

    @Override
    public void beforeSetView() {

    }

    @NonNull
    @Override
    public int getLayoutId() {
        return R.layout.activity_pie_chart;
    }

    @Override
    public void afterSetView() {
        List<String> xs = new ArrayList<>();
        xs.add("一班");
        xs.add("二班");
        xs.add("三班");
        xs.add("四班");

        float[][] fs = new float[1][];
        fs[0] = new float[]{12, 23, 21, 5};
        int[] cs = {Color.parseColor("#ff1122"),
                Color.parseColor("#2233aa"),
                Color.parseColor("#aa9090"),
                Color.parseColor("#9900ff")
        };

        PieChartData mData = new PieChartData(xs, fs, cs);
        mPieChart.setData(mData);
        mPieChart.invalidate();
    }

    /**
     * set style of the center text
     *
     * @return
     */
    private SpannableString generateCenterSpannableText() {
        SpannableString description = new SpannableString("检修\n32\n台");
        description.setSpan(new RelativeSizeSpan(1.7f), 0, description.length(), 0);
//        description.setSpan(new StyleSpan(Typeface.NORMAL), 14, description.length() - 23, 0);
//        description.setSpan(new ForegroundColorSpan(Color.GRAY), 23, description.length() - 9, 0);
//        description.setSpan(new RelativeSizeSpan(.8f), 14, description.length() - 9, 0);
//        description.setSpan(new StyleSpan(Typeface.ITALIC), description.length() - 9, description.length(), 0);
//        description.setSpan(new ForegroundColorSpan(ColorTemplate.getHoloBlue()), description.length() - 9, description.length(), 0);
        return description;
    }
}
