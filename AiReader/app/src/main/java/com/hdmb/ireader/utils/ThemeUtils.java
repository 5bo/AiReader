package com.hdmb.ireader.utils;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;

public final class ThemeUtils {

    private ThemeUtils() {}

    public static boolean configThemeBeforeOnCreate(@NonNull Activity activity, @StyleRes int light, @StyleRes int dark) {
        boolean enable = Themes.isEnableThemeDark(activity);
        activity.setTheme(enable ? dark : light);
        return enable;
    }

    public static void notifyThemeApply(@NonNull Activity activity, boolean delay) {
        if (delay) {
            ActivityUtils.recreateDelayed(activity);
        } else {
            ActivityUtils.recreate(activity);
        }
    }
}