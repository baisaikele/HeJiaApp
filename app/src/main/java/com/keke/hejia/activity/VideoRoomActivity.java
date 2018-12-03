package com.keke.hejia.activity;

import android.Manifest;
import android.content.ComponentName;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.keke.hejia.R;
import com.keke.hejia.agora.AgoraVideoService;
import com.keke.hejia.agora.AgoraVideoUtill;
import com.keke.hejia.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.agora.rtc.Constants;
import io.agora.rtc.IRtcEngineEventHandler;
import io.agora.rtc.RtcEngine;
import io.agora.rtc.video.VideoCanvas;
import io.agora.rtc.video.VideoEncoderConfiguration;

public class VideoRoomActivity extends BaseActivity {


    private static final String LOG_TAG = VideoRoomActivity.class.getSimpleName();

    private static final int PERMISSION_REQ_ID = 22;

    private static final String[] REQUESTED_PERMISSIONS = {Manifest.permission.RECORD_AUDIO, Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
    @BindView(R.id.remote_video_view_container)
    FrameLayout remoteVideoViewContainer;
    @BindView(R.id.local_video_view_container)
    FrameLayout localVideoViewContainer;

    private RtcEngine mRtcEngine; // Tutorial Step 1
    private final IRtcEngineEventHandler mRtcEventHandler = new IRtcEngineEventHandler() { // Tutorial Step 1
        @Override
        public void onFirstRemoteVideoDecoded(final int uid, int width, int height, int elapsed) { // Tutorial Step 5
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    setupRemoteVideo(uid);
                }
            });
        }

        @Override
        public void onUserOffline(int uid, int reason) { // Tutorial Step 7
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    onRemoteUserLeft();
                }
            });
        }

        @Override
        public void onUserMuteVideo(final int uid, final boolean muted) { // Tutorial Step 10
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    onRemoteUserVideoMuted(uid, muted);
                }
            });
        }
    };


    @Override
    public void initData() {

    }

    @Override
    public void getIntentData() {

    }

    @Override
    public int getLayoutResId() {
        return R.layout.activity_video_room;
    }

    @Override
    protected void initUI() {
        if (checkSelfPermission(REQUESTED_PERMISSIONS[0], PERMISSION_REQ_ID) &&
                checkSelfPermission(REQUESTED_PERMISSIONS[1], PERMISSION_REQ_ID) &&
                checkSelfPermission(REQUESTED_PERMISSIONS[2], PERMISSION_REQ_ID)) {
            initAgoraEngineAndJoinChannel();
        }
    }

    private void initAgoraEngineAndJoinChannel() {
//        AgoraVideoUtill.getInstance().setupLocalVideo(this,localVideoViewContainer);
//        AgoraVideoUtill.getInstance().setupVideoProfile();
//        AgoraVideoUtill.getInstance().joinChannel(null,"demoChannel2","",0);
    }

    public boolean checkSelfPermission(String permission, int requestCode) {
        Log.i(LOG_TAG, "checkSelfPermission " + permission + " " + requestCode);
        if (ContextCompat.checkSelfPermission(this,
                permission)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    REQUESTED_PERMISSIONS,
                    requestCode);
            return false;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[], @NonNull int[] grantResults) {
        Log.i(LOG_TAG, "onRequestPermissionsResult " + grantResults[0] + " " + requestCode);

        switch (requestCode) {
            case PERMISSION_REQ_ID: {
                if (grantResults[0] != PackageManager.PERMISSION_GRANTED || grantResults[1] != PackageManager.PERMISSION_GRANTED || grantResults[2] != PackageManager.PERMISSION_GRANTED) {
                    showLongToast("Need permissions " + Manifest.permission.RECORD_AUDIO + "/" + Manifest.permission.CAMERA + "/" + Manifest.permission.WRITE_EXTERNAL_STORAGE);
                    finish();
                    break;
                }

                initAgoraEngineAndJoinChannel();
                break;
            }
        }
    }

    public final void showLongToast(final String msg) {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

//        AgoraVideoUtill.getInstance().leaveChannel();
        AgoraVideoUtill.getInstance().destroy();

    }
    // Tutorial Step 3
    private void setupLocalVideos() {
//        FrameLayout container = (FrameLayout) findViewById(R.id.local_video_view_container);
        SurfaceView surfaceView = RtcEngine.CreateRendererView(getBaseContext());
        surfaceView.setZOrderMediaOverlay(true);
        localVideoViewContainer.addView(surfaceView);
        mRtcEngine.setupLocalVideo(new VideoCanvas(surfaceView, VideoCanvas.RENDER_MODE_FIT, 0));
    }


    // Tutorial Step 5
    private void setupRemoteVideo(int uid) {
        FrameLayout container = (FrameLayout) findViewById(R.id.remote_video_view_container);

        if (container.getChildCount() >= 1) {
            return;
        }
        SurfaceView surfaceView = RtcEngine.CreateRendererView(getBaseContext());
        container.addView(surfaceView);
        mRtcEngine.setupRemoteVideo(new VideoCanvas(surfaceView, VideoCanvas.RENDER_MODE_FIT, uid));

        surfaceView.setTag(uid); // for mark purpose
        View tipMsg = findViewById(R.id.quick_tips_when_use_agora_sdk); // optional UI
        tipMsg.setVisibility(View.GONE);
    }



    // Tutorial Step 7
    private void onRemoteUserLeft() {
        FrameLayout container = (FrameLayout) findViewById(R.id.remote_video_view_container);
        container.removeAllViews();

        View tipMsg = findViewById(R.id.quick_tips_when_use_agora_sdk); // optional UI
        tipMsg.setVisibility(View.VISIBLE);
    }

    // Tutorial Step 10
    private void onRemoteUserVideoMuted(int uid, boolean muted) {
        FrameLayout container = (FrameLayout) findViewById(R.id.remote_video_view_container);

        SurfaceView surfaceView = (SurfaceView) container.getChildAt(0);

        Object tag = surfaceView.getTag();
        if (tag != null && (Integer) tag == uid) {
            surfaceView.setVisibility(muted ? View.GONE : View.VISIBLE);
        }
    }

    private boolean mBound = false;
    private AgoraVideoService.AgoraVideoBinder videoBinder;
    private boolean isAnchor = false;


    private ServiceConnection mConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            videoBinder = ((AgoraVideoService.AgoraVideoBinder) service);
            mRtcEngine = videoBinder.getRtcEngine();
            mRtcEngine.setChannelProfile(Constants.CHANNEL_PROFILE_LIVE_BROADCASTING);
            mBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mBound = false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
