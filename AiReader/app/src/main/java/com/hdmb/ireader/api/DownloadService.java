package com.hdmb.ireader.api;

import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Streaming;
import retrofit2.http.Url;
import rx.Observable;

/**
 * 作者    武博
 * 时间    2016/7/11 0011 14:41
 * 文件    TbReader
 * 描述
 */
public interface DownloadService {
    @GET
    @Streaming
    Observable<ResponseBody> downloadFileFromUrl(@Url String url);
}
