package com.alless.googleplay.widget;

import android.content.Context;
import android.text.format.Formatter;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.alless.googleplay.R;
import com.alless.googleplay.app.Constant;
import com.alless.googleplay.bean.AppDetailBean;
import com.bumptech.glide.Glide;

import butterknife.BindView;

/**
 * Created by Administrator on 2017/3/30.
 */
public class AppDetailInfoView extends BaseView {


    @BindView(R.id.app_name)
    TextView mAppName;
    @BindView(R.id.app_rating)
    RatingBar mAppRating;
    @BindView(R.id.app_download_count)
    TextView mAppDownloadCount;
    @BindView(R.id.app_version)
    TextView mAppVersion;
    @BindView(R.id.timestamp)
    TextView mTimestamp;
    @BindView(R.id.app_size)
    TextView mAppSize;
    @BindView(R.id.app_icon)
    ImageView mAppIcon;

    public AppDetailInfoView(Context context) {
        super(context);
    }

    public AppDetailInfoView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.view_app_detail_info;
    }

    public void bingView(AppDetailBean appDetailBean) {
        String url = Constant.URL_IMAGE + appDetailBean.getIconUrl();
        Glide.with(getContext()).load(url).into(mAppIcon);
        mAppName.setText(appDetailBean.getName());
        mAppRating.setRating(appDetailBean.getStars());
        //占位符方式 刷新以下内容 String.format("下载量：%s" ,"40万+") ----> "下载量：40万+"
        //下载量
        String formatString = getResources().getString(R.string.download_count);
        String formatResult = String.format(formatString, appDetailBean.getDownloadNum());
        mAppDownloadCount.setText(formatResult);
        //版本号
        String version = String.format(getResources().getString(R.string.version_code), appDetailBean.getVersion());
        mAppVersion.setText(version);
        //时间
        String date = String.format(getResources().getString(R.string.time), appDetailBean.getDate());
        mTimestamp.setText(date);
        //大小
        String size = String.format(getResources().getString(R.string.app_size), Formatter.formatFileSize(getContext(), appDetailBean.getSize()));
        mAppSize.setText(size);
    }
}
