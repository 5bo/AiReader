package com.hdmb.ireader.mvp.model;

import com.hdmb.ireader.resp.ArticleDirectory;
import com.hdmb.ireader.resp.ArticlePartContent;

import rx.Observable;

/**
 * 作者    武博
 * 时间    2016/7/15 0015 15:12
 * 文件    TbReader
 * 描述
 */
public interface ArticleReaderModel {

    Observable<ArticlePartContent> requestArticlePartContent(String articleId, int partId);

    Observable<ArticleDirectory> requestArticleDirectory(String articleId);
}
