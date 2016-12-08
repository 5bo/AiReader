package com.hdmb.ireader.ui.activity;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.tb.hdmb.R;
import com.hdmb.ireader.mvp.presenter.BasePresenter;
import com.hdmb.ireader.ui.Navigator;
import com.hdmb.ireader.ui.base.FullActivity;
import com.hdmb.ireader.ui.fragment.BookStoreFragment;
import com.hdmb.ireader.ui.fragment.MyBookshelfFragment;
import com.hdmb.ireader.utils.DisplayUtils;
import com.hdmb.ireader.utils.ThemeUtils;
import com.hdmb.ireader.utils.Themes;
import com.hdmb.ireader.utils.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends FullActivity implements NavigationView.OnNavigationItemSelectedListener {

    // 抽屉导航布局
    @BindView(R.id.drawerLayout)
    protected DrawerLayout drawerLayout;
    @BindView(R.id.navOffline)
    protected TextView navOffline;
    @BindView(R.id.navSettings)
    protected TextView navSettings;
    @BindView(R.id.navMore)
    protected TextView navMore;
    @BindView(R.id.navAbout)
    protected TextView navAbout;

    // 状态栏
    @BindView(R.id.statusBar)
    protected View centerAdaptStatusBar;
    @BindView(R.id.drawerStatusBar)
    protected View drawerStatusBar;
    @BindView(R.id.drawerSwitch)
    protected TextView drawerSwitch;
    @BindView(R.id.search)
    protected TextView search;
    @BindView(R.id.viewPager)
    protected ViewPager viewPager;
    @BindView(R.id.tabLayout)
    protected TabLayout tabLayout;

    private TabLayout.Tab mMyBookshelf;
    private TabLayout.Tab mBookStore;

    // 是否启用夜间模式
    private boolean enableThemeDark;
    // 首次按下返回键时间戳
    private long firstBackPressedTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        enableThemeDark = ThemeUtils.configThemeBeforeOnCreate(this, R.style.AppThemeLight_FitsStatusBar, R.style.AppThemeDark_FitsStatusBar);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        DisplayUtils.adaptStatusBar(this, centerAdaptStatusBar);
        DisplayUtils.adaptStatusBar(this, drawerStatusBar);
        initViewPager();
        initEvents();
    }

    private void initViewPager() {
        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                switch (position) {
                    case 0:
                        return new MyBookshelfFragment();
                    case 1:
                        return new BookStoreFragment();
                }
                return new MyBookshelfFragment();
            }

            @Override
            public int getCount() {
                return 2;
            }
        });
        viewPager.setOffscreenPageLimit(2);
        tabLayout.setupWithViewPager(viewPager);
        mMyBookshelf = tabLayout.getTabAt(0);
        mBookStore = tabLayout.getTabAt(1);
        mMyBookshelf.setText("书架");
        mBookStore.setText("书城");
    }

    private void initEvents() {
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab == tabLayout.getTabAt(0)) {
                    viewPager.setCurrentItem(0);
                } else if (tab == tabLayout.getTabAt(1)) {
                    viewPager.setCurrentItem(1);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        // 判断是否需要切换主题
        if (Themes.isEnableThemeDark(this) != enableThemeDark) {
            ThemeUtils.notifyThemeApply(this, true);
        }
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @OnClick(R.id.drawerSwitch)
    void drawerSwitch(View view) {
        if (drawerLayout.isDrawerOpen(Gravity.LEFT)) {
            drawerLayout.closeDrawer(Gravity.LEFT);
        } else {
            drawerLayout.openDrawer(Gravity.LEFT);
        }
    }

    @OnClick(R.id.navOffline)
    void navOffline(View v) {
        drawerLayout.closeDrawers();
        Navigator.openOffline(mContext);
    }

    @OnClick(R.id.navSettings)
    void navSettings(View v) {
        drawerLayout.closeDrawers();
        Navigator.openSettings(mContext);
    }

    @OnClick(R.id.navMore)
    void navMore(View v) {
        drawerLayout.closeDrawers();
//        Navigator.openMore(mContext);
        ToastUtils.with(mContext).show("工程师正在努力开发中……");
    }

    @OnClick(R.id.navAbout)
    void navAbout(View v) {
        drawerLayout.closeDrawers();
        Navigator.openAbout(mContext);
//        ToastUtils.with(mContext).show("工程师正在努力开发中……");
    }

    @OnClick(R.id.search)
    void searchArticles(View view) {
        Navigator.openSearchArticle(mContext);
    }

    /**
     * 返回键关闭导航
     */
    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            long secondBackPressedTime = System.currentTimeMillis();
            if (secondBackPressedTime - firstBackPressedTime > 2000) {
                ToastUtils.with(this).show("再按一次退出");
                firstBackPressedTime = secondBackPressedTime;
            } else {
                super.onBackPressed();
            }
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        item.setChecked(true);
        drawerLayout.closeDrawers();
        return true;
    }
}
