package com.keke.hejia.base;

import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2018/7/2.
 */

public abstract class BaseActivity extends AppCompatActivity {

    private Unbinder mBind;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (isPortrait()) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }else if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResId());

        mBind = ButterKnife.bind(this);

        getIntentData();
        initUI();
        initData();
    }
    
    public abstract void initData();
    
    public abstract void getIntentData() ;
    public abstract int getLayoutResId();

    protected abstract void initUI() ;
    protected  boolean isPortrait(){

        return true;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();


        if (mBind != null)
        mBind.unbind();
    }



}

