package com.alless.googleplay.ui.fragment;

import android.widget.BaseAdapter;

import com.alless.googleplay.adapter.CategoryAdapter;
import com.alless.googleplay.bean.CategoryItemBean;
import com.alless.googleplay.network.HeiMaRetrofit;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 2017/3/26.
 */

public class CategoryFragment extends BaseListFragment {

    private List<CategoryItemBean> mDataList;

    /**
     * 创建界面adpater
     */
    @Override
    public BaseAdapter onCreateAdapter() {
        return new CategoryAdapter(getContext(),mDataList);
    }

    @Override
    protected void startLoadData() {
        Call<List<CategoryItemBean>> listCall = HeiMaRetrofit.getInstance().getApi().listCategory();
        listCall.enqueue(new Callback<List<CategoryItemBean>>() {
            @Override
            public void onResponse(Call<List<CategoryItemBean>> call, Response<List<CategoryItemBean>> response) {
                mDataList = response.body();
                onDataLoadSuccess();
            }

            @Override
            public void onFailure(Call<List<CategoryItemBean>> call, Throwable t) {
                onDataLoadFailed();
            }
        });


    }
}
