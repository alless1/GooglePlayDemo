package com.alless.googleplay.ui.fragment;

import android.view.View;

import com.alless.googleplay.R;
import com.alless.googleplay.adapter.RecommendAdapter;
import com.alless.googleplay.network.Api;
import com.alless.googleplay.network.HeiMaRetrofit;
import com.alless.googleplay.widget.StellarMap;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 2017/3/26.
 */

public class RecommendFragment extends BaseFragment {


    private List<String> mDatas;

    @Override
    protected void onStartLoadData() {
        Api api = HeiMaRetrofit.getInstance().getApi();
        Call<List<String>> listCall = api.listRecommed();
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


    @Override
    protected View onCreateContentView() {
        StellarMap stellarMap = new StellarMap(getContext());
        int padding = getResources().getDimensionPixelSize(R.dimen.padding);
        stellarMap.setInnerPadding(padding, padding, padding, padding);
        stellarMap.setAdapter(new RecommendAdapter(getContext(), mDatas));
       //设置分布网格
        stellarMap.setRegularity(15, 20);
        //设置初始化页面
        stellarMap.setGroup(0,false);
        return stellarMap;
    }
}
