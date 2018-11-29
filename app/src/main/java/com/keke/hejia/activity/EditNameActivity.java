package com.keke.hejia.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.keke.hejia.R;
import com.keke.hejia.base.BaseActivity;
import com.keke.hejia.util.ToastUitl;
import com.keke.hejia.widget.TitleBuilder;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * 2019/11/29
 * 编辑昵称
 * 张宇
 **/
public class EditNameActivity extends BaseActivity {

    @BindView(R.id.edit_name_edit)
    EditText editNameEdit;
    @BindView(R.id.quxiaowenzi_edit)
    ImageView quxiaowenziEdit;

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
        return R.layout.activity_edit_name;
    }

    @Override
    protected void initUI() {
        //显示右边文字
        new TitleBuilder(this).setTitleText(getString(R.string.etit_edit_name)).setTitleRightText(getString(R.string.etit_save_edit)).setRightTextListening(RightTextReturnListener).setLeftIco(R.drawable.title_fanhui).setLeftIcoListening(leftReturnListener);
    }

    //左侧返回图片监听
    private View.OnClickListener leftReturnListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            finish();
        }
    };
    //左侧返回文字监听
    private View.OnClickListener RightTextReturnListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            ToastUitl.show("wewrwe", 3);
        }
    };

    @OnClick({R.id.quxiaowenzi_edit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.quxiaowenzi_edit:
                editNameEdit.setText("");
                break;
        }
    }
}
