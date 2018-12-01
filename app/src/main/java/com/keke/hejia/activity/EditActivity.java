package com.keke.hejia.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.keke.hejia.R;
import com.keke.hejia.base.BaseActivity;
import com.keke.hejia.base.LauncherActivity;
import com.keke.hejia.widget.TitleBuilder;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * 2019/11/29
 * 编辑资料
 * 张宇
 **/
public class EditActivity extends BaseActivity {

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
    @BindView(R.id.img_face_show_edit)
    ImageView imgFaceShowEdit;
    @BindView(R.id.img_face)
    ImageView imgFace;
    @BindView(R.id.rl_edit_face)
    RelativeLayout rlEditFace;
    @BindView(R.id.tv_edit_name)
    TextView tvEditName;
    @BindView(R.id.img_you)
    ImageView imgYou;
    @BindView(R.id.rl_edit_name)
    RelativeLayout rlEditName;
    @BindView(R.id.tv_edit_gender)
    TextView tvEditGender;
    @BindView(R.id.tv_show_gender_deit)
    TextView tvShowGenderDeit;
    @BindView(R.id.rl_edit_gender)
    RelativeLayout rlEditGender;
    @BindView(R.id.tv)
    TextView tv;
    @BindView(R.id.tv_data_edit)
    TextView tvDataEdit;
    @BindView(R.id.rl_edit_data)
    RelativeLayout rlEditData;

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
        return R.layout.activity_edit;
    }

    @Override
    protected void initUI() {
        //不显示右边
        new TitleBuilder(this).setTitleText(getString(R.string.etit_title)).setLeftIco(R.drawable.title_fanhui).setLeftIcoListening(leftReturnListener);
    }


    //左侧返回图片监听
    private View.OnClickListener leftReturnListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            finish();
        }
    };

    @OnClick({R.id.rl_edit_face, R.id.rl_edit_name, R.id.rl_edit_gender, R.id.rl_edit_data})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_edit_face:   //头像
                break;
            case R.id.rl_edit_name:  //name
                LauncherActivity.goToEditNameActivity(EditActivity.this);
                break;
            case R.id.rl_edit_data:   //出生日期
                break;
        }
    }
}
