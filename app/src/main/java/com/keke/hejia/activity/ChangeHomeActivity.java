package com.keke.hejia.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.keke.hejia.R;
import com.keke.hejia.base.BaseActivity;
import com.keke.hejia.util.ToastUitl;
import com.keke.hejia.widget.TitleBuilder;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ChangeHomeActivity extends BaseActivity {

    @BindView(R.id.change_home_num)
    EditText changeHomeNum;

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
        return R.layout.activity_change_home;
    }

    @Override
    protected void initUI() {
        //不显示右边
        new TitleBuilder(this).setTitleText(getString(R.string.changeHome_title)).setLeftIco(R.drawable.title_fanhui).setLeftIcoListening(leftReturnListener);
    }


    //左侧返回图片监听
    private View.OnClickListener leftReturnListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            finish();
        }
    };

    @OnClick({R.id.change_home_sure, R.id.change_home_login_out})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.change_home_sure:
                String code = changeHomeNum.getText().toString().trim();
                if (TextUtils.isEmpty(code)) {
                    ToastUitl.show("null", 2);
                    return;
                }
                ToastUitl.show("成功", 2);
                break;
            case R.id.change_home_login_out:
                break;
        }
    }
}
