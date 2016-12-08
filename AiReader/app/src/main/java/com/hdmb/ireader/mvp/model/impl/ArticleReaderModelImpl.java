package com.hdmb.ireader.mvp.model.impl;

import com.hdmb.ireader.api.HttpManager;
import com.hdmb.ireader.mvp.model.ArticleReaderModel;
import com.hdmb.ireader.resp.ArticleDirectory;
import com.hdmb.ireader.resp.ArticlePartContent;

import rx.Observable;

/**
 * 作者    武博
 * 时间    2016/7/15 0015 15:31
 * 文件    TbReader
 * 描述
 */
public class ArticleReaderModelImpl implements ArticleReaderModel {

    @Override
    public Observable<ArticlePartContent> requestArticlePartContent(String articleId, int partId) {
        return HttpManager.getInstance().getApiService().queryArticlePartContent(articleId, partId);
    }

    @Override
    public Observable<ArticleDirectory> requestArticleDirectory(String articleId) {
        return HttpManager.getInstance().getApiService().queryArticleDirectory(articleId);
    }

}
