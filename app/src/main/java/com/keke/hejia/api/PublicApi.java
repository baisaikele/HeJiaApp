package com.keke.hejia.api;

import com.keke.hejia.api.bean.RxHjDataObserver;
import com.keke.hejia.bean.ApiInitBean;
import com.keke.hejia.bean.LoginBean;
import com.keke.hejia.bean.SmsLoginBean;

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

    //获取验证码接口     method   1手机号登录  2绑定手机号
    public static void getSmsData(String phone, final ResponseListener listener) {
        HashMap<String, String> map = new HashMap<>();
        map.put("phone", phone);
        map.put("method", "1");
        map.put("sms_type", "0");
        HashMap<String, String> stringStringHashMap = Api.initMap(map, Api.BaseUrl + ApiConstant.USER_SMS_CODE);
        Api.getDefault().getSms(ApiConstant.USER_SMS_CODE, stringStringHashMap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new RxHjDataObserver<SmsLoginBean>() {
                    @Override
                    protected void onSuccees(SmsLoginBean bean) throws Exception {
                        listener.success(bean);
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
