package com.alless.googleplay.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.widget.Button;

import com.alless.googleplay.R;

/**
 * Created by Administrator on 2017/3/31.
 */

public class DownloadButton extends Button {

    private ColorDrawable mColorDrawable;
    private Paint mPaint;
    private int mProgress;
    private int mMax;

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
        //画进度矩形
        float progressWidth = (mProgress * 1.0f / mMax)*getWidth();
        mColorDrawable.setBounds(0, 0, (int) progressWidth, getHeight());
        mColorDrawable.draw(canvas);
        //在父类方法之前执行就不会被覆盖
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
}
