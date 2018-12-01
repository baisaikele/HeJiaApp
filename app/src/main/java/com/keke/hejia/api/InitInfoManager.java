package com.keke.hejia.api;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.keke.hejia.base.HeJiaApp;
import com.keke.hejia.bean.ApiInitBean;
import com.keke.hejia.util.SharedPreferenceUtil;

public class InitInfoManager {
    public static ApiInitBean apiInitBean; //初始化获取的基本配置信息
    private static final String SPKEY_INITAPP = "ini_app";

    public InitInfoManager() {
    }

    private static InitInfoManager manager;

    public static InitInfoManager getInstance() {
        if (manager == null) {
            manager = new InitInfoManager();
        }
        return manager;
    }

    public void init(ApiInitBean bean) {
        String api = new Gson().toJson(bean);
        SharedPreferenceUtil.put(HeJiaApp.instance, SPKEY_INITAPP, api);
        apiInitBean = bean;
    }

    public ApiInitBean getApiIntBean() {
        if (apiInitBean == null) {
            String str = (String) SharedPreferenceUtil.get(HeJiaApp.instance, SPKEY_INITAPP, "");
            if (!TextUtils.isEmpty(str)) {
                Gson gson = new Gson();
                apiInitBean = gson.fromJson(str, ApiInitBean.class);
            } else {
                PublicApi.getIntAPP(new PublicApi.ResponseListener() {
                    @Override
                    public void success(Object o) {
                        ApiInitBean bean = (ApiInitBean) o;
                        init(bean);
                    }

                    @Override
                    public void error(String s) {

                    }
                });
            }
        }
        return apiInitBean;
    }

}
