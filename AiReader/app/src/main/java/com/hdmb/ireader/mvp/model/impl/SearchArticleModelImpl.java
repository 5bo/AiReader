package com.hdmb.ireader.mvp.model.impl;

import com.hdmb.ireader.api.HttpManager;
import com.hdmb.ireader.mvp.model.SearchArticleModel;
import com.hdmb.ireader.resp.Article;

import java.util.List;

import rx.Observable;

/**
 * 作者    武博
 * 时间    2016/7/14 0014 15:17
 * 文件    TbReader
 * 描述
 */
public class SearchArticleModelImpl implements SearchArticleModel {
    @Override
    public Observable<List<Article>> queryArticles(String keyword) {
        return HttpManager.getInstance().getApiService().queryArticles(keyword);
    }
}
