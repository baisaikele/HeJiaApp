package com.keke.hejia.agora;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import com.keke.hejia.R;

import io.agora.rtc.IRtcEngineEventHandler;
import io.agora.rtc.RtcEngine;

public class AgoraVideoService extends Service {


    private static final String TAG = "AgoraVideoService";


    private final IRtcEngineEventHandler mRtcEventHandler = new IRtcEngineEventHandler() { // Tutorial Step 1
        @Override
        public void onFirstRemoteVideoDecoded(final int uid, int width, int height, int elapsed) { // Tutorial Step 5
//            runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
////                    setupRemoteVideo(uid);
//                }
//            });
        }

        @Override
        public void onUserOffline(int uid, int reason) { // Tutorial Step 7
//            runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
////                    onRemoteUserLeft();
//                }
//            });
        }

        @Override
        public void onUserMuteVideo(final int uid, final boolean muted) { // Tutorial Step 10
//            runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
////                    onRemoteUserVideoMuted(uid, muted);
//                }
//            });
        }
    };
    private RtcEngine mRtcEngine;
    private final AgoraVideoBinder    videoBinder = new AgoraVideoBinder();

    @Override
    public void onCreate() {
        super.onCreate();
        initializeAgoraEngine();
    }



    private void initializeAgoraEngine() {
        try {
            mRtcEngine = RtcEngine.create(getApplicationContext(), getString(R.string.agora_app_id), mRtcEventHandler);
        } catch (Exception e) {
            Log.e(TAG, Log.getStackTraceString(e));

            throw new RuntimeException("NEED TO check rtc sdk init fatal error\n" + Log.getStackTraceString(e));
        }
    }


    @Override
    public IBinder onBind(Intent intent) {
        return videoBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    public class AgoraVideoBinder  extends Binder{
        public RtcEngine getRtcEngine(){
            return mRtcEngine;
        }
    }




}
