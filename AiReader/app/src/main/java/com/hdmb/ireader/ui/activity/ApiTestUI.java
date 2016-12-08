package com.hdmb.ireader.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.tb.hdmb.R;
import com.hdmb.ireader.mvp.presenter.TestPresenter;
import com.hdmb.ireader.mvp.presenter.impl.TestPresenterImpl;
import com.hdmb.ireader.mvp.view.TestView;
import com.hdmb.ireader.ui.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 作者    武博
 * 时间    2016/7/14 0014 10:04
 * 文件    TbReader
 * 描述
 */
public class ApiTestUI extends BaseActivity<TestPresenter> implements TestView {

    @BindView(R.id.textView)
    TextView textView;
    @BindView(R.id.refresh)
    Button refresh;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        ButterKnife.bind(this);
        mPresenter.requestTestApi_get();
    }

    @Override
    protected TestPresenter createPresenter() {
        return new TestPresenterImpl(this);
    }

    @Override
    public void showText(String text) {
        textView.setText(text);
    }

    @OnClick(R.id.refresh)
    void refresh(View view){
        mPresenter.requestTestApi_get();
    }
}
