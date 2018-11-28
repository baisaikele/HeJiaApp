package com.keke.hejia.activity;

import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.keke.hejia.R;
import com.keke.hejia.base.BaseActivity;
import com.keke.hejia.util.ToastUitl;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PerfectActivity extends BaseActivity {


    @BindView(R.id.radio_button_nan_perfect)
    RadioButton radioButtonNanPerfect;
    @BindView(R.id.radio_button_nv_perfect)
    RadioButton radioButtonNvPerfect;
    @BindView(R.id.radio_group_perfect)
    RadioGroup radioGroupPerfect;
    @BindView(R.id.edit_name_perfect)
    EditText editNamePerfect;
    @BindView(R.id.tv)
    TextView tv;
    @BindView(R.id.tv_data)
    TextView tvData;
    @BindView(R.id.rl_data_perfect)
    RelativeLayout rlDataPerfect;
    @BindView(R.id.bt_sign_in_perfect)
    Button btSignInPerfect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void initData() {

    }

    @Override
    public void getIntentData() {

    }

    @Override
    public int getLayoutResId() {
        return R.layout.activity_perfect;
    }

    @Override
    protected void initUI() {
        //radio监听
        radioGroupPerfect.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == radioButtonNanPerfect.getId()) {
                    ToastUitl.show("男", 2);
                } else if (checkedId == radioButtonNvPerfect.getId()) {
                    ToastUitl.show("女", 2);
                } else {
                    ToastUitl.show("没有", 2);
                }
            }
        });
    }



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.rl_data_perfect, R.id.bt_sign_in_perfect})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_data_perfect:
                break;
            case R.id.bt_sign_in_perfect:
                if ("".equals(editNamePerfect.getText().toString()))
                    ToastUitl.show("昵称为空", 2);

                break;
        }
    }
}
