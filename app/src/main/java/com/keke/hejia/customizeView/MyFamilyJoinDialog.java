package com.keke.hejia.customizeView;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import com.keke.hejia.R;
import com.keke.hejia.RecycBeanDemo;
import com.keke.hejia.adapter.RecycViewAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyFamilyJoinDialog extends Dialog {
    @BindView(R.id.join_dialog_title)
    TextView joinDialogTitle;
    @BindView(R.id.join_dialog_yaqingni)
    TextView joinDialogYaqingni;
    @BindView(R.id.join_dialog_juese)
    TextView joinDialogJuese;
    @BindView(R.id.joinfamily_dialog_tishi)
    TextView joinfamilyDialogTishi;
    @BindView(R.id.joinfamily_dialog_recycview)
    RecyclerView joinfamilyDialogRecycview;
    @BindView(R.id.joinfamily_dialog_quxiao)
    TextView joinfamilyDialogQuxiao;
    @BindView(R.id.joinfamily_dialog_sure)
    TextView joinfamilyDialogSure;

    private List<RecycBeanDemo> iconList = new ArrayList<>();


    public MyFamilyJoinDialog(@NonNull Context context) {
        super(context, R.style.CustomDialog);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_join_family);
        ButterKnife.bind(this);
        initView();
        setData();
    }

    private void setData() {
    }

    private void initView() {
        String textStr = "<font color=\"#FFA643\">" + "张宇" + "</font>邀请你加入他的家庭";
        joinDialogYaqingni.setText(Html.fromHtml(textStr));
        String textStr2 = "你在家庭中的角色是<font color=\"#FFA643\">" + "(" + "无" + ")" + "</font>";
        joinDialogJuese.setText(Html.fromHtml(textStr2));
        ///初始化
        init();
        //创建一个layoutManager，这里使用LinearLayoutManager指定为线性，也就可以有ListView这样的效果
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        //完成layoutManager设置
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        joinfamilyDialogRecycview.setLayoutManager(layoutManager);
        joinfamilyDialogRecycview.addItemDecoration(new SpaceItemDecoration(25, 0));
        //创建IconAdapter的实例同时将iconList传入其构造函数
        RecycViewAdapter adapter = new RecycViewAdapter(iconList);
        //完成adapter设置
        joinfamilyDialogRecycview.setAdapter(adapter);
    }

    private void init() {
        for (int i = 0; i < 3; i++) {
            RecycBeanDemo a = new RecycBeanDemo("c语言", R.drawable.ic_launcher_background, "父亲");
            iconList.add(a);
            RecycBeanDemo b = new RecycBeanDemo("Github", R.drawable.ic_launcher_background, "孙子");
            iconList.add(b);
            RecycBeanDemo c = new RecycBeanDemo("Java", R.drawable.ic_launcher_background, "儿子");
            iconList.add(c);
        }
    }


    //recycview item边距方法
    public class SpaceItemDecoration extends RecyclerView.ItemDecoration {

        private int leftRight;
        private int topBottom;

        public SpaceItemDecoration(int leftRight, int topBottom) {
            this.leftRight = leftRight;
            this.topBottom = topBottom;
        }

        @Override
        public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
            super.onDraw(c, parent, state);
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            LinearLayoutManager layoutManager = (LinearLayoutManager) parent.getLayoutManager();
            //竖直方向的
            if (layoutManager.getOrientation() == LinearLayoutManager.VERTICAL) {
                //最后一项需要 bottom
                if (parent.getChildAdapterPosition(view) == layoutManager.getItemCount() - 1) {
                    outRect.bottom = topBottom;
                }
                outRect.top = topBottom;
                outRect.left = leftRight;
                outRect.right = leftRight;
            } else {
                //最后一项需要right
                if (parent.getChildAdapterPosition(view) == layoutManager.getItemCount() - 1) {
                    outRect.right = leftRight;
                }
                outRect.top = topBottom;
                outRect.left = leftRight;
                outRect.bottom = topBottom;
            }
        }
    }
}
