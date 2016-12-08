package com.hdmb.ireader.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by TB_Wubo on 2015/7/9.
 */
public class SharedPreferencesUtils {

    private static SharedPreferences sp = null;

    private static final String SHARED_PREFERENCES_NAME = "tbjy";

    private static void init(Context context) {
        if (sp == null) {
            sp = context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        }
    }

    public static void putInt(Context context, String key, int val) {
        init(context);
        sp.edit().putInt(key, val).commit();
    }

    public static int getInt(Context context, String key, int defVal) {
        init(context);
        return sp.getInt(key, defVal);
    }

    public static void putString(Context context, String key, String val) {
        init(context);
        sp.edit().putString(key, val).commit();
    }

    public static String getString(Context context, String key, String defVal) {
        init(context);
        return sp.getString(key, defVal);
    }

    public static void putBoolean(Context context, String key, Boolean val) {
        init(context);
        sp.edit().putBoolean(key, val).commit();
    }

    public static Boolean getBoolean(Context context, String key, Boolean defVal) {
        init(context);
        return sp.getBoolean(key, defVal);
    }

    public static void putFloat(Context context, String key, Float val) {
        init(context);
        sp.edit().putFloat(key, val).commit();
    }

    public static Float getFloat(Context context, String key, Float defVal) {
        init(context);
        return sp.getFloat(key, defVal);
    }

    public static void putLong(Context context, String key, Long val) {
        init(context);
        sp.edit().putLong(key, val).commit();
    }

    public static Long getLong(Context context, String key, Long defVal) {
        init(context);
        return sp.getLong(key, defVal);
    }
}
