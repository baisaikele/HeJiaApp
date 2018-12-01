package com.keke.hejia.base;


import android.content.Context;
import android.content.Intent;


import com.keke.hejia.MainActivity;
import com.keke.hejia.activity.ChangeHomeActivity;
import com.keke.hejia.activity.EditNameActivity;
import com.keke.hejia.activity.InvitationActivity;
import com.keke.hejia.activity.JoinFamilyActivity;
import com.keke.hejia.activity.LoginActivity;
import com.keke.hejia.activity.PerfectActivity;
import com.keke.hejia.activity.UpdataActivity;


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

    /**
     * 去往登录页
     *
     * @param context
     */

    public static void goToLoginActivity(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }

    /**
     * 去往首页
     *
     * @param context
     */

    public static void goToMainActivity(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }

    /**
     * 加入家庭
     *
     * @param context
     */
    public static void goToJoinMainActivity(Context context) {
        Intent intent = new Intent(context, JoinFamilyActivity.class);
        context.startActivity(intent);
    }


    /**
     * 完善资料
     *
     * @param context
     */
    public static void goToPerfectActivity(Context context) {
        Intent intent = new Intent(context, PerfectActivity.class);
        context.startActivity(intent);
    }

    /**
     * 邀请
     *
     * @param context
     */
    public static void goToInvitationActivity(Context context) {
        Intent intent = new Intent(context,InvitationActivity.class);
        context.startActivity(intent);
    }
    /**
     * 更新apk
     */
    public static void goToUpAppActivity(Context context, String title, String cont, String url, int type) {
        Intent intent = new Intent(context, UpdataActivity.class);
        intent.putExtra(UpdataActivity.TYPE, type);
        intent.putExtra(UpdataActivity.KEY_UPDATE_URL, url);
        intent.putExtra(UpdataActivity.KEY_UPDATE_TITLE, title);
        intent.putExtra(UpdataActivity.KEY_IGNORE_DATE, cont);
        context.startActivity(intent);
    }
}
