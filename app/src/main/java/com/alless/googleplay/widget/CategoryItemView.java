package com.alless.googleplay.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.alless.googleplay.R;
import com.alless.googleplay.bean.CategoryItemBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/3/27.
 */

public class CategoryItemView extends RelativeLayout {
    @BindView(R.id.title)
    TextView mTitle;
    @BindView(R.id.table_layout)
    TableLayout mTableLayout;

    public CategoryItemView(Context context) {
        this(context, null);
    }

    public CategoryItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        View.inflate(getContext(), R.layout.view_category_item, this);
        ButterKnife.bind(this, this);
    }

    /**
     * 用数据刷新CategoryItemView
     */
    public void bindView(CategoryItemBean categoryItemBean) {
        mTableLayout.removeAllViews();
        mTitle.setText(categoryItemBean.getTitle());
        List<CategoryItemBean.InfosBean> infos = categoryItemBean.getInfos();
        for (int i = 0; i < infos.size(); i++) {
            //创建行
            TableRow row = new TableRow(getContext());
            //模块宽度
            int result = getResources().getDisplayMetrics().widthPixels - mTableLayout.getPaddingLeft()-mTableLayout.getPaddingRight();
            //设置布局
            TableRow.LayoutParams params = new TableRow.LayoutParams();
            params.width = result/3;

            CategoryItemBean.InfosBean infosBean = infos.get(i);
           //添加到行
            //添加 第一格
            CategoryItemInfoView infoView1 = new CategoryItemInfoView(getContext());
            infoView1.setLayoutParams(params);
            infoView1.bindView(infosBean.getName1(), infosBean.getUrl1());
            row.addView(infoView1);
            //添加 第二格
            if(infosBean.getName2().length()>0){
                CategoryItemInfoView infoView2 = new CategoryItemInfoView(getContext());
                infoView2.setLayoutParams(params);
                infoView2.bindView(infosBean.getName2(), infosBean.getUrl2());
                row.addView(infoView2);
            }
            //添加 第三格
            if(infosBean.getName3().length()>0){
                CategoryItemInfoView infoView3 = new CategoryItemInfoView(getContext());
                infoView3.setLayoutParams(params);
                infoView3.bindView(infosBean.getName3(), infosBean.getUrl3());
                row.addView(infoView3);
            }
            //将行添加到TableLayout
            mTableLayout.addView(row);

        }

    }
}
