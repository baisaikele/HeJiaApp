package com.keke.hejia.activity;
import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.keke.hejia.api.NetWorkUtil;
import com.keke.hejia.api.PublicApi;
import com.keke.hejia.api.kkWzConstants;
import com.keke.hejia.updata.UpdateService;
import com.keke.hejia.util.FileUtils;
import com.keke.hejia.util.LogUtils;
import com.keke.hejia.util.ToastUitl;
import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.io.File;
import com.keke.hejia.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.reactivex.functions.Consumer;
import okhttp3.ResponseBody;
import retrofit2.Response;

public class UpdataActivity extends AppCompatActivity {


    public final static String KEY_IGNORE_DATE = "ignore_date";
    public final static String KEY_UPDATE_TITLE = "title";
    public final static String KEY_UPDATE_URL = "url";
    public final static String TYPE = "TYPE";
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_con)
    TextView tvCon;
    @BindView(R.id.tv_ok)
    TextView tvOk;
    @BindView(R.id.check_version_progress)
    ProgressBar checkVersionProgress;
    @BindView(R.id.iv_title)
    ImageView ivTitle;
    private String ApkUrl;
    private int extra;
    private Unbinder mBind;

//    ProgressReceiver progressReceiver = new ProgressReceiver();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResId());
        mBind = ButterKnife.bind(this);

        initUI();
    }

    public int getLayoutResId() {
        return R.layout.activity_updata;
    }

    protected void initUI() {

        String cont = getIntent().getStringExtra(KEY_IGNORE_DATE);
        String title = getIntent().getStringExtra(KEY_UPDATE_TITLE);
        ApkUrl = getIntent().getStringExtra(KEY_UPDATE_URL);
        extra = getIntent().getIntExtra(TYPE, 1);

        tvCon.setText(cont);
//        tvTitle.setText(String.format(getString(R.string.newneirong)));


        if (extra == 1) {

        } else {
            findViewById(R.id.iv_fins).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });
            findViewById(R.id.iv_fins).setVisibility(View.VISIBLE);
        }


//        IntentFilter filter = new IntentFilter();
//        filter.addAction(getString(R.string.page) + ".ACTION_PROGRESS_UPDATE");
//        registerReceiver(progressReceiver, filter);


    }

    /**
     * 判断读写内存的权限
     */
    @SuppressLint("CheckResult")
    private void checkIsAndroidO() {
        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions.requestEach(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(new Consumer<Permission>() {
                    @Override
                    public void accept(Permission permission) throws Exception {
                        if (permission.granted) {
                            update();
                        } else {
                            ToastUitl.show("获取读写内存卡权限失败", Toast.LENGTH_SHORT);
                        }
                    }
                });


    }

    @OnClick(R.id.tv_ok)
    public void onViewClicked() {
        checkIsAndroidO();
    }

    public void update() {
        if (extra == 0) {
            finish();
        } else {

            if (TextUtils.isEmpty(ApkUrl)) return;
            if (!NetWorkUtil.isConnect(this)) {
                ToastUitl.showShort("无可用网络");
                return;
            }
            execute();
            tvOk.setVisibility(View.GONE);
            checkVersionProgress.setVisibility(View.VISIBLE);

        }
    }

    private void instalAPK() {
        if (extra == 0) {
            finish();
        } else {

            if (TextUtils.isEmpty(ApkUrl)) return;
            if (!NetWorkUtil.isConnect(this)) {
                ToastUitl.showShort("无可用网络");
                return;
            }
            startService(new Intent().setClass(getApplicationContext(), UpdateService.class).putExtra(kkWzConstants.KEY_UPDATE_URL, ApkUrl));
            tvOk.setVisibility(View.GONE);
            checkVersionProgress.setVisibility(View.VISIBLE);

        }
    }

    @Override
    public void onBackPressed() {
        return;

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (mBind != null)
            mBind.unbind();
    }


    public static final String DOWNLOAD_FOLDER_NAME = "xiaoniu";
    public static final String DOWNLOAD_FILE_NAME = "xiaoniu_update.apk";

    public void execute() {
        File folder = Environment.getExternalStoragePublicDirectory(DOWNLOAD_FOLDER_NAME);
        if (!folder.exists() || !folder.isDirectory()) {
            folder.mkdirs();
        }
        final File apkFile = new File(Environment.getExternalStoragePublicDirectory(DOWNLOAD_FOLDER_NAME) + File.separator + DOWNLOAD_FILE_NAME);
        if (apkFile.exists()) {
            apkFile.delete();
        }
        ToastUitl.showShort("开始下载更新，请稍候");
        LogUtils.loge(ApkUrl+"------------------");
        checkVersionProgress= ((ProgressBar) findViewById(R.id.check_version_progress));

        /**
         * 注释更新接口
         *
         * **/

//        PublicApi.upDateApk(ApkUrl, new PublicApi.ResponseListener() {
//            @Override
//            public void success(Object o) {
//                final Response<ResponseBody> response = (Response<ResponseBody>) o;
//
//                new Thread() {
//                    @Override
//                    public void run() {
//                        super.run();
//                        //保存到本地
//                        FileUtils.writeFile2Disk(response, apkFile, new FileUtils.HttpCallBack() {
//                            @Override
//                            public void onLoading(final long current, final long total) {
//                                /** * 更新进度条 */
//                                runOnUiThread(
//                                        new Runnable() {
//                                            @Override
//                                            public void run() {
//                                                if (checkVersionProgress!=null) {
//                                                    checkVersionProgress.setMax((int) total);
//                                                    checkVersionProgress.setProgress((int) current);
//                                                    if(current >= total){
//                                                        install(UpdataActivity.this,apkFile.getAbsolutePath());
//                                                    }
//                                                }
//                                            }
//                                        });
//                            }
//                        });
//
//                    }
//                }.start();
//            }
//
//            @Override
//            public void error(String s) {
//
//            }
//        });
    }
    /**
     * install app
     *
     * @param context
     * @param filePath
     * @return whether apk exist
     */
    public static boolean install(Context context, String filePath) {
        Intent i = new Intent(Intent.ACTION_VIEW);
        File file = new File(filePath);
        if (file != null && file.length() > 0 && file.exists() && file.isFile()) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                // 由于没有在Activity环境下启动Activity,设置下面的标签
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                //参数1 上下文, 参数2 Provider主机地址 和配置文件中保持一致   参数3  共享的文件
                Uri apkUri = FileProvider.getUriForFile(context, "com.keke.beikekanba.fileProvider", file);
                //添加这一句表示对目标应用临时授权该Uri所代表的文件
                i.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                i.setDataAndType(apkUri, "application/vnd.android.package-archive");
            } else {
                i.setDataAndType(Uri.fromFile(new File(filePath)),
                        "application/vnd.android.package-archive");
            }

            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(i);


            return true;

        }
        return false;
    }
}
