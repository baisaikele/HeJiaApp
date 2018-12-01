package com.keke.hejia.util;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.widget.Toast;


/**
 * Created by Administrator on 2018/7/16.
 */

public class Utils {
    /**
     * 复制到粘贴板
     *
     * @param context
     * @param content
     */
    public static void copyToClipboard(Context context, String content) {
        ClipboardManager cmb = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        cmb.setPrimaryClip(ClipData.newPlainText(null, content));
        ToastUitl.show("复制成功", Toast.LENGTH_SHORT);
    }

}
