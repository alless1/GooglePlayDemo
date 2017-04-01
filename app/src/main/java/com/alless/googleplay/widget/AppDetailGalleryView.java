package com.alless.googleplay.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.alless.googleplay.R;
import com.alless.googleplay.app.Constant;
import com.alless.googleplay.bean.AppDetailBean;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;

import butterknife.BindView;

/**
 * Created by Administrator on 2017/3/31.
 */
public class AppDetailGalleryView extends BaseView {
    @BindView(R.id.screen_container)
    LinearLayout mScreenContainer;

    public AppDetailGalleryView(Context context) {
        super(context);
    }

    public AppDetailGalleryView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.view_app_detail_gallery;
    }

    public void bindView(AppDetailBean appDetailBean) {
        int padding = getResources().getDimensionPixelSize(R.dimen.padding);
        for (int i = 0; i < appDetailBean.getScreen().size(); i++) {
            String url = appDetailBean.getScreen().get(i);
            String imageUrl = Constant.URL_IMAGE + url;
            ImageView imageView = new ImageView(getContext());
            if (i == appDetailBean.getScreen().size() - 1) {
                imageView.setPadding(padding, 0, padding,0);
            } else {
                imageView.setPadding(padding,0,0,0);
            }
            Glide.with(getContext()).load(imageUrl).override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL).into(imageView);
            mScreenContainer.addView(imageView);
        }

    }
}
