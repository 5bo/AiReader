package com.hdmb.ireader.mvp.view;

import com.hdmb.ireader.resp.Article;

import java.util.List;

/**
 * 作者    武博
 * 时间    2016/7/13 0013 18:08
 * 文件    TbReader
 * 描述
 */
public interface MyBookshelfView {
    void showMyBookshelfArticles(List<Article> articles);
}
