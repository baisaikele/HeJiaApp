package com.keke.hejia.api;


import android.net.Uri;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2016/4/23.
 * url类
 */
public class UrlGenerator {

    private static String it_origin = "aviapao[av*_)&po1-0UH_()&bi13iv9ash(*&nnzhfajaslv!sdiomvaopsJPj)jnbajnasjndjasn;kasnda;lsv;sjb;lwenn:Ajfwevn;sajhb;aslnefqw;lvlsaeg;ajsviasdnv;las;asdjlasn ;aiajFLVAS;LNAivn;ljf;la6e1c51bfffc79c7932c0bb986cd29ce5viapao[av*_)&po1-0UH_()&bi13iv9ash(*&nnz zhfajaslv!sdiomvaopsJPj)J&YQmfd9sopzxb7968798n8uiqkIJDS;AIJF;IFJ;AFAS'JJ&YQmfd9sopzxb7968798n8uiqkfuvhldkjshgubnalkjbhaskngjklxjnzbaks-qf98";

    private static String secret_key = "ahuivavhse143lk2ul1k23hbl1hu1fuh";

    private static final String TAG = "generate signature";
    private static String it = null;

    public static String generateSignature(String method, String absolutePath, HashMap<String, String> params, String timestamp) {
        if (it == null) {
            int centerIndex = it_origin.length() / 2;
            it = it_origin.substring(centerIndex - secret_key.length() / 2, centerIndex + secret_key.length() / 2);
        }
        Uri uri = Uri.parse(absolutePath);
        String path = uri.getPath();

        List<String> list = new ArrayList<>(params.keySet());

        Collections.sort(list, new Comparator<String>() {//按key值字符串比较从小到大
            @Override
            public int compare(String o1, String o2) {
                return o1.compareTo(o2);
            }
        });

        StringBuilder re = new StringBuilder();
        for (String key : list) {
            re.append(String.format("%s=%s", key, params.get(key)));
        }

//        token_string = req_method + ":" + path + ":" + sorted(query) + ":" + timestamp + ":" + secret
//        signature = ngx.md5(token_string)
        String token_string = method + ':' + path + ":" + re.toString() + ":" + timestamp + ":" + it;

        return MD5Util.md5(token_string);
    }

    public static String generateUrl(String baseUrl, HashMap<String, String> params, String timestamp) {
        if (it == null) {
            int centerIndex = it_origin.length() / 2;
            it = it_origin.substring(centerIndex - secret_key.length() / 2, centerIndex + secret_key.length() / 2);
        }
        //生成url地址， baseUrl+params + _t_ + _s_
        Uri uri = Uri.parse(baseUrl);
        Uri.Builder builder = uri.buildUpon();
        if (!params.isEmpty()) {
            String _s_ = generateSignature("GET", baseUrl, params, timestamp);
            for (String key : params.keySet()) {
                builder.appendQueryParameter(key, params.get(key));
            }
            builder.appendQueryParameter("_t_", timestamp);
            builder.appendQueryParameter("_s_", _s_);
        }
        return builder.toString();
    }


}



