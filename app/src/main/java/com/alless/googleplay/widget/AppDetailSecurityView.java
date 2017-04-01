package com.alless.googleplay.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alless.googleplay.R;
import com.alless.googleplay.app.Constant;
import com.alless.googleplay.bean.AppDetailBean;
import com.alless.googleplay.utils.AnimationUtil;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/3/30.
 */

public class AppDetailSecurityView extends BaseView {
    @BindView(R.id.tag_container)
    LinearLayout mTagContainer;
    @BindView(R.id.security_arrow)
    ImageView mSecurityArrow;
    @BindView(R.id.security_info_container)
    LinearLayout mSecurityInfoContainer;
    private boolean isOpen = false;//展开的状态标记

    public AppDetailSecurityView(Context context) {
        super(context);
    }

    public AppDetailSecurityView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.view_app_detail_security;
    }

    public void bindView(AppDetailBean appDetailBean) {
        //遍历数组
        for (int i = 0; i < appDetailBean.getSafe().size(); i++) {
            AppDetailBean.SafeBean safeBean = appDetailBean.getSafe().get(i);
            //标签添加到容器
            ImageView imageView = new ImageView(getContext());
            String url = Constant.URL_IMAGE + safeBean.getSafeUrl();
            Glide.with(getContext()).load(url).override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL).into(imageView);
            mTagContainer.addView(imageView);
            // checkBox添加到 LinearLayout
            LinearLayout linearLayout = new LinearLayout(getContext());
            linearLayout.setOrientation(LinearLayout.HORIZONTAL);
            ImageView checkBox = new ImageView(getContext());
            String checkBoxUrl = Constant.URL_IMAGE + safeBean.getSafeDesUrl();
            Glide.with(getContext()).load(checkBoxUrl).override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL).into(checkBox);
            linearLayout.addView(checkBox);
            //文本描述 添加到LinearLayout
            TextView textView = new TextView(getContext());
            textView.setText(safeBean.getSafeDes());
            //如果safeDesColor值不等于0表示是有问题，要变化文本颜色 提示用户
            if (safeBean.getSafeDesColor() != 0) {
                textView.setTextColor(getResources().getColor(R.color.app_detail_safe_warning));
            } else {
                textView.setTextColor(getResources().getColor(R.color.app_detail_safe_normal));
            }
            linearLayout.addView(textView);
            //LinearLayout添加到容器
            mSecurityInfoContainer.addView(linearLayout);
        }
    }

    @OnClick(R.id.security_arrow)
    public void onViewClicked() {
        toogle();//展开或者收缩安全描述
    }

    private void toogle() {
        if(isOpen){
            //收缩
            //收缩动画
            int height = mSecurityInfoContainer.getHeight();
            AnimationUtil.animationViewHeight(mSecurityInfoContainer, height, 0);
            //旋转动画
            AnimationUtil.rotateView(mSecurityArrow,-180f,0);
        }else{
            //展开
            mSecurityInfoContainer.measure(0, 0);
            int measuredHeight = mSecurityInfoContainer.getMeasuredHeight();
            AnimationUtil.animationViewHeight(mSecurityInfoContainer, 0, measuredHeight);
            AnimationUtil.rotateView(mSecurityArrow,0,-180f);
        }
        //更新标记
        isOpen = !isOpen;
    }
}
