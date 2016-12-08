package com.hdmb.ireader.api;

import android.os.Environment;

import com.hdmb.ireader.MyApplication;
import com.hdmb.ireader.api.progress.ProgressHelper;
import com.hdmb.ireader.utils.Log;
import com.hdmb.ireader.utils.NetUtils;
import com.hdmb.ireader.utils.Storeage;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 作者    武博
 * 时间    2016/7/11 0011 13:59
 * 文件    TbReader
 * 描述
 */
public class HttpManager {

    private static HttpManager instance;
    private Retrofit mDFRetrofit;
    public static String HOST;
    private static final String CACHE_CONTROL = "Cache-Control";

    private static Interceptor REWRITE_CACHE_CONTROL_INTERCEPTOR;

    private static File httpCacheDirectory;
    private static int cacheSize = 10 * 1024 * 1024; // 10 MiB
    private static Cache cache;
    private static OkHttpClient client;

    public static HttpManager getInstance() {
        if (instance == null)
            instance = new HttpManager();
        return instance;
    }

    private HttpManager() {
        HOST = Storeage.getApiHost(MyApplication.getInstance());
        httpCacheDirectory = new File(Environment.getExternalStorageDirectory().getPath() + Storeage.CACHE_PATH, "httpCache");
        cache = new Cache(httpCacheDirectory, cacheSize);
        REWRITE_CACHE_CONTROL_INTERCEPTOR = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                if (MyApplication.getInstance().httpLog)
                    Log.e("requestUrl", chain.request().url().toString());
                Response originalResponse = chain.proceed(chain.request());
                if (NetUtils.isNetworkAvailable(MyApplication.getInstance())) {
                    int maxAge = 60; // 在线缓存在1分钟内可读取
                    return originalResponse.newBuilder()
                            .removeHeader("Pragma")
                            .removeHeader(CACHE_CONTROL)
                            .header(CACHE_CONTROL, "public, max-age=" + maxAge)
                            .build();
                } else {
                    int maxStale = 60 * 60 * 24 * 28; // 离线时缓存保存4周
                    Log.i("ApiRequest", "离线缓存");
                    return originalResponse.newBuilder()
                            .removeHeader("Pragma")
                            .removeHeader(CACHE_CONTROL)
                            .header(CACHE_CONTROL, "public, only-if-cached, max-stale=" + maxStale)
                            .build();
                }
            }
        };

        client = new OkHttpClient.Builder()
                .addNetworkInterceptor(REWRITE_CACHE_CONTROL_INTERCEPTOR)
                .addInterceptor(REWRITE_CACHE_CONTROL_INTERCEPTOR)//不添加离线缓存无效
                .connectTimeout(10, TimeUnit.SECONDS)//设置超时时间
                .cache(cache)
                .build();

        OkHttpClient.Builder builder = ProgressHelper.addProgress(client.newBuilder());

        mDFRetrofit = new Retrofit.Builder().baseUrl(HOST)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(builder.build())
                .build();
    }

    public DownloadService getDownloadService() {
        return mDFRetrofit.create(DownloadService.class);
    }

    public ApiService getApiService() {
        return mDFRetrofit.create(ApiService.class);
    }

    public TestService getTestService() {
        return mDFRetrofit.create(TestService.class);
    }
}
