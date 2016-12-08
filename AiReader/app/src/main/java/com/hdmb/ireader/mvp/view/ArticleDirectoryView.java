package com.hdmb.ireader.mvp.view;

import com.hdmb.ireader.resp.ArticleDirectory;

/**
 * 作者    武博
 * 时间    2016/7/15 0015 10:14
 * 文件    TbReader
 * 描述
 */
public interface ArticleDirectoryView {

    /**
     * 显示图书目录
     * @param articleDirectory
     */
    void showDirectory(ArticleDirectory articleDirectory);

    /**
     * 去看书
     */
    void startArticleLook();

    /**
     * 显示加入书架状态
     * @param status
     */
    void showAddBookshelfStatus(Boolean status);
}
