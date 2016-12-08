package com.hdmb.ireader.ui;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;

import com.hdmb.ireader.ui.activity.AboutActivity;
import com.hdmb.ireader.ui.activity.MoreActivity;
import com.hdmb.ireader.ui.activity.OfflineActivity;
import com.hdmb.ireader.ui.activity.SearchArticleActivity;
import com.hdmb.ireader.ui.activity.SettingsActivity;
import com.hdmb.ireader.utils.ToastUtils;

/**
 * 作者    武博
 * 时间    2016/7/14 0014 15:12
 * 文件    TbReader
 * 描述
 */
public final class Navigator {

    /**
     * 检索图书
     * @param context
     */
    public static void openSearchArticle(@NonNull Context context) {
        context.startActivity(new Intent(context, SearchArticleActivity.class));
    }

    /**
     * 在浏览器中打开
     * @param context
     * @param url
     */
    public static void openInBrowser(@NonNull Context context, @NonNull String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (intent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(intent);
        } else {
            ToastUtils.with(context).show("系统中没有安装浏览器");
        }
    }

    public static void openOffline(@NonNull Context context){
        context.startActivity(new Intent(context, OfflineActivity.class));
    }

    public static void openSettings(@NonNull Context context){
        context.startActivity(new Intent(context, SettingsActivity.class));
    }

    public static void openMore(@NonNull Context context){
        context.startActivity(new Intent(context, MoreActivity.class));
    }

    public static void openAbout(@NonNull Context context){
        context.startActivity(new Intent(context, AboutActivity.class));
    }

    public static void openEmail(@NonNull Context context, @NonNull String email, @NonNull String subject, @NonNull String text) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setData(Uri.parse("mailto:" + email));
        if (intent.resolveActivity(context.getPackageManager()) != null) {
            intent.putExtra(Intent.EXTRA_SUBJECT, subject);
            intent.putExtra(Intent.EXTRA_TEXT, text);
            context.startActivity(intent);
        } else {
            ToastUtils.with(context).show("系统中没有安装邮件客户端");
        }
    }
}
