package com.hdmb.ireader.mvp.view;

import com.hdmb.ireader.resp.ArticleDirectory;

/**
 * 作者    武博
 * 时间    2016/7/18 0018 15:11
 * 文件    TbReader
 * 描述
 */
public interface ArticleReaderView {

    void handleParts(ArticleDirectory articleDirectory);

    void showContentView(String fileName);

    void showProgressBar(String message);

    void hideProgressBar();

    void loadError(Throwable e);
}
