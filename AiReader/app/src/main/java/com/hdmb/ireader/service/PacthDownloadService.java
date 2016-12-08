package com.hdmb.ireader.service;

import android.app.IntentService;
import android.content.Intent;
import android.os.Environment;
import android.text.TextUtils;

import com.hdmb.ireader.MyApplication;
import com.hdmb.ireader.api.HttpManager;
import com.hdmb.ireader.utils.FileUtils;

import java.io.File;
import java.io.IOException;

import okhttp3.ResponseBody;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 作者    武博
 * 时间    2016/7/11 0011 12:01
 * 文件    TbReader
 * 描述
 */
public class PacthDownloadService extends IntentService {

    public PacthDownloadService() {
        super("PacthDownloadService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            String downloadUrl = intent.getStringExtra("url");

            if (!TextUtils.isEmpty(downloadUrl)) {
                downloadPatch(downloadUrl);
            }
        }
    }

    private void downloadPatch(String downloadUrl) {
        File dir = new File(Environment.getExternalStorageDirectory() + "/shine/patch");
        if (!dir.exists()) {
            dir.mkdir();
        }
        File patchFile = new File(dir, String.valueOf(System.currentTimeMillis()) + ".apatch");
        downloadFile(downloadUrl, patchFile);
    }

    private void downloadFile(String downloadUrl, File file) {
        HttpManager.getInstance().getDownloadService().downloadFileFromUrl(downloadUrl)
                .subscribeOn(Schedulers.newThread())// 在新线程执行
                .map((ResponseBody responseBody) -> {
                    FileUtils.inputStreamToFile(responseBody.byteStream(), file);
                    return file;
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<File>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(File file) {
                        if (file.exists() && file.length() > 0) {
                            try {
                                MyApplication.getInstance().getPatchManager().addPatch(file.getAbsolutePath());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
    }
}
