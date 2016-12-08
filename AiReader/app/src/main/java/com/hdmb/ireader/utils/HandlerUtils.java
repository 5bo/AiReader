package com.hdmb.ireader.utils;

import android.os.Handler;
import android.os.Looper;

/**
 * 作者    武博
 * 时间    2016/7/13 0013 17:12
 * 文件    TbReader
 * 描述
 */
public final class HandlerUtils {

    private HandlerUtils() {}

    private static final Handler handler = new Handler(Looper.getMainLooper());

    public static boolean post(Runnable r) {
        return handler.post(r);
    }

    public static boolean postDelayed(Runnable r, long delayMillis) {
        return handler.postDelayed(r, delayMillis);
    }
}
