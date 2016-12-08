package com.hdmb.ireader.utils;

import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;

/**
 * 作者    武博
 * 时间    2016/7/13 0013 16:30
 * 文件    TbReader
 * 描述
 */
public class DisplayUtils {
    private DisplayUtils() {
    }

    public static void adaptStatusBar(@NonNull Context context, @NonNull View statusBar) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            ViewGroup.LayoutParams layoutParams = statusBar.getLayoutParams();
            layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
            layoutParams.height = ResUtils.getStatusBarHeight(context);
            statusBar.setLayoutParams(layoutParams);
        }
    }
}
