package com.hdmb.ireader.txtreader;

import com.hdmb.ireader.txtreader.bean.TxtError;

/**
 * 作者    武博
 * 时间    2016/8/8 0008 15:40
 * 文件    TbReader
 * 描述
 */
public interface Transformer {

    public void postResult(Boolean t);

    public void postError(TxtError txterror);
}
