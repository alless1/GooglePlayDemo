package com.alless.googleplay.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.Button;

import com.alless.googleplay.R;
import com.alless.googleplay.bean.AppDetailBean;
import com.alless.googleplay.bean.DownloadInfoBean;
import com.alless.googleplay.manager.DownloadManager;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by Administrator on 2017/3/31.
 */

public class DownloadButton extends Button implements Observer{
    private static final String TAG = "DownloadButton";

    private ColorDrawable mColorDrawable;
    private Paint mPaint;
    private int mProgress ;
    private int mMax ;
    private boolean enableProgress = true;

    public DownloadButton(Context context) {
        this(context,null);
    }

    public DownloadButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mColorDrawable = new ColorDrawable(Color.BLUE);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.BLUE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Log.d(TAG, "onDraw: 前");
        if (enableProgress) {
            Log.d(TAG, "onDraw: 后mProgress"+mProgress);
            //画进度矩形
            float progressWidth = (mProgress * 1.0f / mMax)*getWidth();
            mColorDrawable.setBounds(0, 0, (int) progressWidth, getHeight());
            mColorDrawable.draw(canvas);
            //在父类方法之前执行就不会被覆盖
        }

        super.onDraw(canvas);
    }

    /**
     * 设置进度的文本
     */
    public void setProgress(int progress) {
        mProgress = progress;
        //百分比
        int percent = (int) ((mProgress * 1.0f / mMax) * 100);
        String progressString = String.format(getResources().getString(R.string.download_progress), percent);
        setText(progressString);
        invalidate();
    }

    /**
     * 设置最大值
     */
    public void setMax(int max) {
        mMax = max;
    }

    /**
     * 同步状态
     * @param appDetailBean
     */
    public void syncState(AppDetailBean appDetailBean) {
        //初始化信息
        DownloadInfoBean downloadInfoBean = DownloadManager.getInstance().initDownloadInfo(getContext(),
                appDetailBean.getPackageName(),
                appDetailBean.getSize(),
                appDetailBean.getDownloadUrl());


        //将当前应用当做观察者添加入 被观察者的集合中.
        DownloadManager.getInstance().addObserver(appDetailBean.getPackageName(), this);

        //刷新界面
        updateStatus(downloadInfoBean);
    }

    //更新UI
    private void updateStatus(DownloadInfoBean downloadInfoBean) {
        switch (downloadInfoBean.getStatus()) {
            case DownloadManager.STATE_INSTALLED:
                setText(getResources().getString(R.string.open));
                break;
            case DownloadManager.STATE_DOWNLOADED:
                clearProgressBackground();
                setText(getResources().getString(R.string.install));
                break;
            case DownloadManager.STATE_UN_DOWNLOAD:
                setText(getResources().getString(R.string.download));
                break;
            case DownloadManager.STATE_WAITING:
                setText(getResources().getString(R.string.waiting));
                break;
            case DownloadManager.STATE_DOWNLOADING:
                //更新进度
                enableProgress = true;//进度开关打开
                setMax(downloadInfoBean.getSize());
                setProgress(downloadInfoBean.getProgress());
                break;
            case DownloadManager.STATE_PAUSE:
                setText(getResources().getString(R.string.pause));
                break;
            case DownloadManager.STATE_FAILED:
                clearProgressBackground();
                setText(getResources().getString(R.string.retry));
                break;
        }

    }

    /**
     * 下载完成以后去掉蓝色背景
     */
    private void clearProgressBackground() {
        //关闭绘制背景的开关
        enableProgress = false;
        invalidate();
    }

    /**
     * 被观察者的更新消息
     * @param o
     * @param arg
     */
    @Override
    public void update(Observable o, Object arg) {
        final DownloadInfoBean downloadInfoBean = (DownloadInfoBean) arg;
        //更新UI
        post(new Runnable() {
            @Override
            public void run() {
                updateStatus(downloadInfoBean);
            }
        });
    }
}
