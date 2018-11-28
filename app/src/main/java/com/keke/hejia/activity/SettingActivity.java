package com.keke.hejia.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.keke.hejia.R;
import com.keke.hejia.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SettingActivity extends BaseActivity {


    @BindView(R.id.tv_wx_bangding_setting)  //微信
            TextView tvWxBangdingSetting;
    @BindView(R.id.tv_phone_bangding_setting)  //手机
            TextView tvPhoneBangdingSetting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }

    @Override
    public void initData() {

    }

    @Override
    public void getIntentData() {

    }

    @Override
    public int getLayoutResId() {
        return R.layout.activity_setting;
    }

    @Override
    protected void initUI() {

    }

    @OnClick({R.id.img_setting_fh, R.id.tv_wx_bangding_setting, R.id.tv_phone_bangding_setting, R.id.rl_ghysjt_setting, R.id.rl_ghxsjt_setting, R.id.rl_gy_setting, R.id.rl_lxwm_setting, R.id.bt_tuichu_setting})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_setting_fh:   //返回
                break;
            case R.id.tv_wx_bangding_setting://绑定微信
                break;
            case R.id.tv_phone_bangding_setting: //绑定手机
                break;
            case R.id.rl_ghysjt_setting:  //退出原生
                break;
            case R.id.rl_ghxsjt_setting:   //退出新生
                break;
            case R.id.rl_gy_setting:  //  关于我们
                break;
            case R.id.rl_lxwm_setting:  //联系我们
                break;
            case R.id.bt_tuichu_setting:  //退出登录
                break;
        }
    }
}
