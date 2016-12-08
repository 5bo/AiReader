package com.hdmb.ireader.api.progress;

import com.hdmb.ireader.utils.Log;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;

/**
 * 作者    武博
 * 时间    2016/7/11 0011 15:17
 * 文件    TbReader
 * 描述
 */
public class ProgressHelper {
    private static Progress progress = new Progress();
    private static ProgressHandler mProgressHandler;

    public static OkHttpClient.Builder addProgress(OkHttpClient.Builder builder) {

        if (builder == null) {
            builder = new OkHttpClient.Builder();
        }

        ProgressListener progressListener = new ProgressListener() {
            //该方法在子线程中运行
            @Override
            public void onProgress(long progress, long total, boolean done) {
                Log.e("progress:", String.format("%d%% done\n", progress / total * 100));
                if (mProgressHandler == null) {
                    return;
                }
                ProgressHelper.progress.setBytesRead(progress);
                ProgressHelper.progress.setContentLength(total);
                ProgressHelper.progress.setDone(done);
                mProgressHandler.sendMessage(ProgressHelper.progress);
            }
        };

        //添加拦截器，自定义ResponseBody，添加下载进度
        builder.networkInterceptors().add((Interceptor.Chain chain) -> {
            okhttp3.Response originalResponse = chain.proceed(chain.request());
            return originalResponse.newBuilder().body(
                    new ProgressResponseBody(originalResponse.body(), progressListener))
                    .build();

        });

        return builder;
    }

    public static void setProgressHandler(ProgressHandler progressHandler) {
        mProgressHandler = progressHandler;
    }
}
