package com.hdmb.ireader.utils;

/**
 * Created by TB_Wubo on 2015/7/9.
 */
public class Log {

    public static final String TAG = "TbReader";

    public static final int ALL = 0;
    public static final int VERBOSE = 1;
    public static final int DEBUG = 2;
    public static final int INFO = 3;
    public static final int WARN = 4;
    public static final int ERROR = 5;
    public static final int WTF = 6;

    private static boolean[] sw = {true, true, true, true, true, true, true};

    public static void _switch(int level, boolean state) {
        if (level >= 0 && level <= 6)
            sw[level] = state;
    }

    // e
    public static void e(String msg) {
        if (sw[0] && sw[5])
            android.util.Log.e(TAG, msg);
    }

    public static void e(String tag, String msg) {
        if (sw[0] && sw[5])
            android.util.Log.e(tag, msg);
    }

    public static void e(String msg, Throwable throwable) {
        if (sw[0] && sw[5])
            android.util.Log.e(TAG, msg, throwable);
    }

    public static void e(String tag, String msg, Throwable throwable) {
        if (sw[0] && sw[5])
            android.util.Log.e(tag, msg, throwable);
    }

    // d
    public static void d(String tag, String msg) {
        if (sw[0] && sw[2])
            android.util.Log.d(tag, msg);
    }

    public static void d(String tag, String msg, Throwable throwable) {
        if (sw[0] && sw[2])
            android.util.Log.e(tag, msg, throwable);
    }

    // i
    public static void i(String tag, String msg) {
        if (sw[0] && sw[3])
            android.util.Log.i(tag, msg);
    }

    public static void i(String tag, String msg, Throwable throwable) {
        if (sw[0] && sw[3])
            android.util.Log.e(tag, msg, throwable);
    }

    // v
    public static void v(String tag, String msg) {
        if (sw[0] && sw[1])
            android.util.Log.v(tag, msg);
    }

    public static void v(String tag, String msg, Throwable throwable) {
        if (sw[0] && sw[1])
            android.util.Log.v(tag, msg, throwable);
    }

    // w
    public static void w(String tag, String msg) {
        if (sw[0] && sw[4])
            android.util.Log.w(tag, msg);
    }

    public static void w(String tag, String msg, Throwable throwable) {
        if (sw[0] && sw[4])
            android.util.Log.w(tag, msg, throwable);
    }

    // wtf
    public static void wtf(String tag, String msg) {
        if (sw[0] && sw[6])
            android.util.Log.wtf(tag, msg);
    }

    public static void wtf(String tag, String msg, Throwable throwable) {
        if (sw[0] && sw[6])
            android.util.Log.wtf(tag, msg, throwable);
    }

    public static boolean isLoggable(int level) {
        if (level >= 0 && level < sw.length)
            return sw[level] && sw[0];
        return false;
    }
}