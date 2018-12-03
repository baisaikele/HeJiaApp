package com.keke.hejia.agora;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.annotation.WorkerThread;
import android.text.TextUtils;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;
import android.widget.FrameLayout;

import com.keke.hejia.HeJiaApp;
import com.keke.hejia.R;
import com.keke.hejia.agora.mode.EngineConfig;
import com.keke.hejia.agora.mode.MyEngineEventHandler;
import com.keke.hejia.util.LogUtils;

import java.io.File;

import io.agora.rtc.Constants;
import io.agora.rtc.IRtcEngineEventHandler;
import io.agora.rtc.RtcEngine;
import io.agora.rtc.video.VideoCanvas;
import io.agora.rtc.video.VideoEncoderConfiguration;

public class AgoraVideoUtill extends Thread {



    private static volatile AgoraVideoUtill instance=null;
    private   RtcEngine mRtcEngine=null;



//    初始化
    private AgoraVideoUtill(){

//        新建一个 本地直播信息
        this.mEngineConfig = new EngineConfig();


        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(HeJiaApp.instance);
        this.mEngineConfig.mUid = pref.getInt(ConstantApp.PrefManager.PREF_PROPERTY_UID, 0);
        this.mEngineEventHandler = new MyEngineEventHandler(HeJiaApp.instance, this.mEngineConfig);

    }


//    杀死
    public void deInitWorkerThread() {
        instance.exit();
        try {
            instance.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        instance = null;
    }


    public static  AgoraVideoUtill getInstance(){
        if(instance==null){
            synchronized(RtcEngine.class){
                if(instance==null){
                    instance= new  AgoraVideoUtill();
                    instance.start();
//                    instance.waitForReady();
                }
            }
        }
        return instance;
    }

    private static final int ACTION_WORKER_THREAD_QUIT = 0X1010; // quit this thread

    private static final int ACTION_WORKER_JOIN_CHANNEL = 0X2010;

    private static final int ACTION_WORKER_LEAVE_CHANNEL = 0X2011;

    private static final int ACTION_WORKER_CONFIG_ENGINE = 0X2012;

    private static final int ACTION_WORKER_PREVIEW = 0X2014;

    private static final class WorkerThreadHandler extends Handler {

        private AgoraVideoUtill mWorkerThread;

        WorkerThreadHandler(AgoraVideoUtill thread) {
            this.mWorkerThread = thread;
        }

        public void release() {
            mWorkerThread = null;
        }

        @Override
        public void handleMessage(Message msg) {
            if (this.mWorkerThread == null) {
                LogUtils.loge("handler is already released! " + msg.what);
                return;
            }

            switch (msg.what) {
                case ACTION_WORKER_THREAD_QUIT:
                    mWorkerThread.exit();
                    break;
                case ACTION_WORKER_JOIN_CHANNEL:
                    String[] data = (String[]) msg.obj;
                    mWorkerThread.joinChannel(data[0], msg.arg1);
                    break;
                case ACTION_WORKER_LEAVE_CHANNEL:
                    String channel = (String) msg.obj;
                    mWorkerThread.leaveChannel(channel);
                    break;
                case ACTION_WORKER_CONFIG_ENGINE:
                    Object[] configData = (Object[]) msg.obj;
                    mWorkerThread.configEngine((int) configData[0], (VideoEncoderConfiguration) configData[1]);
                    break;
                case ACTION_WORKER_PREVIEW:
                    Object[] previewData = (Object[]) msg.obj;
                    mWorkerThread.preview((boolean) previewData[0], (SurfaceView) previewData[1], (int) previewData[2]);
                    break;
            }
        }
    }

    private WorkerThreadHandler mWorkerHandler;

    private boolean mReady;

    public final void waitForReady() {
        while (!mReady) {
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
//            LogUtils.logd("wait for " + WorkerThread.class.getSimpleName());

        }
    }

    @Override
    public void run() {
        LogUtils.logd("start to run");
        Looper.prepare();

        mWorkerHandler = new WorkerThreadHandler(this);

        ensureRtcEngineReadyLock();

        mReady = true;

        // enter thread looper
        Looper.loop();
    }



    public final void joinChannel(final String channel, int uid) {
        if (Thread.currentThread() != this) {
            LogUtils.logd("joinChannel() - worker thread asynchronously " + channel + " " + uid);
            Message envelop = new Message();
            envelop.what = ACTION_WORKER_JOIN_CHANNEL;
            envelop.obj = new String[]{channel};
            envelop.arg1 = uid;
            mWorkerHandler.sendMessage(envelop);
            return;
        }

        ensureRtcEngineReadyLock();
        mRtcEngine.joinChannel(null, channel, "OpenLive", uid);

        mEngineConfig.mChannel = channel;

        LogUtils.logd("joinChannel " + channel + " " + uid);
    }

    public final void leaveChannel(String channel) {
        if (Thread.currentThread() != this) {
            LogUtils.logd("leaveChannel() - worker thread asynchronously " + channel);
            Message envelop = new Message();
            envelop.what = ACTION_WORKER_LEAVE_CHANNEL;
            envelop.obj = channel;
            mWorkerHandler.sendMessage(envelop);
            return;
        }

        if (mRtcEngine != null) {
            mRtcEngine.leaveChannel();
        }

        int clientRole = mEngineConfig.mClientRole;
        mEngineConfig.reset();
        LogUtils.logd("leaveChannel " + channel + " " + clientRole);
    }

    private EngineConfig mEngineConfig;

    public final EngineConfig getEngineConfig() {
        return mEngineConfig;
    }

    private final MyEngineEventHandler mEngineEventHandler;


    /**
     *      初始化视频 格式
     * @param cRole
     * @param vProfile
     */

    public final void configEngine(int cRole, VideoEncoderConfiguration vProfile) {
        if (Thread.currentThread() != this) {
            LogUtils.logd("configEngine() - worker thread asynchronously " + cRole + " " + vProfile);
            Message envelop = new Message();
            envelop.what = ACTION_WORKER_CONFIG_ENGINE;
            envelop.obj = new Object[]{cRole, vProfile};
            mWorkerHandler.sendMessage(envelop);
            return;
        }

        ensureRtcEngineReadyLock();
        mEngineConfig.mClientRole = cRole;
        mEngineConfig.mVideoProfile = vProfile;

//        mRtcEngine.setVideoProfile(mEngineConfig.mVideoProfile, true);
        mRtcEngine.setVideoEncoderConfiguration(mEngineConfig.mVideoProfile);

        mRtcEngine.setClientRole(cRole);

        LogUtils.logd("configEngine " + cRole + " " + mEngineConfig.mVideoProfile);
    }


    /**
     *       加入直播间的准备
     * @param start
     * @param view
     * @param uid
     */
    public final void preview(boolean start, SurfaceView view, int uid) {
        if (Thread.currentThread() != this) {
            LogUtils.logd("preview() - worker thread asynchronously " + start + " " + view + " " + (uid & 0XFFFFFFFFL));
            Message envelop = new Message();
            envelop.what = ACTION_WORKER_PREVIEW;
            envelop.obj = new Object[]{start, view, uid};
            mWorkerHandler.sendMessage(envelop);
            return;
        }

        ensureRtcEngineReadyLock();
        if (start) {
            mRtcEngine.setupLocalVideo(new VideoCanvas(view, VideoCanvas.RENDER_MODE_HIDDEN, uid));
            mRtcEngine.startPreview();
        } else {
            mRtcEngine.stopPreview();
        }
    }

    public static String getDeviceID(Context context) {
        // XXX according to the API docs, this value may change after factory reset
        // use Android id as device id
        return Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    private RtcEngine ensureRtcEngineReadyLock() {
        if (mRtcEngine == null) {
            String appId = HeJiaApp.instance.getString(R.string.agora_app_id);
            if (TextUtils.isEmpty(appId)) {
                throw new RuntimeException("NEED TO use your App ID, get your own ID at https://dashboard.agora.io/");
            }
            try {
                mRtcEngine = RtcEngine.create(HeJiaApp.instance, appId, mEngineEventHandler.mRtcEventHandler);
            } catch (Exception e) {
                LogUtils.logd(Log.getStackTraceString(e));
                throw new RuntimeException("NEED TO check rtc sdk init fatal error\n" + Log.getStackTraceString(e));
            }
            mRtcEngine.setChannelProfile(Constants.CHANNEL_PROFILE_LIVE_BROADCASTING);
            mRtcEngine.enableVideo();
            mRtcEngine.setLogFile(Environment.getExternalStorageDirectory()
                    + File.separator + HeJiaApp.instance.getPackageName() + "/log/agora-rtc.log");
            mRtcEngine.enableDualStreamMode(true);
        }
        return mRtcEngine;
    }

    public MyEngineEventHandler eventHandler() {
        return mEngineEventHandler;
    }

    public RtcEngine getRtcEngine() {
        return mRtcEngine;
    }

    /**
     * call this method to exit
     * should ONLY call this method when this thread is running
     */
    public final void exit() {
        if (Thread.currentThread() != this) {
            LogUtils.logd("exit() - exit app thread asynchronously");
            mWorkerHandler.sendEmptyMessage(ACTION_WORKER_THREAD_QUIT);
            return;
        }

        mReady = false;

        // TODO should remove all pending(read) messages

        LogUtils.logd("exit() > start");

        // exit thread looper
        Looper.myLooper().quit();

        mWorkerHandler.release();

        LogUtils.logd("exit() > end");
    }




    /**
     *         本地视频 推送到那个framelayout  （）最后一个
     * @param context
     *
     */
    public SurfaceView setupLocalVideo(Context context,int uid) {
        SurfaceView surfaceView = RtcEngine.CreateRendererView(context);
        Log.e("@@", "setupLocalVideo: " );
        surfaceView.setZOrderOnTop(true);
        surfaceView.setZOrderMediaOverlay(true);
        mRtcEngine.setupLocalVideo(new VideoCanvas(surfaceView, VideoCanvas.RENDER_MODE_FIT, uid));
        return surfaceView;
    }
//
//
    /**
     *  别人加入房间
     * @param uid
     */
    public SurfaceView setupRemoteVideo(Context context,int uid) {

        SurfaceView surfaceV = RtcEngine.CreateRendererView(context);
        surfaceV.setZOrderOnTop(true);
        surfaceV.setZOrderMediaOverlay(true);
        mRtcEngine.setupRemoteVideo(new VideoCanvas(surfaceV, VideoCanvas.RENDER_MODE_FIT, uid));
        return surfaceV;

    }











//    private final IRtcEngineEventHandler mRtcEventHandler = new IRtcEngineEventHandler() { // Tutorial Step 1
//        @Override
//        public void onFirstRemoteVideoDecoded(final int uid, int width, int height, int elapsed) { // Tutorial Step 5
////            runOnUiThread(new Runnable() {
////                @Override
////                public void run() {
////                    setupRemoteVideo(uid);
////                }
////            });
//        }
//
//        @Override
//        public void onUserOffline(int uid, int reason) { // Tutorial Step 7
////            runOnUiThread(new Runnable() {
////                @Override
////                public void run() {
////                    onRemoteUserLeft();
////                }
////            });
//        }
//
//        @Override
//        public void onUserMuteVideo(final int uid, final boolean muted) { // Tutorial Step 10
////            runOnUiThread(new Runnable() {
////                @Override
////                public void run() {
////                    onRemoteUserVideoMuted(uid, muted);
////                }
////            });
//        }
//    };
//
//
//
//
//
////     打开视频模式
////     可以修改视频大小
//
//    public void setupVideoProfile() {
//        mRtcEngine.enableVideo();
//        mRtcEngine.setVideoEncoderConfiguration(new VideoEncoderConfiguration(VideoEncoderConfiguration.VD_640x360, VideoEncoderConfiguration.FRAME_RATE.FRAME_RATE_FPS_15,
//                VideoEncoderConfiguration.STANDARD_BITRATE,
//                VideoEncoderConfiguration.ORIENTATION_MODE.ORIENTATION_MODE_FIXED_PORTRAIT));
//    }
//
//

//
//
//
//
//    // 加入房间
//
//    /**
//     *
//     * @param token       传入能标识用户角色和权限的 Token。如果安全要求不高，也可以将值设为 null。
//     * @param channel_id  传入能标识频道的频道 ID。
//     * @param data
//     * @param uid         频道内每个用户的 UID 必须是唯一的。如果将 UID 设为 0，系统将自动分配一个 UID。
//     */
//    public void joinChannel(String token,String channel_id,String data,int uid) {
//        mRtcEngine.joinChannel(token, channel_id, data, uid); // if you do not specify the uid, Agora will assign one.
//
//    }
//
//    // 离开
//    public void leaveChannel() {
//        mRtcEngine.leaveChannel();
//    }
//
////     别人离开
//    public void clearFragment(FrameLayout frameLayout){
//        frameLayout.removeAllViews();
//    }
//
//
//
//    // 销毁
//    public void destroy(){
//        RtcEngine.destroy();
//        mRtcEngine = null;
//    }
//
//    /**
//     * 切换摄像头
//     */
//    public void onSwitchCamera() {
//        mRtcEngine.switchCamera();
//    }
//
//
//    /**
//     *   是否上传本地音频
//     * @param isMute
//     */
//    public void muteLocalAudioStream(boolean isMute){
//        mRtcEngine.muteLocalAudioStream(isMute);
//    }
//
//    /**
//     *   是否上传本地视频
//     * @param isMute
//     */
//    public void muteLocalVideoStream(boolean isMute){
//        mRtcEngine.muteLocalVideoStream(isMute);
//    }



}
