package com.hdmb.ireader.mvp.presenter.impl;

import com.hdmb.ireader.api.Resp;
import com.hdmb.ireader.mvp.model.ApiTestModel;
import com.hdmb.ireader.mvp.model.impl.ApiTestModelImpl;
import com.hdmb.ireader.mvp.presenter.TestPresenter;
import com.hdmb.ireader.mvp.view.TestView;
import com.hdmb.ireader.utils.Log;

import java.io.IOException;

import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;

/**
 * 作者    武博
 * 时间    2016/7/14 0014 10:11
 * 文件    TbReader
 * 描述
 */
public class TestPresenterImpl extends BasePresenterImpl implements TestPresenter {

    ApiTestModel mApiTestModel;
    TestView mTestView;

    public TestPresenterImpl(TestView testView) {
        mApiTestModel = new ApiTestModelImpl();
        this.mTestView = testView;
    }

    @Override
    public void requestTestApi_get() {
        toSubscribe(mApiTestModel.loadTestApi_get(), new Subscriber<Resp>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                HttpException he = (HttpException) e;
                int code = he.code();
                String message = he.message();
                Log.e("onError", "responseCode:" + code + ";responseMsg:" + message);
                try {
                    mTestView.showText("responseCode:" + code + ";responseMsg:" + message + ";body:" + he.response().errorBody().string());
                } catch (IOException e1) {
                    e1.printStackTrace();
                }

            }

            @Override
            public void onNext(Resp resp) {
                android.util.Log.e("onNest", "response: " + resp.toString());
                mTestView.showText("response: " + resp.toString());
            }
        });
    }

}
