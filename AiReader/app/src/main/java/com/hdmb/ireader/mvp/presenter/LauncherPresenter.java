package com.hdmb.ireader.mvp.presenter;

import java.io.File;

/**
 * 作者    武博
 * 时间    2016/7/11 0011 15:42
 * 文件    TbReader
 * 描述
 */
public interface LauncherPresenter extends BasePresenter {

    void checkedPatchs();

    void downloadPatch();

    void loadPatch(File file);

    void updateAPIHOST();
}
