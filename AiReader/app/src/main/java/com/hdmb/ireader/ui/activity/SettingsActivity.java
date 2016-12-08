package com.hdmb.ireader.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;

import com.tb.hdmb.R;
import com.hdmb.ireader.mvp.presenter.BasePresenter;
import com.hdmb.ireader.ui.base.StatusBarActivity;
import com.hdmb.ireader.ui.listener.NavigationFinishClickListener;
import com.hdmb.ireader.ui.storeage.SettingShared;
import com.hdmb.ireader.utils.ThemeUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 作者    武博
 * 时间    2016/8/22 0022 15:53
 * 文件    TbReader
 * 描述
 */
public class SettingsActivity extends StatusBarActivity<BasePresenter> {
    @BindView(R.id.toolbar)
    protected Toolbar toolbar;

    @BindView(R.id.switch_notification)
    protected SwitchCompat switchNotification;

    @BindView(R.id.switch_theme_dark)
    protected SwitchCompat switchThemeDark;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ThemeUtils.configThemeBeforeOnCreate(this, R.style.AppThemeLight, R.style.AppThemeDark);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        ButterKnife.bind(this);
        toolbar.setNavigationOnClickListener(new NavigationFinishClickListener(this));

        switchNotification.setChecked(SettingShared.isEnableNotification(this));
        switchThemeDark.setChecked(SettingShared.isEnableThemeDark(this));
    }


    @OnClick(R.id.btn_notification)
    protected void onBtnNotificationClick() {
        switchNotification.toggle();
        SettingShared.setEnableNotification(this, switchNotification.isChecked());
    }

    @OnClick(R.id.btn_theme_dark)
    protected void onBtnThemeDarkClick() {
        switchThemeDark.toggle();
        SettingShared.setEnableThemeDark(this, switchThemeDark.isChecked());
        ThemeUtils.notifyThemeApply(this, false);
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }
}
