package com.keke.hejia.api.bean;

public class HJBaseEntity <T> {


    public static final int SUCCESS_CODE=0;//成功的code
    public static final int FAIL_CODE=-1;//失败的code
    private int code;
    private String msg;
    private T data;



    public boolean isSuccess(){
        return getStatus()==SUCCESS_CODE;
    }


    public int getStatus() {
        return code;
    }

    public void setStatus(int status) {
        this.code = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}