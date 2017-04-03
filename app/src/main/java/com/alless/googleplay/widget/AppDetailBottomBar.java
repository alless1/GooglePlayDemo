package com.alless.googleplay.widget;

import android.content.Context;
import android.util.AttributeSet;

import com.alless.googleplay.R;
import com.alless.googleplay.bean.AppDetailBean;
import com.alless.googleplay.manager.DownloadManager;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/3/31.
 */

public class AppDetailBottomBar extends BaseView {
    @BindView(R.id.download_button)
    DownloadButton mDownloadButton;
    private AppDetailBean mAppDetailBean;

    public AppDetailBottomBar(Context context) {
        super(context);
    }

    public AppDetailBottomBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.view_app_detail_bottom_bar;
    }

    public void bindView(AppDetailBean appDetailBean) {
        mAppDetailBean = appDetailBean;
        mDownloadButton.syncState(appDetailBean);
    }

    @OnClick(R.id.download_button)
    public void onViewClicked() {
        DownloadManager.getInstance().handleDownloadAction(getContext(),mAppDetailBean.getPackageName());
    }
}
