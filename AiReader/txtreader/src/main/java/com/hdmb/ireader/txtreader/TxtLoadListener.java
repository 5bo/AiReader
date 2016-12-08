package com.hdmb.ireader.txtreader;

import com.hdmb.ireader.txtreader.bean.TxtError;

/**
 * 作者    武博
 * 时间    2016/8/8 0008 16:05
 * 文件    TbReader
 * 描述
 */
public interface TxtLoadListener {
    void onLoadSuccess();

    void onError(TxtError error);
}
