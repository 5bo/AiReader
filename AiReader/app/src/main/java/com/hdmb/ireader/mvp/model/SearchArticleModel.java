package com.hdmb.ireader.mvp.model;

import com.hdmb.ireader.resp.Article;

import java.util.List;

import rx.Observable;

/**
 * 作者    武博
 * 时间    2016/7/14 0014 15:16
 * 文件    TbReader
 * 描述
 */
public interface SearchArticleModel {

    /**
     *
     * @param keyword
     * @return
     */
    Observable<List<Article>> queryArticles(String keyword);
}
