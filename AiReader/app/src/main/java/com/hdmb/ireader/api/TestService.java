package com.hdmb.ireader.api;

import retrofit2.http.GET;
import rx.Observable;

/**
 * 作者    武博
 * 时间    2016/7/14 0014 10:06
 * 文件    TbReader
 * 描述
 */
public interface TestService {

    @GET("ireader/api")
    Observable<Resp> ireaderApi();
}
