package com.hdmb.ireader.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.tb.hdmb.R;
import com.hdmb.ireader.mvp.presenter.LauncherPresenter;
import com.hdmb.ireader.mvp.presenter.impl.LauncherPresenterImpl;
import com.hdmb.ireader.mvp.view.LauncherView;
import com.hdmb.ireader.ui.base.BaseActivity;
import com.hdmb.ireader.utils.PermissionsChecker;
import com.hdmb.ireader.utils.SharedPreferencesUtils;
import com.hdmb.ireader.utils.Storeage;

import org.joda.time.DateTime;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 作者    武博
 * 时间    2016/7/11 0011 15:34
 * 文件    TbReader
 * 描述
 */
public class LauncherActivity extends BaseActivity<LauncherPresenter> implements LauncherView {

    private static final String SP_API_HOST_DATE = "api_host";

    private static final int REQUEST_CODE = 0; // 请求码
    private PermissionsChecker mPermissionsChecker; // 权限检测器
    // 所需的全部权限
    static final String[] PERMISSIONS = new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.WAKE_LOCK
    };

    @BindView(R.id.download)
    LinearLayout download;
    @BindView(R.id.downloadHint)
    TextView downloadHint;
    @BindView(R.id.downloadProgrss)
    ProgressBar downloadProgress;

    @Override
    protected LauncherPresenterImpl createPresenter() {
        return new LauncherPresenterImpl(this);
    }

    @Override
    public void showCheckPatchsProgressBar() {
        download.setVisibility(View.GONE);
    }

    @Override
    public void hideCheckPatchsProgressBar() {
    }

    @Override
    public void showLoadPatchProgressBar(int patchCount, int patchIndex, long progress, long total, boolean done) {
        downloadHint.setText("加载数据中(" + patchIndex + "/" + patchCount + ")..." + progress + "/" + total);
        downloadProgress.setMax((int) total);
        downloadProgress.setProgress((int) progress);
    }

    @Override
    public void hideLoadPatchProgressBar() {
        download.setVisibility(View.GONE);
    }

    @Override
    public void startMainActivity() {
        startActivity(new Intent(this, MainActivity.class));
        back();
    }

    @Override
    public void updateApiHost(String apiHost) {
        if (!TextUtils.isEmpty(apiHost))
            Storeage.setApiHost(mContext, apiHost);
    }

    @Override
    public void checkPatchs() {
        mPresenter.checkedPatchs();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        ButterKnife.bind(this);
        mPermissionsChecker = new PermissionsChecker(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // 缺少权限时, 进入权限配置页面
        if (mPermissionsChecker.lacksPermissions(PERMISSIONS)) {
            PermissionsActivity.startActivityForResult(this, REQUEST_CODE, PERMISSIONS);
        } else {
            mPresenter.updateAPIHOST();
        }
    }

    @Override
    protected void back() {
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 拒绝时, 关闭页面, 缺少主要权限, 无法运行
        if (requestCode == REQUEST_CODE && resultCode == PermissionsActivity.PERMISSIONS_DENIED) {
            finish();
        } else {
            mPresenter.checkedPatchs();
            checkApiHost();
        }
    }

    private void checkApiHost() {
        String date = new DateTime().toString("yyyyMMdd");
        String apihostDate = SharedPreferencesUtils.getString(mContext, SP_API_HOST_DATE, "");
        if (!date.equals(apihostDate)) {
            mPresenter.updateAPIHOST();
        }
    }
}
