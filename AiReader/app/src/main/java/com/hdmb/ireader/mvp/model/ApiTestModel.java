package com.hdmb.ireader.mvp.model;

import com.hdmb.ireader.api.Resp;

import rx.Observable;

/**
 * 作者    武博
 * 时间    2016/7/14 0014 10:05
 * 文件    TbReader
 * 描述
 */
public interface ApiTestModel {

    Observable<Resp> loadTestApi_get();
}
