package com.keke.hejia.activity;

import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.keke.hejia.R;
import com.keke.hejia.base.BaseActivity;
import com.keke.hejia.util.Utils;
import com.keke.hejia.widget.TitleBuilder;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 2019/11/29
 * y邀请
 * 张宇
 **/
public class InvitationActivity extends BaseActivity {

    @BindView(R.id.img_invitation_wxyaoqing)
    ImageView imgInvitationWxyaoqing;
    @BindView(R.id.view)
    View view;
    @BindView(R.id.tv_tips_yaqingma)
    TextView tvTipsYaqingma;
    @BindView(R.id.tv_tips_baohu)
    TextView tvTipsBaohu;
    @BindView(R.id.tv_invition_yaoqingma_tips)
    TextView tvInvitionYaoqingmaTips;
    @BindView(R.id.tv_yqm_code)
    TextView tvYqmCode;
    @BindView(R.id.tv_copy_cpde)
    TextView tvCopyCpde;

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
        return R.layout.activity_invitation;
    }

    @Override
    protected void initUI() {
        //设置下划线
        tvYqmCode.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); //下划线
        //不显示右边
        new TitleBuilder(this).setTitleText(getString(R.string.invitation_title)).setLeftIco(R.drawable.title_fanhui).setLeftIcoListening(leftReturnListener);
    }

    //左侧返回图片监听
    private View.OnClickListener leftReturnListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            finish();
        }
    };

    @OnClick({R.id.img_invitation_wxyaoqing, R.id.tv_invition_yaoqingma_tips, R.id.tv_copy_cpde})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_invitation_wxyaoqing:   //微信邀请
                break;
            case R.id.tv_copy_cpde:    //复制邀请码
                Utils.copyToClipboard(this, "ninisdfs");
                break;
        }
    }
}
