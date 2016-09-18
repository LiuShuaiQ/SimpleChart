package com.liushuai.myapplication.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.liushuai.myapplication.R;
import com.liushuai.myapplication.utils.ToastUtils;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.ButterKnife;

/**
 * 基础activity,工程所有Activity的父类
 * Created by LiuShuai on 2016/7/8.
 */
public abstract class BaseActivity extends AppCompatActivity {

    public Context mContext;

    public static BaseActivity mInstance;

    /**
     * 进度条对话框
     */
    public ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        beforeSetView();
        setContentView(getLayoutId());
        ButterKnife.bind(this);
        mContext = mInstance = this;
        //初始化进度条
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage(getResources().getString(R.string.loading));
        mProgressDialog.setCancelable(false);
        afterSetView();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);

    }

    /**
     * 显示dialog
     */
    public void showMyDialog() {
        if (mProgressDialog != null && !mProgressDialog.isShowing())
            mProgressDialog.show();
    }

    /**
     * 关闭dialog
     */
    public void closeMyDialog() {
        if (mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    /**
     * 连续点击退出程序
     */
    protected static Boolean isQuit = false;
    Timer mTimer = new Timer();

    public void toQuitSystem() {
        if (isQuit == false) {
            isQuit = true;
            ToastUtils.showShortToast(this, "再按一次返回键退出应用程序");
            TimerTask task = new TimerTask() {
                @Override
                public void run() {
                    isQuit = false;
                }
            };
            mTimer.schedule(task, 2000);
        } else {
            this.finish();
        }
    }

    /**
     * 返回监听事件<br/>
     * android:onClick = "back"
     *
     * @param view
     */
    public void back(View view) {
        this.finish();
    }

    public abstract void beforeSetView();

    /**
     * @return 布局的ID
     */
    @LayoutRes
    @NonNull
    public abstract int getLayoutId();

    public abstract void afterSetView();

}
