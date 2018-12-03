package com.keke.hejia.agora.mode;

import android.content.Context;

import com.keke.hejia.util.LogUtils;

import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;

import io.agora.rtc.IRtcEngineEventHandler;

public class MyEngineEventHandler {
    public MyEngineEventHandler(Context ctx, EngineConfig config) {
        this.mContext = ctx;
        this.mConfig = config;
    }

    private final EngineConfig mConfig;

    private final Context mContext;



//           注册监听
    private final ConcurrentHashMap<AGEventHandler, Integer> mEventHandlerList = new ConcurrentHashMap<>();

    public void addEventHandler(AGEventHandler handler) {
        this.mEventHandlerList.put(handler, 0);
    }

    public void removeEventHandler(AGEventHandler handler) {
        this.mEventHandlerList.remove(handler);
    }

    public final IRtcEngineEventHandler mRtcEventHandler = new IRtcEngineEventHandler() {

//        已完成远端视频首帧解码回调。
        @Override
        public void onFirstRemoteVideoDecoded(int uid, int width, int height, int elapsed) {
            LogUtils.logd( "onFirstRemoteVideoDecoded " + (uid & 0xFFFFFFFFL) + " " + width + " " + height + " " + elapsed);

            Iterator<AGEventHandler> it = mEventHandlerList.keySet().iterator();
            while (it.hasNext()) {
                AGEventHandler handler = it.next();
                handler.onFirstRemoteVideoDecoded(uid, width, height, elapsed);
            }
        }

//        已发送本地视频首帧回调。
//
//       第一帧本地视频显示在视图上时，触发此回调。
        @Override
        public void onFirstLocalVideoFrame(int width, int height, int elapsed) {
            LogUtils.logd("onFirstLocalVideoFrame " + width + " " + height + " " + elapsed);
        }

//        远端用户/主播加入当前频道回调。
        @Override
        public void onUserJoined(int uid, int elapsed) {
            LogUtils.logd("onUserJoined " + (uid & 0xFFFFFFFFL) + " " + elapsed);

            Iterator<AGEventHandler> it = mEventHandlerList.keySet().iterator();
            while (it.hasNext()) {
                AGEventHandler handler = it.next();
                handler.onUserJoined(uid, elapsed);
            }
        }

//        远端用户（通信模式）/主播（直播模式）离开当前频道回调。
//
//提示有远端用户/主播离开了频道（或掉线）。用户离开频道有两个原因，即正常离开和超时掉线：

        @Override
        public void onUserOffline(int uid, int reason) {
            // FIXME this callback may return times
            Iterator<AGEventHandler> it = mEventHandlerList.keySet().iterator();
            while (it.hasNext()) {
                AGEventHandler handler = it.next();
                handler.onUserOffline(uid, reason);
            }
        }

//        远端用户暂停/重新发送视频流回调。
//
//提示有其他用户暂停/恢复了视频流的发送。
        @Override
        public void onUserMuteVideo(int uid, boolean muted) {
        }

//        当前通话统计回调。 该回调在通话中每两秒触发一次。
        @Override
        public void onRtcStats(RtcStats stats) {
        }


//        离开频道回调。
        @Override
        public void onLeaveChannel(RtcStats stats) {

        }

//        网络质量报告回调。
        @Override
        public void onLastmileQuality(int quality) {
            LogUtils.logd("onLastmileQuality " + quality);
        }

        @Override
        public void onError(int err) {
            super.onError(err);
            LogUtils.logd("onError " + err);
        }

        @Override
        public void onJoinChannelSuccess(String channel, int uid, int elapsed) {
            LogUtils.logd("onJoinChannelSuccess " + channel + " " + uid + " " + (uid & 0xFFFFFFFFL) + " " + elapsed);

            Iterator<AGEventHandler> it = mEventHandlerList.keySet().iterator();
            while (it.hasNext()) {
                AGEventHandler handler = it.next();
                handler.onJoinChannelSuccess(channel, uid, elapsed);
            }
        }

//        重新加入频道回调。
//
//有时候由于网络原因，客户端可能会和服务器失去连接，SDK 会进行自动重连，自动重连成功后触发此回调方法。

        public void onRejoinChannelSuccess(String channel, int uid, int elapsed) {
            LogUtils.logd("onRejoinChannelSuccess " + channel + " " + uid + " " + elapsed);
        }

//        发生警告回调。
//
//该回调方法表示 SDK 运行时出现了（网络或媒体相关的）警告。通常情况下，SDK 上报的警告信息 App 可以忽略，SDK 会自动恢复。 例如和服务
        public void onWarning(int warn) {
            LogUtils.logd("onWarning " + warn);
        }
    };

}
