package com.keke.hejia.api;

import com.keke.hejia.api.bean.RxHjDataObserver;
import com.keke.hejia.bean.ApiInitBean;
import com.keke.hejia.bean.LoginBean;

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


    //登录接口
    public static void getLogin(String user_key, String openid, String source, String nickname, String smsCode, final ResponseListener listener) {
        HashMap<String, String> map1 = new HashMap<>();
        map1.put("user_key", user_key);
        map1.put("openid", openid);
        map1.put("source", source);
        map1.put("nickname", nickname);
        map1.put("sms_code", smsCode);
        map1.put("platform", "ANDROID");
        HashMap<String, String> stringStringHashMap = Api.initMap(map1, Api.BaseUrl + ApiConstant.USER_LOGIN);
        Api.getDefault().getLogin(ApiConstant.USER_LOGIN, stringStringHashMap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new RxHjDataObserver<LoginBean>() {
                    @Override
                    protected void onSuccees(LoginBean loginBean) throws Exception {
                        listener.success(loginBean);
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
