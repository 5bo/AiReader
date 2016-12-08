package com.hdmb.ireader.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.hdmb.ireader.mvp.presenter.BasePresenter;
import com.hdmb.ireader.ui.base.StatusBarActivity;
import com.hdmb.ireader.ui.listener.NavigationFinishClickListener;
import com.hdmb.ireader.utils.ThemeUtils;
import com.hdmb.ireader.utils.Utils;
import com.hdmb.ireader.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 作者    武博
 * 时间    2016/8/22 0022 15:58
 * 文件    TbReader
 * 描述
 */
public class AboutActivity extends StatusBarActivity<BasePresenter> {

    @BindView(R.id.toolbar)
    protected Toolbar toolbar;
    @BindView(R.id.appName)
    TextView appName;
    @BindView(R.id.versionName)
    TextView versionName;
    @BindView(R.id.reward)
    Button reward;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ThemeUtils.configThemeBeforeOnCreate(this, R.style.AppThemeLight, R.style.AppThemeDark);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        ButterKnife.bind(this);
        toolbar.setNavigationOnClickListener(new NavigationFinishClickListener(this));

        appName.setText(R.string.app_name);
        versionName.setText("版本号:"+ Utils.getVersionName(mContext));
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @OnClick(R.id.reward)
    void reward(View v){
        String url = "weixin://wxpay/bizpayurl?pr=2DAIDXj";
        Uri uri = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }
}
