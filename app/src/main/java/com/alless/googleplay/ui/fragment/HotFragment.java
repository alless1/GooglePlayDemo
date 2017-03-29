package com.alless.googleplay.ui.fragment;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.alless.googleplay.R;
import com.alless.googleplay.network.Api;
import com.alless.googleplay.network.HeiMaRetrofit;
import com.alless.googleplay.widget.FlowLayout;

import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 2017/3/26.
 */

public class HotFragment extends BaseFragment {
    private static final String TAG = "HotFragment";
    private List<String> mDatas;
    private int mPadding;
    private FlowLayout mFlowLayout;

    /**
     * onDataLoadSuccess()会调用此方法,创造内容视图
     *
     * @return
     */
    @Override
    protected View onCreateContentView() {
        mPadding = getResources().getDimensionPixelSize(R.dimen.padding);
        mFlowLayout = new FlowLayout(getContext());
        mFlowLayout.setPadding(mPadding, mPadding, mPadding, mPadding);
        for (int i = 0; i < mDatas.size(); i++) {
            //创造TextView
            TextView tv = getTextView(i);
            //创造selector
            StateListDrawable selector = getStateListDrawable();
            tv.setBackgroundDrawable(selector);
            mFlowLayout.addView(tv);
        }

        return mFlowLayout;
    }

    private TextView getTextView(int i) {
        final TextView tv = new TextView(getContext());
        tv.setText(mDatas.get(i));
        tv.setTextColor(Color.WHITE);
        tv.setGravity(Gravity.CENTER);
        tv.setClickable(true);
        tv.setPadding(mPadding, mPadding, mPadding, mPadding);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),tv.getText(),Toast.LENGTH_SHORT).show();;
            }
        });
        return tv;
    }

    /**
     * 创建选择器selector
     * @return
     */
    private StateListDrawable getStateListDrawable() {
        //创建shape
        GradientDrawable normal = new GradientDrawable();
        normal.setCornerRadius(8);
        normal.setColor(getArgb());
        GradientDrawable pressed = new GradientDrawable();
        pressed.setCornerRadius(8);
        pressed.setColor(Color.GRAY);

        StateListDrawable selector = new StateListDrawable();
        selector.addState(new int[]{android.R.attr.state_pressed}, pressed);
        selector.addState(new int[]{}, normal);

        return selector;
    }

    /**
     * 获取随机色
     * @return
     */
    private int getArgb() {
        int alpha = 255;
        int red = 30 + new Random().nextInt(200);
        int green = 30 + new Random().nextInt(200);
        int blue = 30 + new Random().nextInt(200);
        return Color.argb(alpha, red, green, blue);
    }


    @Override
    protected void onStartLoadData() {
        Api api = HeiMaRetrofit.getInstance().getApi();
        Call<List<String>> listCall = api.listHot();
        //异步请求,同步请求为execute();
        listCall.enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                mDatas = response.body();
                onDataLoadSuccess();

            }

            @Override
            public void onFailure(Call<List<String>> call, Throwable t) {
                onDataLoadFailed();
            }
        });
    }


}
