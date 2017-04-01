package com.alless.googleplay.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.TextView;

import com.alless.googleplay.R;
import com.alless.googleplay.bean.AppDetailBean;
import com.alless.googleplay.utils.AnimationUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/3/31.
 */
public class AppDetailDesView extends BaseView {
    private static final int MAX_LINES = 5;
    @BindView(R.id.app_detail_des_text)
    TextView mAppDetailDesText;
    @BindView(R.id.app_name)
    TextView mAppName;
    @BindView(R.id.des_arrow)
    ImageView mDesArrow;
    private int mOriginalHeight;
    private boolean isOpen = false;

    @Override
    protected int getLayoutId() {
        return R.layout.view_app_detail_des;
    }

    public AppDetailDesView(Context context) {
        super(context);
    }

    public AppDetailDesView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initHeight();
    }

    private void initHeight() {
        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                getViewTreeObserver().removeGlobalOnLayoutListener(this);
                //获取描述文本加载完以后的高度
                mOriginalHeight = mAppDetailDesText.getMeasuredHeight();
                //获取行数
                int lineCount = mAppDetailDesText.getLineCount();
                if(lineCount>MAX_LINES){
                    mAppDetailDesText.setLines(MAX_LINES);
                }

            }
        });
    }

    public void bindView(AppDetailBean appDetailBean) {
        mAppDetailDesText.setText(appDetailBean.getDes());
        mAppName.setText(appDetailBean.getName());
    }


    @OnClick(R.id.des_arrow)
    public void onViewClicked() {
        toggle();
    }

    private void toggle() {
        if(isOpen){
            //关闭
            int start = mOriginalHeight;
            if (mAppDetailDesText.getLineCount() > MAX_LINES) {
                mAppDetailDesText.setLines(MAX_LINES);
                mAppDetailDesText.measure(0,0);
            }
            int end = mAppDetailDesText.getMeasuredHeight();
            AnimationUtil.animationViewHeight(mAppDetailDesText, start, end);
            AnimationUtil.rotateView(mDesArrow,-180,0);
        }else{
            //打开
            int measuredHeight = mAppDetailDesText.getMeasuredHeight();
            AnimationUtil.animationViewHeight(mAppDetailDesText, measuredHeight, mOriginalHeight);
            AnimationUtil.rotateView(mDesArrow,0,-180);

        }
        isOpen = !isOpen;
    }
}
