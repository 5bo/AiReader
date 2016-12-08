package com.hdmb.ireader.mvp.view;

import com.hdmb.ireader.resp.Article;

import java.util.List;

/**
 * 作者    武博
 * 时间    2016/7/14 0014 15:22
 * 文件    TbReader
 * 描述
 */
public interface SearchArticleView {

    void showArticles(List<Article> articles);
}
