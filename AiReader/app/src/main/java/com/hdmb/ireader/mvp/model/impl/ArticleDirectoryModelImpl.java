package com.hdmb.ireader.mvp.model.impl;

import com.hdmb.ireader.api.HttpManager;
import com.hdmb.ireader.mvp.model.ArticleDirectoryModel;
import com.hdmb.ireader.resp.ArticleDirectory;

import rx.Observable;

/**
 * 作者    武博
 * 时间    2016/7/15 0015 10:09
 * 文件    TbReader
 * 描述
 */
public class ArticleDirectoryModelImpl implements ArticleDirectoryModel {
    @Override
    public Observable<ArticleDirectory> requestArticleDirectory(String articleId) {
        return HttpManager.getInstance().getApiService().queryArticleDirectory(articleId);
    }
}
