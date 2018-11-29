package com.keke.hejia.base;


import android.content.Context;
import android.content.Intent;


import com.keke.hejia.activity.ChangeHomeActivity;
import com.keke.hejia.activity.EditNameActivity;


/**
 * Created by Administrator on 2018/7/11.
 */

public class LauncherActivity {


    /**
     * 去往退出更换原生家庭页面
     *
     * @param context
     */

    public static void goToChangeHomeActivity(Context context) {
        Intent intent = new Intent(context, ChangeHomeActivity.class);
        context.startActivity(intent);
    }


    /**
     * 去往修改昵称页面
     *
     * @param context
     */

    public static void goToEditNameActivity(Context context) {
        Intent intent = new Intent(context, EditNameActivity.class);
        context.startActivity(intent);
    }

}
