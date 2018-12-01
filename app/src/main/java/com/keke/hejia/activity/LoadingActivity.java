package com.keke.hejia.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.UserManager;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.keke.hejia.MainActivity;
import com.keke.hejia.R;
import com.keke.hejia.api.InitInfoManager;
import com.keke.hejia.api.PublicApi;
import com.keke.hejia.base.BaseActivity;
import com.keke.hejia.base.HeJiaApp;
import com.keke.hejia.base.LauncherActivity;
import com.keke.hejia.bean.ApiInitBean;
import com.keke.hejia.util.DepthPageTransformer;
import com.keke.hejia.util.SharedPreferenceUtil;
import com.keke.hejia.util.ToastUitl;
import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.functions.Consumer;

public class LoadingActivity extends BaseActivity {

    @BindView(R.id.vp)
    ViewPager vp;
    @BindView(R.id.ll)
    LinearLayout linearLayout;
    @BindView(R.id.lan_Iv)
    ImageView lanIv;
    @BindView(R.id.btn)
    Button btn;

    //图片资源文件
    private int[] images = {R.drawable.loading_one, R.drawable.loading_two,
            R.drawable.loading_three, R.drawable.loading_four};
    //图片放置
    private List<ImageView> iamgeList;
    // 放4个小灰点的线性布局
    //小点之间的距离
    private int pointWidth;
    public int last_time = 3;
    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            handler.removeCallbacksAndMessages(null);
            switch (message.what) {
                case 1:
                    handler.removeMessages(2);
                    Intent intent = new Intent(LoadingActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                    break;
                case 2:
                    last_time--;
                    if (last_time == 0) {
                        handler.sendEmptyMessageDelayed(1, 1000);
                    } else {
                        handler.sendEmptyMessageDelayed(2, 1000);
                    }
                    break;
            }
            return false;
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initData() {
        iamgeList = new ArrayList<ImageView>();
        //初始化图片和小点，图片的个数和小点的个数要始终一致
        for (int i = 0; i < images.length; i++) {
            ImageView imageView = new ImageView(this);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            iamgeList.add(imageView);

            // 根据图片的个数去放置相应数量的小灰点
            ImageView huiImageView = new ImageView(this);
            huiImageView.setImageResource(R.drawable.radio_button_false);
            LinearLayout.LayoutParams layoutParams =
                    new LinearLayout.LayoutParams
                            (LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            huiImageView.setLayoutParams(layoutParams);

            if (i > 0) {
                //给除了第一个小点的其它小点左边距
                layoutParams.leftMargin = 20;
            }

            linearLayout.addView(huiImageView);
        }

        /*我们的代码现在都写在onCreate方法中，onCreate在调用的时候，界面底层的绘制工作还没有完成。所以，如果我们直接在onCreate方法里去
         * 拿距离是拿不到
         * dOnGlobalLayoutListener：在视图树全部都绘制完成的时候调用*/
        lanIv.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                //视图全部绘制完成时，计算得到小点之间的距离
                pointWidth = linearLayout.getChildAt(1).getLeft() - linearLayout.getChildAt(0).getLeft();
                System.out.println(pointWidth);
            }
        });
        //绑定适配器
        vp.setAdapter(new MyAdapter());
        //设置图片切换的监听事件
        vp.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                //让滑倒最后一页显示按钮
                if (position == images.length - 1) {
                    btn.setVisibility(View.VISIBLE);
                } else {
                    btn.setVisibility(View.GONE);

                }
            }

            @Override
            //在Viewpger的界面不断滑动的时候会触发
            //position：当前滑到了第几页从0开始计数
            public void onPageScrolled(int position, float offset, int arg2) {
                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) lanIv.getLayoutParams();
                //滑动的百分比乘上小点之间的距离，再加上当前位置乘上距离，即为蓝色小点的左边距
                layoutParams.leftMargin = (int) (pointWidth * offset + position * pointWidth);
                lanIv.setLayoutParams(layoutParams);
                System.out.println("当前滑动的百分比   " + offset);

            }

            @Override
            public void onPageScrollStateChanged(int state) {
                // TODO Auto-generated method stub

            }
        });
        //设置滑动特效（可加可不加）
        vp.setPageTransformer(true, new DepthPageTransformer());
    }

    @Override
    public void getIntentData() {

    }

    @Override
    public int getLayoutResId() {
        return R.layout.activity_loading;
    }

    @Override
    protected void initUI() {
        requestPermissions();
        initBanner();
    }

    //判断是否是第一次进入
    private void initBanner() {
        if (SharedPreferenceUtil.getFirst(this, "first", "").equals("")) {
            vp.setVisibility(View.VISIBLE);
            linearLayout.setVisibility(View.VISIBLE);
            lanIv.setVisibility(View.VISIBLE);
            SharedPreferenceUtil.putFirst(this, true, "first", "123");
        } else {
            handler.sendEmptyMessage(2);
        }
    }


    private void requestPermissions() {
        RxPermissions rxPermission = new RxPermissions(LoadingActivity.this);
        rxPermission
                .requestEach(
                        Manifest.permission.READ_PHONE_STATE,
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                )
                .subscribe(new Consumer<Permission>() {
                    @Override
                    public void accept(Permission permission) throws Exception {
                        PublicApi.getIntAPP(new PublicApi.ResponseListener() {
                            @Override
                            public void success(Object o) {
                                ApiInitBean apiIntBean = (ApiInitBean) o;
                                InitInfoManager.getInstance().init(apiIntBean);
                            }

                            @Override
                            public void error(String s) {

                            }
                        });


                        if (permission.granted) {

                        } else if (permission.shouldShowRequestPermissionRationale) {
                            // 用户拒绝了该权限，没有选中『不再询问』（Never ask again）,那么下次再次启动时，还会提示请求权限的对话框
                            Log.d("TAG", permission.name + " is denied. More info should be provided.");
                            String string = getString(R.string.permission_erro);
                            ToastUitl.showShort(string);
                        } else {
                            // 用户拒绝了该权限，并且选中『不再询问』
                            Log.d("TAG", permission.name + " is denied.");
                        }

                    }
                });
    }

    @OnClick({R.id.btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn:
                LauncherActivity.goToLoginActivity(this);
                break;
        }
    }


    @Override
    public void onBackPressed() {

    }

    class MyAdapter extends PagerAdapter {
        @Override
        // 返回ViewPager里面有几页
        public int getCount() {
            // TODO Auto-generated method stub
            return images.length;
        }

        @Override
        // 用于判断是否有对象生成界面
        public boolean isViewFromObject(View view, Object object) {
            // TODO Auto-generated method stub
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView imageView = iamgeList.get(position);
            imageView.setImageResource(images[position]);
            container.addView(imageView);
            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            // TODO Auto-generated method stub
            container.removeView((View) object);
        }
    }
}
