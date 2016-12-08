package com.hdmb.ireader.utils;

import android.content.Context;

/**
 * 作者    武博
 * 时间    2016/8/25 0025 16:37
 * 文件    TbReader
 * 描述
 */
public class Storeage {

    public static final String CACHE_PATH = "/1reader";

    public static String getApiHost(Context context) {
        return SharedPreferencesUtils.getString(context, "API_HOST", "http://ireader.tbjiaoyu.com");
    }

    public static void setApiHost(Context context, String host) {
        SharedPreferencesUtils.putString(context, "API_HOST", host);
    }
}
