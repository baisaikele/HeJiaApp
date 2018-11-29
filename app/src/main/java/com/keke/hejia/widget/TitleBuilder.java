package com.keke.hejia.widget;

import android.app.Activity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.keke.hejia.R;

public class TitleBuilder {
    private View titleView;
    private RelativeLayout titleBar;
    private TextView text;
    private TextView textRight;
    private ImageView leftIco;
    private ImageView rightIco;

    /**
     * 构造方法：用于获取对象
     */
    public TitleBuilder(Activity context) {
        titleView = context.findViewById(R.id.title_bar);
        text = (TextView) titleView.findViewById(R.id.title_text);
        textRight = (TextView) titleView.findViewById(R.id.title_sure);
        titleBar = (RelativeLayout) titleView.findViewById(R.id.title_bar);
        leftIco = (ImageView) titleView.findViewById(R.id.title_leftIco);
        rightIco = (ImageView) titleView.findViewById(R.id.title_rightIco);
    }

    /**
     * 用于设置标题栏文字
     */
    public TitleBuilder setTitleText(String titleText) {
        if (!TextUtils.isEmpty(titleText)) {
            text.setText(titleText);
        }
        return this;
    }

    /**
     * 用于设置标题栏右边文字
     */
    public TitleBuilder setTitleRightText(String titleText) {
        if (!TextUtils.isEmpty(titleText)) {
            textRight.setText(titleText);
        }
        return this;
    }

    /**
     * 用于设置标题栏右边单击事件
     */
    public TitleBuilder setRightTextListening(View.OnClickListener listener) {
        if (textRight.getVisibility() == View.VISIBLE) {
            textRight.setOnClickListener(listener);
        }
        return this;
    }

    /**
     * 用于设置标题栏左边要显示的图片
     */
    public TitleBuilder setLeftIco(int resId) {
        leftIco.setVisibility(resId > 0 ? View.VISIBLE : View.GONE);
        leftIco.setImageResource(resId);
        return this;
    }

    /**
     * 用于设置标题栏右边要显示的图片
     */
    public TitleBuilder setRightIco(int resId) {
        rightIco.setVisibility(resId > 0 ? View.VISIBLE : View.GONE);
        rightIco.setImageResource(resId);
        return this;
    }

    /**
     * 用于设置标题栏左边图片的单击事件
     */
    public TitleBuilder setLeftIcoListening(View.OnClickListener listener) {
        if (leftIco.getVisibility() == View.VISIBLE) {
            leftIco.setOnClickListener(listener);
        }
        return this;
    }

    /**
     * 用于设置标题栏右边文字的单击事件
     */
    public TitleBuilder setRightIcoListening(View.OnClickListener listener) {
        if (rightIco.getVisibility() == View.VISIBLE) {
            rightIco.setOnClickListener(listener);
        }
        return this;
    }
}
