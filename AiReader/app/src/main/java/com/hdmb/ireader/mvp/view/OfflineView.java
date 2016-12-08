package com.hdmb.ireader.mvp.view;

import com.hdmb.ireader.resp.Article;

import java.util.List;

/**
 * 作者    武博
 * 时间    2016/8/24 0024 14:13
 * 文件    TbReader
 * 描述
 */
public interface OfflineView {
    void showArticles(List<Article> articles);
}
