package com.hdmb.ireader.mvp.view;

/**
 * 作者    武博
 * 时间    2016/7/11 0011 15:44
 * 文件    TbReader
 * 描述
 */
public interface LauncherView {

    /**
     * 显示检查补丁进度条
     */
    void showCheckPatchsProgressBar();
    /**
     * 隐藏检查补丁进度条
     */
    void hideCheckPatchsProgressBar();

    /**
     * 显示下载补丁进度条
     * @param patchCount 补丁数量
     * @param patchIndex 当前补丁
     * @param progress 已经下载或上传字节数
     * @param total    总字节数
     * @param done     是否完成
     */
    void showLoadPatchProgressBar(int patchCount, int patchIndex, long progress, long total, boolean done);
    /**
     * 隐藏下载补丁进度条
     */
    void hideLoadPatchProgressBar();

    void startMainActivity();

    void updateApiHost(String apiHost);

    void checkPatchs();
}
