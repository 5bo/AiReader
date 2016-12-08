package com.hdmb.ireader.mvp.model.impl;

import com.hdmb.ireader.MyApplication;
import com.hdmb.ireader.api.HttpManager;
import com.hdmb.ireader.mvp.model.LauncherModel;
import com.hdmb.ireader.resp.Patch;

import java.util.List;

import okhttp3.ResponseBody;
import rx.Observable;

/**
 * 作者    武博
 * 时间    2016/7/11 0011 16:21
 * 文件    TbReader
 * 描述
 */
public class LauncherModelImpl implements LauncherModel {
    @Override
    public Observable<List<Patch>> checkPatchs() {
        String versionName = MyApplication.VSERSION_NAME;
        String patchVersionName = MyApplication.PATCH_VERSION_NAME;
        return HttpManager.getInstance().getApiService().checkPatchs(versionName, patchVersionName);
    }

    @Override
    public Observable<ResponseBody> loadPatch(String url) {
        return HttpManager.getInstance().getDownloadService().downloadFileFromUrl(url);
    }
}
