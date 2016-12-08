package com.hdmb.ireader.api.progress;

/**
 * 作者    武博
 * 时间    2016/7/11 0011 15:15
 * 文件    TbReader
 * 描述
 */
public interface ProgressListener {
    /**
     * @param progress 已经下载或上传字节数
     * @param total    总字节数
     * @param done     是否完成
     */
    void onProgress(long progress, long total, boolean done);
}
