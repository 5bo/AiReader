package com.hdmb.ireader.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

/**
 * 作者    武博
 * 时间    2016/7/11 0011 11:53
 * 文件    TbReader
 * 描述
 */
public class Utils {

    public static String getVersionName(Context context) {
        try {
            PackageInfo pi = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return pi.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "1.0";
    }

    public static String getPatchVersionName(Context context){
        return SharedPreferencesUtils.getString(context, "PATCH_VERSION", "0");
    }

    public static void setPatchVersionName(Context context, String versionName){
        SharedPreferencesUtils.putString(context, "PATCH_VERSION", versionName);
    }
}
