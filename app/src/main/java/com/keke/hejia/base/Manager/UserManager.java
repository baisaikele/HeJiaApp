package com.keke.hejia.base.Manager;

import android.content.Intent;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.keke.hejia.base.HeJiaApp;
import com.keke.hejia.bean.LoginBean;
import com.keke.hejia.util.SharedPreferenceUtil;

public class UserManager {

    private static UserManager sUserManager;
    private static final String USERINFO = "userinfo";
    public static boolean is_new = false;   //判断是否新用户
//    private static boolean isLogin = false;


    public static UserManager getInstance() {
        if (sUserManager == null) {
            synchronized (UserManager.class) {
                if (sUserManager == null) {
                    sUserManager = new UserManager();
                }
            }
        }
        return sUserManager;
    }

    /**
     * 登录
     *
     * @param loginBean
     */
    public void login(LoginBean loginBean) {

    }


}
