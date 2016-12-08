package com.hdmb.ireader.mvp.model;

import com.hdmb.ireader.resp.Patch;

import java.util.List;

import okhttp3.ResponseBody;
import rx.Observable;

/**
 * 作者    武博
 * 时间    2016/7/11 0011 16:04
 * 文件    TbReader
 * 描述
 */
public interface LauncherModel {

    Observable<List<Patch>> checkPatchs();

    Observable<ResponseBody> loadPatch(String url);
}
