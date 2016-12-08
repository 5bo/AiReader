package com.hdmb.ireader.mvp.presenter.impl;

import com.hdmb.ireader.MyApplication;
import com.hdmb.ireader.api.progress.ProgressHandler;
import com.hdmb.ireader.api.progress.ProgressHelper;
import com.hdmb.ireader.mvp.model.LauncherModel;
import com.hdmb.ireader.mvp.model.impl.LauncherModelImpl;
import com.hdmb.ireader.mvp.presenter.LauncherPresenter;
import com.hdmb.ireader.mvp.view.LauncherView;
import com.hdmb.ireader.resp.Patch;
import com.hdmb.ireader.utils.FileUtils;
import com.hdmb.ireader.utils.Utils;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import rx.Observable;
import rx.Subscriber;

/**
 * 作者    武博
 * 时间    2016/7/11 0011 16:26
 * 文件    TbReader
 * 描述
 */
public class LauncherPresenterImpl extends BasePresenterImpl implements LauncherPresenter {

    private static final String TAG = "LauncherPresenterImpl";
    private LauncherModel mLauncherModel;
    private LauncherView mLauncherView;
    private static int mDownloadPatchIndex = 0;
    private static List<Patch> mPatchs = new ArrayList<>();

    Subscriber<List<Patch>> mPatchSubscriber = new Subscriber<List<Patch>>() {
        @Override
        public void onCompleted() {
            mLauncherView.hideCheckPatchsProgressBar();
        }

        @Override
        public void onError(Throwable e) {
            mLauncherView.startMainActivity();
        }

        @Override
        public void onNext(List<Patch> patches) {
            mPatchs = patches;
            mLauncherView.hideCheckPatchsProgressBar();
            if (patches != null) {
                downloadPatch();
            } else {
                mLauncherView.startMainActivity();
            }
        }
    };


    public LauncherPresenterImpl(LauncherView launcherView) {
        mLauncherModel = new LauncherModelImpl();
        mLauncherView = launcherView;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        /**
         * 监听下载进度
         */
        ProgressHelper.setProgressHandler(new ProgressHandler() {

            @Override
            protected void onProgress(long progress, long total, boolean done) {
                mLauncherView.showLoadPatchProgressBar(mPatchs.size(), mDownloadPatchIndex, progress, total, done);
            }
        });
    }

    @Override
    public void checkedPatchs() {
        mLauncherView.showCheckPatchsProgressBar();
        toSubscribe(mLauncherModel.checkPatchs(), mPatchSubscriber);
    }

    @Override
    public void downloadPatch() {
        File dir = new File("/sdcard/1tbjy_crash/patch");
        if (!dir.exists()) {
            dir.mkdir();
        }
        File patchFile = new File(dir, String.valueOf(System.currentTimeMillis()) + ".apatch");
        String downloadUrl = mPatchs.get(mDownloadPatchIndex).getPatchUrl();
        toSubscribe(mLauncherModel.loadPatch(downloadUrl)
                .map((ResponseBody responseBody) -> {
                    FileUtils.inputStreamToFile(responseBody.byteStream(), patchFile);
                    return patchFile;
                }), new Subscriber<File>() {
            @Override
            public void onCompleted() {
                mLauncherView.hideLoadPatchProgressBar();
            }

            @Override
            public void onError(Throwable e) {
                mLauncherView.startMainActivity();
            }

            @Override
            public void onNext(File file) {
                loadPatch(file);
                mLauncherView.hideLoadPatchProgressBar();
                if (mDownloadPatchIndex < mPatchs.size() - 1) {
                    mDownloadPatchIndex++;
                    downloadPatch();
                } else {
                    mLauncherView.startMainActivity();
                }
            }
        });
    }

    @Override
    public void loadPatch(File file) {
        if (file.exists() && file.length() > 0) {
            try {
                MyApplication.getInstance().getPatchManager().addPatch(file.getAbsolutePath());
                Utils.setPatchVersionName(MyApplication.getInstance(), mPatchs.get(mDownloadPatchIndex).getPatchName());
            } catch (IOException e) {
                e.printStackTrace();
                mLauncherView.startMainActivity();
            }
        }
    }

    @Override
    public void updateAPIHOST() {
        toSubscribe(Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext(getApiHost());
            }
        }), new Subscriber<String>() {
            @Override
            public void onCompleted() {
                mLauncherView.checkPatchs();
            }

            @Override
            public void onError(Throwable e) {
                mLauncherView.checkPatchs();
            }

            @Override
            public void onNext(String s) {
                mLauncherView.updateApiHost(s);
            }
        });
    }

    private String getApiHost(){
        String url = "http://blog.csdn.net/haizai219/article/details/52288535";
        Document doc = null;
        try {
            Connection conn = Jsoup.connect(url);
            conn.header("User-Agent", "Mozilla/5.0 (Linux; Android 6.0; Nexus 5 Build/MRA58N) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.76 Mobile Safari/537.36");
            conn.header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
            doc = conn.get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Elements elements = doc.getElementsByTag("div");
        for (Element element : elements) {
            if (element.className().equals("article_c")){
                return element.text();
            }
        }
        return "";
    }
}
