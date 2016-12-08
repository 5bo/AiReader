package com.hdmb.ireader.ui.listener;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.view.View;

/**
 * 作者    武博
 * 时间    2016/7/15 0015 9:49
 * 文件    TbReader
 * 描述
 */
public class NavigationFinishClickListener implements View.OnClickListener {

    private final Activity activity;

    public NavigationFinishClickListener(@NonNull Activity activity) {
        this.activity = activity;
    }

    @Override
    public void onClick(View view) {
        ActivityCompat.finishAfterTransition(activity);
    }
}
