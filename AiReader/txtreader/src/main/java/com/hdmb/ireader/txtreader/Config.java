package com.hdmb.ireader.txtreader;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * 作者    武博
 * 时间    2016/8/16 0016 10:40
 * 文件    TbReader
 * 描述
 */
public class Config {

    private static SharedPreferences sp = null;
    private static final String SHARED_PREFERENCES_NAME = "tbjy";

    private static void init(Context context) {
        if (sp == null) {
            sp = context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        }
    }

    public static int getTxtSize(Context context) {
        init(context);
        return sp.getInt("txtReaderTextSize", 30);
    }

    public static void setTxtSize(Context context, int txtSize) {
        init(context);
        sp.edit().putInt("txtReaderTextSize", txtSize).commit();
    }

    public static String getTypeface(Context context) {
        init(context);
        return sp.getString("txtReaderTypeface", "fonts/font2.ttf");
    }

    public static void setTypeface(Context context, String typeface) {
        init(context);
        sp.edit().putString("txtReaderTypeface", typeface).commit();
    }

    public static int getTheme(Context context) {
        init(context);
        return sp.getInt("txtReaderTheme", com.hdmb.ireader.R.drawable.ic_default_bg);
    }

    public static void setTheme(Context context, int themeId) {
        init(context);
        sp.edit().putInt("txtReaderTheme", themeId).commit();
    }
}
