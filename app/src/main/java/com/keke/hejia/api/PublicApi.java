package com.keke.hejia.api;

import com.keke.hejia.api.bean.RxHjDataObserver;
import com.keke.hejia.bean.ApiInitBean;

import java.util.HashMap;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class PublicApi {


    //初始化接口
    public static void getIntAPP(final ResponseListener listener) {
        HashMap<String, String> map1 = new HashMap<>();
        map1.put("platform", "ANDROID");
        HashMap<String, String> stringStringHashMap = Api.initMap(map1, Api.BaseUrl + ApiConstant.APP_INITIAL);
        Api.getDefault().getInitApp(ApiConstant.APP_INITIAL, stringStringHashMap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new RxHjDataObserver<ApiInitBean>() {
                    @Override
                    protected void onSuccees(ApiInitBean apiInitBean) throws Exception {
                        listener.success(apiInitBean);
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        listener.error(e.getMessage());

                    }
                });
    }


    public interface ResponseListener {
        void success(Object o);

        void error(String s);
    }

}
