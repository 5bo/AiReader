package com.hdmb.ireader.mvp.model.impl;

import com.hdmb.ireader.api.HttpManager;
import com.hdmb.ireader.api.Resp;
import com.hdmb.ireader.mvp.model.ApiTestModel;

import rx.Observable;

/**
 * 作者    武博
 * 时间    2016/7/14 0014 10:06
 * 文件    TbReader
 * 描述
 */
public class ApiTestModelImpl implements ApiTestModel {
    @Override
    public Observable<Resp> loadTestApi_get() {
        return HttpManager.getInstance().getTestService().ireaderApi();
    }
}
