package com.keke.hejia.activity;

import android.app.DatePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.keke.hejia.R;
import com.keke.hejia.base.BaseActivity;
import com.keke.hejia.util.ToastUitl;
import com.keke.hejia.widget.TitleBuilder;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
/**
 * 2019/11/29
 * 完善资料
 * 张宇
 **/
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
    int mYear;
    int mMonth;
    int mDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void initData() {
        Calendar ca = Calendar.getInstance();
        mYear = ca.get(Calendar.YEAR);
        mMonth = ca.get(Calendar.MONTH);
        mDay = ca.get(Calendar.DAY_OF_MONTH);
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
        //显示title文字
        new TitleBuilder(this).setTitleText(getString(R.string.title_activity_wsxx));
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
                //TODO 调用时间选择器
                new DatePickerDialog(PerfectActivity.this, onDateSetListener, mYear, mMonth, mDay).show();
                break;
            case R.id.bt_sign_in_perfect:
                if ("".equals(editNamePerfect.getText().toString()))
                    ToastUitl.show("昵称为空", 2);

                break;
        }
    }

    /**
     * 日期选择器对话框监听
     */
    private DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            mYear = year;
            mMonth = monthOfYear;
            mDay = dayOfMonth;
            String days;
            if (mMonth + 1 < 10) {
                if (mDay < 10) {
                    days = new StringBuffer().append(mYear).append("年").append("0").
                            append(mMonth + 1).append("月").append("0").append(mDay).append("日").toString();
                } else {
                    days = new StringBuffer().append(mYear).append("年").append("0").
                            append(mMonth + 1).append("月").append(mDay).append("日").toString();
                }

            } else {
                if (mDay < 10) {
                    days = new StringBuffer().append(mYear).append("年").
                            append(mMonth + 1).append("月").append("0").append(mDay).append("日").toString();
                } else {
                    days = new StringBuffer().append(mYear).append("年").
                            append(mMonth + 1).append("月").append(mDay).append("日").toString();
                }

            }
            tvData.setText(days);
        }
    };
}
