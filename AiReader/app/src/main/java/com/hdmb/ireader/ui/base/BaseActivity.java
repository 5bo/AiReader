package com.hdmb.ireader.ui.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;

import com.hdmb.ireader.mvp.presenter.BasePresenter;

/**
 * 作者    武博
 * 时间    2016/7/11 0011 15:35
 * 文件    TbReader
 * 描述
 */
public abstract class BaseActivity<P extends BasePresenter> extends AppCompatActivity {

    protected Context mContext;
    protected P mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        mPresenter = createPresenter();
        if (mPresenter != null) {
            mPresenter.onCreate();
        }
    }

    protected abstract P createPresenter();

    @Override
    protected void onResume() {
        super.onResume();
//        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
//        MobclickAgent.onPause(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.onDestroy();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            back();
        }
        return super.onKeyDown(keyCode, event);
    }

    protected void back() {
    }
}
