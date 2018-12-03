package com.keke.hejia;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.support.multidex.MultiDex;

import com.keke.hejia.agora.AgoraVideoService;
import com.keke.hejia.agora.AgoraVideoUtill;
import com.keke.hejia.api.Api;

import java.util.List;

public class HeJiaApp extends Application {
    public static HeJiaApp instance;


    //    网络请求 api 用来   清除 缓存和cooke
    public Api api;
    //    是否登录
    public static boolean isLogin=false;
    //    服务器时间戳
    private long timestampCorrection;

    public String uid="0";


    @Override
    public void onCreate() {
        super.onCreate();
        if (isMainProcess()) {
            instance=this;
            MultiDex.install(this);
            AgoraVideoUtill.getInstance();
            startService(new Intent(this,AgoraVideoService.class));
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

