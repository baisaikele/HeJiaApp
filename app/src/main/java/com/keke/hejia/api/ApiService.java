package com.keke.hejia.api;

import com.keke.hejia.api.bean.HJBaseEntity;
import com.keke.hejia.bean.ApiInitBean;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * Created by Administrator on 2018/7/2.
 */

public interface ApiService {


    @GET()
    Call<ResponseBody> getdata(@Url String url, @QueryMap Map<String, String> map);

    @GET
    Observable<HJBaseEntity<String>> getStatus(@Url String url, @QueryMap Map<String, String> map);

    //初始化
    @GET
    Observable<HJBaseEntity<ApiInitBean>> getInitApp(@Url String url, @QueryMap Map<String, String> map);
}
