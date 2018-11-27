package com.keke.hejia.api;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseArray;
import android.widget.Toast;

import com.keke.hejia.HeJiaApp;
import com.keke.hejia.util.LogUtils;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Administrator on 2018/7/2.
 */

public class Api {

    public static String trueUrl, otherUrl;
    //    public static final String BaseUrl = "http://dzapi.mmpei.cn/";
    //public static final String BaseUrl = "http://47.100.18.5/";   //旧地址   微转
    public static final String BaseUrl = "https://kk-api.ikeke.ltd/"; //新地址    壳壳
    private static final String ThridUrl = "http://118.190.131.243";

    private static final int READ_TIME_OUT = 10 * 1000;

    private static final int CONNECT_TIME_OUT = 10 * 1000;

    private static final long CACHE_STALE_SEC = 60 * 60 * 24 * 4;

    public static final String CACHE_CONTROL_CACHE = "only-if-cached, max-stale=" + CACHE_STALE_SEC;

    public static final String CACHE_CONTROL_AGE = "max-age=5";

    public static final int DEFAULT_SERVER = 0;

    public static final int Thrid_SERVER = 1;
    private CookieManger cookieManger;

    public static SparseArray<ApiService> mServers = new SparseArray<>(4);

    private final static String SIMPLE_DATE_PATTERN = "yyyyMMddhhmmss";
    private final static DateFormat simpleDateFormat = new SimpleDateFormat(SIMPLE_DATE_PATTERN, Locale.CHINESE);
    private static String DEVICE_INFO = "";
    private static String DEFAULT_USERAGENT = System.getProperty("http.agent") + " yingpa";


    public static boolean open_log = false;
//    private CookieManger cookieManger;


    public static void setDeviceInfo(String deviceInfo) {
        DEVICE_INFO = SwitchUtil.native2Ascii(deviceInfo);
    }

    public static void setDefaultUseragent(String defaultUseragent) {
        DEFAULT_USERAGENT = SwitchUtil.native2Ascii(defaultUseragent);
    }

    private Api() {
        this(DEFAULT_SERVER);
    }

    private Api(int type) {

        //缓存
        File cacheFile = new File(HeJiaApp.instance.getCacheDir(), "cache");
        Cache cache = new Cache(cacheFile, 1024 * 1024 * 100); //100Mb
        //增加头部信息
        Interceptor headerInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request build = chain.request().newBuilder()
                        .addHeader("Content-Type", "application/json")
                        .addHeader("DEVICE_INFO", DEVICE_INFO + "&" + "nowTime=" + simpleDateFormat.format(new Date()))
                        .addHeader("User-Agent", DEFAULT_USERAGENT)
                        .build();
                return chain.proceed(build);
            }
        };


        HeJiaApp.instance.api = this;

        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                if (open_log) {
                    Log.e("API_REQUSET_@@@@@@@@@", message);
                }
                if (TextUtils.isEmpty(message)) return;
                String s = message.substring(0, 1);
                //如果收到响应是json才打印
                if ("{".equals(s) || "[".equals(s)) {
                    LogUtils.logi("@@@@@@@@@@@收到响应: " + message);
                }
            }
        }).setLevel(HttpLoggingInterceptor.Level.BODY);//设置打印数据的级别


        Interceptor mRewriteCacheControlInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                if (!NetWorkUtil.isConnect(HeJiaApp.instance)) {
                    request = request.newBuilder()
                            .cacheControl(CacheControl.FORCE_CACHE)
                            .build();
                }
                Response originalResponse = chain.proceed(request);
                if (NetWorkUtil.isConnect(HeJiaApp.instance)) {
                    String cacheControl = request.cacheControl().toString();
                    return originalResponse.newBuilder()
                            .removeHeader("Pragma")
                            .header("Cache-Control", cacheControl)
                            .build();
                } else {
                    return originalResponse.newBuilder()
                            .removeHeader("Pragma")
                            .header("Cache-Control", "public, only-if-cached, max-stale=" + CACHE_STALE_SEC)
                            .build();
                }
            }
        };
        trueUrl = BaseUrl;
        otherUrl = BaseUrl;

        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .readTimeout(READ_TIME_OUT, TimeUnit.MILLISECONDS)
                .connectTimeout(CONNECT_TIME_OUT, TimeUnit.MILLISECONDS)
                .addInterceptor(mRewriteCacheControlInterceptor)
                .addNetworkInterceptor(mRewriteCacheControlInterceptor);

        if (type == DEFAULT_SERVER) {
            cookieManger = new CookieManger(HeJiaApp.instance);
            builder.cookieJar(cookieManger);
        }
        OkHttpClient okHttpClient = builder
                .addInterceptor(headerInterceptor)
                .addInterceptor(httpLoggingInterceptor)
                .cache(cache)
                .build();


        switch (type) {
            case Thrid_SERVER:
                otherUrl = ThridUrl + "/";
                break;
        }

        Retrofit retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(BaseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        switch (type) {
            case Thrid_SERVER:
                mServers.put(Thrid_SERVER, retrofit.create(ApiService.class));
                break;
            default:
                mServers.put(DEFAULT_SERVER, retrofit.create(ApiService.class));
        }

    }


    public static ApiService getDefault() {
        return getService(DEFAULT_SERVER);
    }

    public static ApiService getService(int type) {

        if (!NetWorkUtil.isConnect(HeJiaApp.instance)) {
            Toast.makeText(HeJiaApp.instance, "网络异常", Toast.LENGTH_SHORT).show();
        }

        if (mServers.get(type) == null) {
            synchronized (Api.class) {
                if (mServers.get(type) == null) {
                    new Api(type);
                }
            }
        }
        return mServers.get(type);
    }


    public static String getCacheControl() {
        return NetWorkUtil.isConnect(HeJiaApp.instance) ? CACHE_CONTROL_AGE : CACHE_CONTROL_CACHE;
    }

    public static HashMap<String, String> initMap(HashMap<String, String> map, String baseUrl) {

        String _t_ = String.valueOf(System.currentTimeMillis() / 1000 + HeJiaApp.instance.getTimestampCorrection());
        map.put("guid", getGuid());
        String _s_ = UrlGenerator.generateSignature("GET", baseUrl, map, _t_);

        map.put("_t_", _t_);
        map.put("_s_", _s_);
        return map;
    }
//
//


    /**
     * 获取GUID值
     *
     * @return
     */

    private static String mGuid = "";
    private static String MD5_KEY = "6b22f857fcf34565895bf37b35f88a6b";

    public static String getGuid() {
        if (TextUtils.isEmpty(mGuid))
            return getGuid(HeJiaApp.instance);
        return mGuid;
    }

    public static String getGuid(Context context) {
        if (TextUtils.isEmpty(mGuid)) {//查找sharedPreference 如果没有 生成新的并保存
            SharedPreferences sp = context.getSharedPreferences("GUID", Context.MODE_PRIVATE);
            mGuid = sp.getString("guid", "");
            if (TextUtils.isEmpty(mGuid)) {
                mGuid = getAppUniqueToken(context);
                sp.edit().putString("guid", mGuid).commit();
            }
        }
        return mGuid;
    }

    private static String getAppUniqueToken(Context context) {
        String IMEI = "";
        String Pseudo_UniqueId = "";
        String macId = "";
        String blueToothId = "";
        try {
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
                IMEI = ((TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            macId = ((WifiManager) context.getSystemService(Context.WIFI_SERVICE)).getConnectionInfo().getMacAddress();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            Pseudo_UniqueId = "35" + //we make this look like a valid IMEI
                    Build.BOARD.length() % 10 +
                    Build.BRAND.length() % 10 +
                    Build.DEVICE.length() % 10 +
                    Build.DISPLAY.length() % 10 +
                    Build.HOST.length() % 10 +
                    Build.ID.length() % 10 +
                    Build.MANUFACTURER.length() % 10 +
                    Build.MODEL.length() % 10 +
                    Build.PRODUCT.length() % 10 +
                    Build.TAGS.length() % 10 +
                    Build.TYPE.length() % 10 +
                    Build.USER.length() % 10; //13 digits
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            blueToothId = BluetoothAdapter.getDefaultAdapter().getAddress();
        } catch (Exception e) {
            e.printStackTrace();
        }

        StringBuilder sb = new StringBuilder();
        sb.append(IMEI)
                .append(Pseudo_UniqueId)
                .append(macId)
                .append(blueToothId)
                .append(MD5_KEY);

        return MD5Util.md5(sb.toString());
    }


    public void clearAll() {
        if (cookieManger == null) {
            return;
        }
        cookieManger.clearAll();
    }


}
