package com.hdmb.ireader.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.PowerManager;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tb.hdmb.R;
import com.hdmb.ireader.mvp.presenter.ArticleReaderPresenter;
import com.hdmb.ireader.mvp.presenter.impl.ArticleReaderPresenterImpl;
import com.hdmb.ireader.mvp.view.ArticleReaderView;
import com.hdmb.ireader.resp.Article;
import com.hdmb.ireader.resp.ArticleDirectory;
import com.hdmb.ireader.txtreader.Config;
import com.hdmb.ireader.txtreader.PageSeparateListener;
import com.hdmb.ireader.txtreader.TxtLoadListener;
import com.hdmb.ireader.txtreader.TxtManager;
import com.hdmb.ireader.txtreader.TxtManagerImpl;
import com.hdmb.ireader.txtreader.TxtReader;
import com.hdmb.ireader.txtreader.TxtViewOnTouchListener;
import com.hdmb.ireader.txtreader.bean.TxtError;
import com.hdmb.ireader.txtreader.bean.TxtFile;
import com.hdmb.ireader.ui.base.BaseActivity;
import com.hdmb.ireader.ui.dialog.LoadingDialog;
import com.hdmb.ireader.ui.dialog.TxtReaderMenu;
import com.hdmb.ireader.ui.dialog.TxtReaderMenuLight;
import com.hdmb.ireader.ui.dialog.TxtReaderMenuParts;
import com.hdmb.ireader.ui.dialog.TxtReaderMenuTheme;
import com.hdmb.ireader.ui.dialog.TxtReaderMenuTypeface;
import com.hdmb.ireader.utils.HandlerUtils;
import com.hdmb.ireader.utils.RxUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.subscriptions.CompositeSubscription;

/**
 * 作者    武博
 * 时间    2016/7/27 0027 15:52
 * 文件    TbReader
 * 描述
 */
public class BookReaderActivity extends BaseActivity<ArticleReaderPresenter> implements ArticleReaderView {

    private static final int REQUEST_CODE = 0; // 请求码
    @BindView(R.id.txtReader)
    protected TxtReader txtReader;
    @BindView(R.id.errorLayout)
    protected LinearLayout errorLayout;
    @BindView(R.id.crash)
    protected TextView crash;
    protected LoadingDialog loadingDialog;

    private TxtFile txtFile;
    private TxtManager txtManager;
    private TxtReaderMenu menu;
    private TxtReaderMenuParts partsMenu;
    private TxtReaderMenuTypeface typefaceMenu;
    private TxtReaderMenuTheme themeMenu;
    private TxtReaderMenuLight lightMenu;

    private Article mArticle;
    private int mPartId = 0;
    private static boolean mSeparateStatus = false;
    private PowerManager.WakeLock wakeLock = null;
    private static final String EXTRA_ARTICLE = "article";
    private static boolean isRequestParts = false;

    CompositeSubscription mSubscriptions = new CompositeSubscription();

    public static void openBookReader(@NonNull Activity activity, @NonNull Article article) {
        Intent intent = new Intent(activity, BookReaderActivity.class);
        intent.putExtra(EXTRA_ARTICLE, article);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        ThemeUtils.configThemeBeforeOnCreate(this, R.style.AppThemeLight_FitsStatusBar, R.style.AppThemeDark_FitsStatusBar);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_article_reader);
        loadingDialog = new LoadingDialog(mContext);
        mSubscriptions = RxUtils.getNewCompositeSubIfUnsubscribed(mSubscriptions);

        ButterKnife.bind(this);
        mArticle = getIntent().getParcelableExtra(EXTRA_ARTICLE);
        mPartId = mArticle.getPartId();
        if (mArticle.getStatus().contains("连载"))
            isRequestParts = true;

        txtFile = new TxtFile();
        loadTxtFileToPart(mPartId);
        if (isRequestParts)
            mPresenter.loadArticleParts(mArticle.getId());
        initTxtReaderListener();
    }

    @Override
    public void onResume() {
        super.onResume();
        acquireWakeLock(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        releaseWakeLock();
    }

    // 获取锁
    public void acquireWakeLock(Context context) {
        if (wakeLock == null) {
            PowerManager powerManager = (PowerManager) (context
                    .getSystemService(Context.POWER_SERVICE));
            wakeLock = powerManager.newWakeLock(
                    PowerManager.SCREEN_DIM_WAKE_LOCK, "TxtReader");
            wakeLock.acquire();
        }
    }

    // 释放锁
    public void releaseWakeLock() {
        if (wakeLock != null && wakeLock.isHeld()) {
            wakeLock.release();
            wakeLock = null;
        }
    }

    private void initTxtReaderListener() {
        txtReader.setTxtPageChangeListsner((int pageIndex, int pageNums) -> {

        });
        txtReader.setPageSeparateListener(new PageSeparateListener() {
            @Override
            public void onSeparateStart() {
                mSeparateStatus = true;
            }

            @Override
            public void onSeparateDone() {
                mSeparateStatus = false;
            }
        });
        txtReader.setTxtViewOnTouchListener(new TxtViewOnTouchListener() {
            @Override
            public void onShowMenu() {
                showMenu();
            }

            @Override
            public void onOutSideAreaTouch() {
                hideMenu();
            }
        });
        txtReader.setOnPartChangeListener(part -> {
            loadTxtFileToPart(part);
        });
        partsMenu = new TxtReaderMenuParts(mContext, mArticle.getPartCount(), part -> {
            loadTxtFileToPart(part);
        });
        typefaceMenu = new TxtReaderMenuTypeface(mContext, new TxtReaderMenuTypeface.OnTxtReaderMenuTypefaceClickListener() {
            @Override
            public void onTextSizeChange(int textSize) {
                if (mSeparateStatus) {
                    typefaceMenu.setTextSize(textSize);
                    return;
                }
                Config.setTxtSize(mContext, textSize);
                txtManager.getConfig().setTextSize(textSize);
                txtManager.initSettings();
                txtManager.jumpToPage(1);
                txtManager.separatePage();
            }

            @Override
            public void onTypefaceChange(String textsort) {
                if (mSeparateStatus) {
                    typefaceMenu.setTypeface(textsort);
                    return;
                }
                Config.setTypeface(mContext, textsort);
                txtManager.getConfig().setFontType(textsort);
                txtManager.initSettings();
                txtManager.jumpToPage(1);
                txtManager.separatePage();
                txtManager.refreshBitmapText();
            }
        });
        themeMenu = new TxtReaderMenuTheme(mContext, (int theme) -> {
            Config.setTheme(mContext, theme);
            if (theme == TxtReaderMenuTheme.STYLE_BLACK) {
                txtManager.getConfig().setTextColor(Color.WHITE);
            } else {
                txtManager.getConfig().setTextColor(Color.BLACK);
            }
            txtManager.initSettings();
            txtManager.getConfig().setBackgroundColor(theme);
            txtManager.refreshBitmapBackground();
        });
        lightMenu = new TxtReaderMenuLight(mContext);
        menu = new TxtReaderMenu(mContext, new TxtReaderMenu.OnTxtReaderMenuClickListener() {
            @Override
            public void onPartsMenu() {
                showPartsMenu();
            }

            @Override
            public void onTypefaceMenu() {
                showTypefaceMenu();
            }

            @Override
            public void onThemeMenu() {
                showThemeMenu();
            }

            @Override
            public void onLightMenu() {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    // 判断是否有WRITE_SETTINGS权限
                    if (!Settings.System.canWrite(BookReaderActivity.this)) {
                        // 申请WRITE_SETTINGS权限
                        Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS,
                                Uri.parse("package:" + getPackageName()));
                        // REQUEST_CODE1是本次申请的请求码
                        startActivityForResult(intent, REQUEST_CODE);
                    } else {
                        showLightMenu();
                    }
                } else {
                    showLightMenu();
                }
            }
        });
    }

    private void loadTxtFileToPart(int part) {
//        if (part > mArticle.getPartCount()) {
//            ToastUtils.with(mContext).show("已经是最后一节");
//            return;
//        }
        mPartId = part;
        txtFile.setBookName(mArticle.getName() + "--第" + mPartId + "节");
        txtFile.setPart(part);
        mPresenter.loadArticlePartContent(mArticle.getId(), mPartId);
    }

    private void hideMenu() {
        menu.dismiss();
        partsMenu.dismiss();
        typefaceMenu.dismiss();
        themeMenu.dismiss();
        lightMenu.dismiss();
    }

    private void showMenu() {
        menu.showAtLocation(getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
    }

    private void showPartsMenu() {
        HandlerUtils.post(() -> {
            menu.dismiss();
            if (!partsMenu.isShowing()) {
                partsMenu.showAtLocation(getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
            }
        });
    }

    private void showTypefaceMenu() {
        HandlerUtils.post(() -> {
            menu.dismiss();
            if (!typefaceMenu.isShowing()) {
                typefaceMenu.showAtLocation(getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
            }
        });
    }

    private void showThemeMenu() {
        HandlerUtils.post(() -> {
            menu.dismiss();
            if (!themeMenu.isShowing()) {
                themeMenu.showAtLocation(getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
            }
        });
    }

    private void showLightMenu() {
        HandlerUtils.post(() -> {
            menu.dismiss();
            if (!lightMenu.isShowing()) {
                lightMenu.showAtLocation(getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
            }
        });
    }

    private void loadFile(String filePath) {
        txtFile.setFilePath(filePath);
        txtManager = new TxtManagerImpl(mContext, txtFile);
        txtManager.getConfig().setTextSize(Config.getTxtSize(this));
        txtManager.getConfig().setFontType(Config.getTypeface(this));
        txtManager.getConfig().setBackgroundColor(Config.getTheme(this));
        txtManager.initSettings();
        txtManager.setTxtLoadListener(new TxtLoadListener() {
            @Override
            public void onLoadSuccess() {
                HandlerUtils.post(() -> {
                    hideProgressBar();
                    errorLayout.setVisibility(View.GONE);
                });
            }

            @Override
            public void onError(TxtError txtError) {
                HandlerUtils.post(() -> {
                    hideProgressBar();
                    errorLayout.setVisibility(View.VISIBLE);
                });
            }
        });
        txtReader.setTxtManager(txtManager);
    }

    @Override
    protected ArticleReaderPresenter createPresenter() {
        return new ArticleReaderPresenterImpl(this, this);
    }

    @Override
    public void handleParts(ArticleDirectory articleDirectory) {
        mArticle.setStatus(articleDirectory.getStatus());
        mArticle.setPartCount(articleDirectory.getParts().size());
        partsMenu.setPartSize(articleDirectory.getParts().size());
        partsMenu.setScrollPosition(mPartId - 1);
    }

    @Override
    public void showContentView(String fileName) {
        loadFile(fileName);
        txtManager.loadTxtFile();
    }

    @Override
    public void showProgressBar(String message) {
        hideProgressBar();
        if (loadingDialog == null)
            loadingDialog = new LoadingDialog(mContext);
        loadingDialog.show();
        loadingDialog.setMessage(message);
    }

    @Override
    public void hideProgressBar() {
        if (loadingDialog != null && loadingDialog.isShowing())
            loadingDialog.hide();
    }

    @Override
    public void loadError(Throwable e) {
        StackTraceElement[] elements = e.getStackTrace();
        StringBuilder sb = new StringBuilder();
        for (StackTraceElement element : elements)
            sb.append(element.toString() + "\r\n");
        crash.setText(sb.toString());
        hideProgressBar();
        errorLayout.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        hideProgressBar();
        RxUtils.unsubscribeIfNotNull(mSubscriptions);
    }

    @OnClick(R.id.errorLayout)
    protected void refresh(View v) {
        mPresenter.loadArticlePartContent(mArticle.getId(), mPartId);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // 请求码是REQUEST_CODE，表示本次结果是申请WRITE_SETTINGS权限的结果
        if (requestCode == REQUEST_CODE) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                // 判断是否有WRITE_SETTINGS权限
                if (Settings.System.canWrite(this)) {
                    showLightMenu();
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}