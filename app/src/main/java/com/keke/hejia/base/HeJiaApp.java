package com.keke.hejia.base;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.keke.hejia.api.Api;

import java.util.List;

public class HeJiaApp extends Application {
    public static HeJiaApp instance;


    //    网络请求 api 用来   清除 缓存和cooke
    public Api api;
    //    是否登录
    public static boolean isLogin = false;
    //    服务器时间戳
    private long timestampCorrection;


    @Override
    public void onCreate() {
        super.onCreate();
        if (isMainProcess()) {
            instance = this;
            MultiDex.install(this);
        }

    }


    public boolean isMainProcess() {
        ActivityManager am = ((ActivityManager) getSystemService(Context.ACTIVITY_SERVICE));
        List<ActivityManager.RunningAppProcessInfo> processInfos = am.getRunningAppProcesses();
        String mainProcessName = getPackageName();
        int myPid = android.os.Process.myPid();
        for (ActivityManager.RunningAppProcessInfo info : processInfos) {
            if (info.pid == myPid && mainProcessName.equals(info.processName)) {
                return true;
            }
        }
        return false;
    }

    public long getTimestampCorrection() {
        return timestampCorrection;
    }

}

