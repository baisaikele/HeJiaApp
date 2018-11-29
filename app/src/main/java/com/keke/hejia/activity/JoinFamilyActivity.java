package com.keke.hejia.activity;

import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.VideoView;

import com.keke.hejia.R;
import com.keke.hejia.base.BaseActivity;
import com.keke.hejia.customizeView.MyFamilyJoinDialog;
import com.keke.hejia.util.ToastUitl;
import com.keke.hejia.widget.TitleBuilder;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class JoinFamilyActivity extends BaseActivity {

    @BindView(R.id.title_leftIco)
    ImageView titleLeftIco;
    @BindView(R.id.title_text)
    TextView titleText;
    @BindView(R.id.title_rightIco)
    ImageView titleRightIco;
    @BindView(R.id.title_sure)
    TextView titleSure;
    @BindView(R.id.title_bar)
    RelativeLayout titleBar;
    @BindView(R.id.tv_join_family_qyq)
    TextView tvJoinFamilyQyq;
    @BindView(R.id.edit_join_family_code)
    EditText editJoinFamilyCode;
    @BindView(R.id.tv_join_family_sure)
    TextView tvJoinFamilySure;
    @BindView(R.id.tv_join_family_chenggong)
    TextView tvJoinFamilyChenggong;
    @BindView(R.id.video_view)
    VideoView videoView;

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
        return R.layout.activity_join_family;
    }

    @Override
    protected void initUI() {
        //显示右边文字
        new TitleBuilder(this).setTitleText(getString(R.string.joinfamily_title)).setTitleRightText(getString(R.string.joinfamily_title_tiaoguo)).setRightTextListening(RightTextReturnListener);
    }

    //右侧返回文字监听
    private View.OnClickListener RightTextReturnListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            ToastUitl.show("wewrwe", 3);
        }
    };


    @OnClick({R.id.tv_join_family_qyq, R.id.tv_join_family_sure})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_join_family_qyq:   //去邀请
                break;
            case R.id.tv_join_family_sure:  //成功
                push();
                break;
        }
    }

    public void push() {
        final MyFamilyJoinDialog myFamilyJoinDialog = new MyFamilyJoinDialog(this);
        myFamilyJoinDialog.show();
        //点击取消
        myFamilyJoinDialog.findViewById(R.id.joinfamily_dialog_quxiao).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myFamilyJoinDialog.dismiss();
            }
        });
        //点击确认
        myFamilyJoinDialog.findViewById(R.id.joinfamily_dialog_sure).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myFamilyJoinDialog.dismiss();
            }
        });
    }
}
