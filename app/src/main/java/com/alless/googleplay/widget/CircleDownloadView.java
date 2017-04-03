package com.alless.googleplay.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alless.googleplay.R;
import com.alless.googleplay.bean.AppListItemBean;
import com.alless.googleplay.bean.DownloadInfoBean;
import com.alless.googleplay.manager.DownloadManager;

import java.util.Observable;
import java.util.Observer;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/3/29.
 */

public class CircleDownloadView extends RelativeLayout implements Observer {
    @BindView(R.id.download)
    ImageView mDownload;
    @BindView(R.id.download_info)
    TextView mDownloadInfo;
    private RectF mRectF;
    private Paint mPaint;
    private AppListItemBean mAppListItemBean;
    private DownloadInfoBean mDownloadInfoBean;
    private boolean enableProgress= true;
    private int mPercent;

    public CircleDownloadView(Context context) {
        this(context, null);
    }

    public CircleDownloadView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.view_download_progress, this);
        ButterKnife.bind(this);

        mRectF = new RectF();
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.BLUE);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(8);
        //绘制viewGroup需要设置以下属性
        setWillNotDraw(false);
    }

    @OnClick(R.id.download)
    public void onViewClicked() {
        //点击处理交给DownloadManager处理
        DownloadManager.getInstance().handleDownloadAction(getContext(), mAppListItemBean.getPackageName());

    }

    public void syncState(AppListItemBean bean) {
        mAppListItemBean = bean;
        //初始化下载信息
        mDownloadInfoBean = DownloadManager.getInstance().initDownloadInfo(getContext(), bean.getPackageName(), bean.getSize(), bean.getDownloadUrl());
        //增加观察者
        DownloadManager.getInstance().addObserver(bean.getPackageName(), this);
        //刷新ui
        updateStatus(mDownloadInfoBean);
    }

    private void updateStatus(DownloadInfoBean downloadInfoBean) {
        switch (downloadInfoBean.getStatus()) {
            case DownloadManager.STATE_INSTALLED:
                mDownloadInfo.setText(getResources().getString(R.string.open));
                mDownload.setImageResource(R.drawable.ic_install);
                break;
            case DownloadManager.STATE_DOWNLOADED:
                mDownloadInfo.setText(getResources().getString(R.string.install));
                mDownload.setImageResource(R.drawable.ic_install);
                break;
            case DownloadManager.STATE_UN_DOWNLOAD:
                clearProgress();//清除进度
                mDownloadInfo.setText(getResources().getString(R.string.download));
                mDownload.setImageResource(R.drawable.ic_download);
                break;
            case DownloadManager.STATE_WAITING:
                mDownloadInfo.setText(getResources().getString(R.string.waiting));
                mDownload.setImageResource(R.drawable.ic_cancel);
                break;
            case DownloadManager.STATE_DOWNLOADING:
                mDownload.setImageResource(R.drawable.ic_pause);
                mPercent = (int)(downloadInfoBean.getProgress()*1.0f/downloadInfoBean.getSize()*100);
                String percentString = mPercent + "%";
                mDownloadInfo.setText(percentString);
                enableProgress = true;//打开进度条开关
                invalidate();
                break;
            case DownloadManager.STATE_PAUSE:
                mDownloadInfo.setText(getResources().getString(R.string.continue_download));
                mDownload.setImageResource(R.drawable.ic_download);
                break;
            case DownloadManager.STATE_FAILED:
                mDownloadInfo.setText(getResources().getString(R.string.retry));
                mDownload.setImageResource(R.drawable.ic_redownload);
                break;
        }

    }

    /**
     * 清除进度条
     */
    private void clearProgress() {
        enableProgress = false;//关闭进度条开关
        invalidate();
    }


    /**
     * 更新观察者
     *
     * @param o
     * @param arg
     */
    @Override
    public void update(Observable o, Object arg) {
        final DownloadInfoBean downloadInfoBean = (DownloadInfoBean) arg;
        post(new Runnable() {
            @Override
            public void run() {
                updateStatus(downloadInfoBean);
            }
        });

    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (enableProgress) {
            int left = mDownload.getLeft() ;
            int top = mDownload.getTop();
            int right = mDownload.getRight();
            int bottom = mDownload.getBottom();
            mRectF.set(left, top, right, bottom);
            int startAngle = -90;
            int sweepAngle = (int) (mPercent * 1.0f / 100 * 360);
            boolean UserCenter = false;
            canvas.drawArc(mRectF, startAngle, sweepAngle, UserCenter, mPaint);
        }
    }
}
