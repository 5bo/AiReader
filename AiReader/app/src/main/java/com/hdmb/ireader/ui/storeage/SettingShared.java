package com.hdmb.ireader.ui.storeage;

import android.content.Context;
import android.support.annotation.NonNull;

/**
 * 作者    武博
 * 时间    2016/8/22 0022 16:35
 * 文件    TbReader
 * 描述
 */
public final class SettingShared {
    private SettingShared() {
    }

    private static final String TAG = "SettingShared";

    private static final String KEY_ENABLE_NOTIFICATION = "enableNotification";
    private static final String KEY_ENABLE_THEME_DARK = "enableThemeDark";

    public static boolean isEnableNotification(@NonNull Context context) {
        return SharedWrapper.with(context, TAG).getBoolean(KEY_ENABLE_NOTIFICATION, true);
    }

    public static void setEnableNotification(@NonNull Context context, boolean enable) {
        SharedWrapper.with(context, TAG).setBoolean(KEY_ENABLE_NOTIFICATION, enable);
    }

    public static boolean isEnableThemeDark(@NonNull Context context) {
        return SharedWrapper.with(context, TAG).getBoolean(KEY_ENABLE_THEME_DARK, false);
    }

    public static void setEnableThemeDark(@NonNull Context context, boolean enable) {
        SharedWrapper.with(context, TAG).setBoolean(KEY_ENABLE_THEME_DARK, enable);
    }
}
