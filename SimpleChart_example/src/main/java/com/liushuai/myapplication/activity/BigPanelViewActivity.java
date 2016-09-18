package com.liushuai.myapplication.activity;

import android.support.annotation.NonNull;

import com.liushuai.myapplication.R;
import com.liushuai.mylibrary.view.BigPanelView;

import butterknife.Bind;

public class BigPanelViewActivity extends BaseActivity {

    @Bind(R.id.big_panel_view)
    BigPanelView mBigPanelView;

    @Override
    public void beforeSetView() {

    }

    @NonNull
    @Override
    public int getLayoutId() {
        return R.layout.activity_big_panel_view;
    }

    @Override
    public void afterSetView() {

    }
}
