package com.hdmb.ireader.mvp.model;

import com.hdmb.ireader.resp.ArticleDirectory;

import rx.Observable;

/**
 * 作者    武博
 * 时间    2016/7/15 0015 9:59
 * 文件    TbReader
 * 描述
 */
public interface ArticleDirectoryModel {

    Observable<ArticleDirectory> requestArticleDirectory(String articleId);
}
