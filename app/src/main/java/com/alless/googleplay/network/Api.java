package com.alless.googleplay.network;

import com.alless.googleplay.bean.AppListItemBean;
import com.alless.googleplay.bean.CategoryItemBean;
import com.alless.googleplay.bean.SubjectItemBean;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Administrator on 2017/3/26.
 */

public interface Api {
    /**
     * 泛型T你想要解析后数据结构
     */
    @GET("hot")
    Call<List<String>> listHot();

    @GET("recommend")
    Call<List<String>> listRecommed();

    @GET("category")
    Call<List<CategoryItemBean>> listCategory();

    @GET("subject")
    Call<List<SubjectItemBean>> listSubject(@Query("index") int index);

    @GET("game")
    Call<List<AppListItemBean>> listGame(@Query("index") int index);

    @GET("app")
    Call<List<AppListItemBean>> listApp(@Query("index") int index);

}
