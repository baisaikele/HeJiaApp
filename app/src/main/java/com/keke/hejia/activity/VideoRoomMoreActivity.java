package com.keke.hejia.activity;

import android.content.SharedPreferences;
import android.os.Build;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewStub;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.widget.RelativeLayout;

import com.keke.hejia.HeJiaApp;
import com.keke.hejia.R;
import com.keke.hejia.agora.AgoraVideoUtill;
import com.keke.hejia.agora.ConstantApp;
import com.keke.hejia.agora.mode.AGEventHandler;
import com.keke.hejia.agora.mode.VideoStatusData;
import com.keke.hejia.agora.weight.GridVideoViewContainer;
import com.keke.hejia.agora.weight.VideoViewEventListener;
import com.keke.hejia.base.BaseActivity;
import com.keke.hejia.util.LogUtils;

import java.util.HashMap;

import io.agora.rtc.Constants;
import io.agora.rtc.RtcEngine;
import io.agora.rtc.video.VideoCanvas;
import io.agora.rtc.video.VideoEncoderConfiguration;

public class VideoRoomMoreActivity extends BaseActivity implements AGEventHandler {



    @Override
    public void initData() {

    }

    @Override
    public void getIntentData() {

    }

    @Override
    public int getLayoutResId() {
        return R.layout.activity_video_room_more;
    }

    @Override
    protected void initUI() {

        AgoraVideoUtill.getInstance().eventHandler().addEventHandler(this);
        final View layout = findViewById(Window.ID_ANDROID_CONTENT);
        ViewTreeObserver vto = layout.getViewTreeObserver();
//
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    layout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                } else {
                    layout.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                }
                initUIandEvent();
            }
        });
    }














    private GridVideoViewContainer mGridVideoViewContainer;
    private RelativeLayout mSmallVideoViewDock;
//    private int cRole=Constants.CLIENT_ROLE_AUDIENCE;
    private int cRole=Constants.CLIENT_ROLE_BROADCASTER;

    private String channle_room="1234";

    private final HashMap<Integer, SurfaceView> mUidsList = new HashMap<>(); // uid = 0 || uid == EngineConfig.mUid


    public int mViewType = VIEW_TYPE_DEFAULT;

    public static final int VIEW_TYPE_DEFAULT = 0;

    public static final int VIEW_TYPE_SMALL = 1;

    private void initUIandEvent() {


        doConfigEngine(cRole);

        mGridVideoViewContainer = (GridVideoViewContainer) findViewById(R.id.grid_video_view_container);
        mGridVideoViewContainer.setItemEventHandler(new VideoViewEventListener() {
            @Override
            public void onItemDoubleClick(View v, Object item) {

                if (mUidsList.size() < 2) {
                    return;
                }

            }
        });

        if (isBroadcaster()) {
            mUidsList.put(0, AgoraVideoUtill.getInstance().setupLocalVideo(this,new Integer(HeJiaApp.instance.uid))); // get first surface view
            AgoraVideoUtill.getInstance().preview(true,mUidsList.get(0),new Integer(HeJiaApp.instance.uid));
            mGridVideoViewContainer.initViewContainer(getApplicationContext(), 0, mUidsList); // first is now full view

        } else {
        }

        AgoraVideoUtill.getInstance().joinChannel(channle_room,new Integer( HeJiaApp.instance.uid));


    }

    @Override
    public void onFirstRemoteVideoDecoded(int uid, int width, int height, int elapsed) {

    }

    @Override
    public void onJoinChannelSuccess(String channel, int uid, int elapsed) {
        if (isFinishing()) {
            return;
        }

        if (mUidsList.containsKey(uid)) {
            LogUtils.logd("already added to UI, ignore it " + (uid & 0xFFFFFFFFL) + " " + mUidsList.get(uid));
            return;
        }

        final boolean isBroadcaster = isBroadcaster();
        LogUtils.logd("onJoinChannelSuccess " + channel + " " + uid + " " + elapsed + " " + isBroadcaster);

        AgoraVideoUtill.getInstance().getEngineConfig().mUid = uid;

        SurfaceView surfaceV = mUidsList.remove(0);
        if (surfaceV != null) {
            mUidsList.put(uid, surfaceV);
        }

    }

    @Override
    public void onUserOffline(int uid, int reason) {

       LogUtils.logd("onUserOffline " + (uid & 0xFFFFFFFFL) + " " + reason);
       doRemoveRemoteUi(uid);
    }

    @Override
    public void onUserJoined(int uid, int elapsed) {
        doRenderRemoteUi(uid);

        Log.e("@@@@@@@@@@", "onUserJoined: " );
    }



    private void doRemoveRemoteUi(final int uid) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (isFinishing()) {
                    return;
                }

                mUidsList.remove(uid);

                int bigBgUid = -1;

                LogUtils.logd("doRemoveRemoteUi " + (uid & 0xFFFFFFFFL) + " " + (bigBgUid & 0xFFFFFFFFL));

                switchToDefaultVideoView();

            }
        });
    }

    private void doRenderRemoteUi(final int uid) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (isFinishing()) {
                    return;
                }

                SurfaceView surfaceV ;
                if (AgoraVideoUtill.getInstance().getEngineConfig().mUid == uid) {
                   surfaceV= AgoraVideoUtill.getInstance().setupLocalVideo(VideoRoomMoreActivity.this,uid);
                } else {
                  surfaceV=  AgoraVideoUtill.getInstance().setupRemoteVideo(VideoRoomMoreActivity.this,uid);
                }

                mUidsList.put(uid, surfaceV);
                switchToDefaultVideoView();
            }
        });
    }



    private void switchToDefaultVideoView() {
        if (mSmallVideoViewDock != null)
            mSmallVideoViewDock.setVisibility(View.GONE);
        mGridVideoViewContainer.initViewContainer(getApplicationContext(), AgoraVideoUtill.getInstance().getEngineConfig().mUid, mUidsList);

        mViewType = VIEW_TYPE_DEFAULT;

        int sizeLimit = mUidsList.size();
        if (sizeLimit > ConstantApp.MAX_PEER_COUNT + 1) {
            sizeLimit = ConstantApp.MAX_PEER_COUNT + 1;
        }
        for (int i = 0; i < sizeLimit; i++) {
            int uid = mGridVideoViewContainer.getItem(i).mUid;
            if (AgoraVideoUtill.getInstance().getEngineConfig().mUid != uid) {
                AgoraVideoUtill.getInstance().getRtcEngine().setRemoteVideoStreamType(uid, Constants.VIDEO_STREAM_HIGH);
                LogUtils.logd("setRemoteVideoStreamType VIDEO_STREAM_HIGH " + mUidsList.size() + " " + (uid & 0xFFFFFFFFL));
            }
        }
    }
    //  是否是 主播
    private boolean isBroadcaster() {
        return cRole == Constants.CLIENT_ROLE_BROADCASTER;
    }



    //  设置视频 类型
    private void doConfigEngine(int cRole) {
        AgoraVideoUtill.getInstance().configEngine(cRole,
                new VideoEncoderConfiguration(VideoEncoderConfiguration.VD_640x360,
                        VideoEncoderConfiguration.FRAME_RATE.FRAME_RATE_FPS_15,
                        VideoEncoderConfiguration.STANDARD_BITRATE,
                        VideoEncoderConfiguration.ORIENTATION_MODE.ORIENTATION_MODE_FIXED_PORTRAIT));
    }

    protected void deInitUIandEvent() {
        AgoraVideoUtill.getInstance().eventHandler().removeEventHandler(this);

        mUidsList.clear();
        AgoraVideoUtill.getInstance().leaveChannel(channle_room);
        if (isBroadcaster()) {
            AgoraVideoUtill.getInstance().preview(false, null, 0);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        deInitUIandEvent();
    }
}
