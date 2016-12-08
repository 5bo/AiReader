package com.hdmb.ireader.utils;

import android.content.Context;

/**
 * 作者    武博
 * 时间    2016/7/13 0013 17:08
 * 文件    TbReader
 * 描述    配置信息
 */
public final class Themes {

    public static final String KEY_ENABLE_THEME_DARK = "endableThemeDark";

    public static boolean isEnableThemeDark(Context context) {
        return SharedPreferencesUtils.getBoolean(context, KEY_ENABLE_THEME_DARK, false);
    }

    public static void setEnableThemeDark(Context context, boolean isEnableThemeDark) {
        SharedPreferencesUtils.putBoolean(context, KEY_ENABLE_THEME_DARK, isEnableThemeDark);
    }
}
