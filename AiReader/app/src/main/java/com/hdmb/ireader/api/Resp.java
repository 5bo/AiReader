package com.hdmb.ireader.api;

import com.google.gson.annotations.SerializedName;

/**
 * 作者    武博
 * 时间    2016/7/11 0011 14:45
 * 文件    TbReader
 * 描述
 */
public class Resp<T> {

    int code;
    @SerializedName("message")
    String msg;
    T data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
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

    @Override
    public String toString() {
        return "Resp{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data.toString() +
                '}';
    }
}
