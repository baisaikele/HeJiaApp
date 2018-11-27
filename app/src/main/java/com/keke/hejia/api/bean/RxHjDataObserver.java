package com.keke.hejia.api.bean;

import android.accounts.NetworkErrorException;
import android.content.Context;
import android.text.TextUtils;


import com.keke.hejia.util.ToastUitl;

import java.net.ConnectException;
import java.net.UnknownHostException;
import java.util.concurrent.TimeoutException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by Administrator on 2018/4/3.
 */

public abstract class RxHjDataObserver<T> implements Observer<HJBaseEntity<T>> {

    protected Context mContext;

    public RxHjDataObserver(Context cxt) {
        this.mContext = cxt;
    }

    public RxHjDataObserver() {

    }

    @Override
    public void onSubscribe(Disposable d) {
        onRequestStart();

    }

    @Override
    public void onNext(HJBaseEntity<T> tHJBaseEntity) {
        onRequestEnd();
        try {

            switch (tHJBaseEntity.getStatus()) {
                case HJBaseEntity.SUCCESS_CODE:
                    onSuccees(tHJBaseEntity.getData());
                    break;
                case HJBaseEntity.FAIL_CODE:
                    if(!TextUtils.isEmpty(tHJBaseEntity.getMsg())){
                        ToastUitl.showShort(tHJBaseEntity.getMsg());
                    }
                    onCodeError(tHJBaseEntity);
                    break;
                default:
                    if(!TextUtils.isEmpty(tHJBaseEntity.getMsg())){
                        ToastUitl.showShort(tHJBaseEntity.getMsg());
                    }
                    onCodeError(tHJBaseEntity);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onError(Throwable e) {
//        Log.w(TAG, "onError: ", );这里可以打印错误信息
        if (e == null || TextUtils.isEmpty(e.getMessage())) {
            return;
        }
        if (e.getMessage().equals("connect timed out")) {
            ToastUitl.showShort("连接时间过长，请检查网络");
        }
        onRequestEnd();
        try {
            if (e instanceof ConnectException
                    || e instanceof TimeoutException
                    || e instanceof NetworkErrorException
                    || e instanceof UnknownHostException) {
                onFailure(e, true);
            } else {
                onFailure(e, false);
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    @Override
    public void onComplete() {
    }

    /**
     * 返回成功
     *
     * @param t
     * @throws Exception
     */
    protected abstract void onSuccees(T t) throws Exception;

    /**
     * 返回成功了,但是code错误
     *
     * @param t
     * @throws Exception
     */
    protected void onCodeError(HJBaseEntity<T> t) throws Exception {
    }

    /**
     * 返回失败
     *
     * @param e
     * @param isNetWorkError 是否是网络错误
     * @throws Exception
     */
    protected abstract void onFailure(Throwable e, boolean isNetWorkError) throws Exception;

    protected void onRequestStart() {
    }

    protected void onRequestEnd() {
//        closeProgressDialog();
    }

    public void showProgressDialog() {
//        ProgressDialog.show(mContext, false, "请稍后");
    }

    public void closeProgressDialog() {
//        ProgressDialog.cancle();
    }
}
