package com.keke.hejia.activity;

import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.keke.hejia.R;
import com.keke.hejia.base.BaseActivity;
import com.keke.hejia.util.ToastUitl;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity {

    @BindView(R.id.icon_img)
    ImageView iconImg;
    @BindView(R.id.edit_sr_phone)
    EditText editSrPhone;
    @BindView(R.id.edit_sms_code)
    EditText editSmsCode;
    @BindView(R.id.tv_sms)
    TextView tvSms;

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
        return R.layout.activity_login;
    }

    @Override
    protected void initUI() {
        initTitle();
    }


    /**
     * 2018/11/27
     * zhangyu
     * 透明式状态栏
     **/
    private void initTitle() {
        setHalfTransparent();
        setFitSystemWindow(true);
    }

    @OnClick({R.id.img_dissmiss, R.id.bt_sign_in, R.id.img_wx, R.id.tv_sms})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_dissmiss:
                break;
            case R.id.bt_sign_in:
                if (editSrPhone.getText().toString().equals("") || editSmsCode.getText().toString().equals("")) {
                    ToastUitl.show("请输入信息", 2);
                    return;
                }

                break;
            case R.id.img_wx:
                ToastUitl.show("目前不支持微信！！", 2);
                break;
            case R.id.tv_sms:
                if ("".equals(editSrPhone.getText().toString())) {
                    ToastUitl.show("请输入手机号", 2);
                    return;
                }
                restart();
                tvSms.setBackgroundResource(R.drawable.text_share_false);
                break;
        }
    }


    //释放倒计时器
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    //倒计时器

    /**
     * 取消倒计时
     *
     * @param v
     */
    public void oncancel(View v) {
        timer.cancel();
    }

    /**
     * 开始倒计时
     */
    public void restart() {
        timer.start();
    }

    @Override
    public void onBackPressed() {

    }

    private CountDownTimer timer = new CountDownTimer(60000, 1000) {

        @Override
        public void onTick(long millisUntilFinished) {
            if (tvSms == null) {
                return;
            }
            tvSms.setText("" + (millisUntilFinished / 1000) + "秒");
        }

        @Override
        public void onFinish() {
            if (tvSms == null) {
                return;
            }
            tvSms.setEnabled(true);
            tvSms.setBackgroundResource(R.drawable.text_bangding_shouji);
            tvSms.setText(getString(R.string.login_hq_yzm));
        }
    };


    /**
     * 如果需要内容紧贴着StatusBar
     * 应该在对应的xml布局文件中，设置根布局fitsSystemWindows=true。
     */
    private View contentViewGroup;

    protected void setFitSystemWindow(boolean fitSystemWindow) {
        if (contentViewGroup == null) {
            contentViewGroup = ((ViewGroup) findViewById(android.R.id.content)).getChildAt(0);
        }
        contentViewGroup.setFitsSystemWindows(fitSystemWindow);
    }

    //半透明状态栏
    protected void setHalfTransparent() {

        if (Build.VERSION.SDK_INT >= 21) {//21表示5.0
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        } else if (Build.VERSION.SDK_INT >= 19) {//19表示4.4
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //虚拟键盘也透明
            // getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }

}
