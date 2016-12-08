package com.hdmb.ireader.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.tb.hdmb.R;
import com.hdmb.ireader.mvp.presenter.BasePresenter;
import com.hdmb.ireader.ui.Navigator;
import com.hdmb.ireader.ui.base.StatusBarActivity;
import com.hdmb.ireader.ui.listener.NavigationFinishClickListener;
import com.hdmb.ireader.utils.ThemeUtils;

import org.joda.time.DateTime;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 作者    武博
 * 时间    2016/8/22 0022 16:12
 * 文件    TbReader
 * 描述
 */
public class CrashActivity extends StatusBarActivity<BasePresenter> implements Toolbar.OnMenuItemClickListener {

    private static final String EXTRA_E = "e";

    @BindView(R.id.toolbar)
    protected Toolbar toolbar;
    @BindView(R.id.info)
    protected TextView info;
    private String crashLog;

    public static void start(@NonNull Context context, @NonNull Throwable e) {
        Intent intent = new Intent(context, CrashActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Bundle bundle = new Bundle();
        bundle.putSerializable(EXTRA_E, e);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ThemeUtils.configThemeBeforeOnCreate(this, R.style.AppThemeLight, R.style.AppThemeDark);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crash);
        ButterKnife.bind(this);

        toolbar.setNavigationOnClickListener(new NavigationFinishClickListener(this));
        toolbar.inflateMenu(R.menu.crash);
        toolbar.setOnMenuItemClickListener(this);

        Throwable e = (Throwable) getIntent().getSerializableExtra(EXTRA_E);
        StringBuilder sb = new StringBuilder();
        sb.append("生产厂商：\n");
        sb.append(Build.MANUFACTURER).append("\n\n");
        sb.append("手机型号：\n");
        sb.append(Build.MODEL).append("\n\n");
        sb.append("系统版本：\n");
        sb.append(Build.VERSION.RELEASE).append("\n\n");
        sb.append("异常时间：\n");
        sb.append(new DateTime()).append("\n\n");
        sb.append("异常类型：\n");
        sb.append(e.getClass().getName()).append("\n\n");
        sb.append("异常信息：\n");
        sb.append(e.getMessage()).append("\n\n");
        sb.append("异常堆栈：\n");
        Writer writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        e.printStackTrace(printWriter);
        Throwable cause = e.getCause();
        while (cause != null) {
            cause.printStackTrace(printWriter);
            cause = cause.getCause();
        }
        printWriter.close();
        sb.append(writer.toString());
        crashLog = sb.toString();

        info.setText(crashLog);
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_send:
                Navigator.openEmail(
                        this,
                        "wb_1456@163.com",
                        "来自 Reader-"  + " 的客户端崩溃日志",
                        crashLog
                );
                return true;
            default:
                return false;
        }
    }
}
