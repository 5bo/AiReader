package com.hdmb.ireader.ui.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;

import com.hdmb.ireader.mvp.presenter.BasePresenter;

/**
 * 作者    武博
 * 时间    2016/7/11 0011 15:35
 * 文件    TbReader
 * 描述
 */
public abstract class BaseFragment<P extends BasePresenter> extends Fragment {

    protected Context mContext;
    protected P mPresenter;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mContext = getActivity();
        mPresenter = createPresenter();
        if (mPresenter != null) {
            mPresenter.onCreate();
        }
    }

    protected abstract P createPresenter();

    @Override
    public void onResume() {
        super.onResume();
//        MobclickAgent.onResume(this);
    }

    @Override
    public void onPause() {
        super.onPause();
//        MobclickAgent.onPause(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.onDestroy();
        }
    }
}
