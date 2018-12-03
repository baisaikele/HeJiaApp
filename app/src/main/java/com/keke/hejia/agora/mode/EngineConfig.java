package com.keke.hejia.agora.mode;

import io.agora.rtc.video.VideoEncoderConfiguration;

public class EngineConfig {
    public int mClientRole;

    public VideoEncoderConfiguration mVideoProfile;

    public int mUid;

    public String mChannel;

    public void reset() {
        mChannel = null;
    }

    public  EngineConfig() {
    }
}
