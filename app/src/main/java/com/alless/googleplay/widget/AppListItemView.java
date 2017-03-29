package com.alless.googleplay.widget;

import android.content.Context;
import android.text.format.Formatter;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alless.googleplay.R;
import com.alless.googleplay.app.Constant;
import com.alless.googleplay.bean.AppListItemBean;
import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/3/29.
 */
public class AppListItemView extends RelativeLayout {
    private static final String TAG = "AppListItemView";

    @BindView(R.id.app_icon)
    ImageView mAppIcon;
    @BindView(R.id.app_name)
    TextView mAppName;
    @BindView(R.id.app_rating)
    RatingBar mAppRating;
    @BindView(R.id.app_size)
    TextView mAppSize;
    @BindView(R.id.download_progress)
    CircleDownloadView mDownloadProgress;
    @BindView(R.id.app_des)
    TextView mAppDes;

    public AppListItemView(Context context) {
        this(context, null);
    }

    public AppListItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.list_app_item, this);
        ButterKnife.bind(this, this);
    }

    public void bindView(AppListItemBean bean) {
        String url = Constant.URL_IMAGE + bean.getIconUrl();
        Glide.with(getContext()).load(url).into(mAppIcon);
        mAppName.setText(bean.getName());
        mAppRating.setRating(bean.getStars());
        mAppSize.setText(Formatter.formatFileSize(getContext(), bean.getSize()));
        mAppDes.setText(bean.getDes());
    }

}
