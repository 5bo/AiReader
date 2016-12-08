package com.hdmb.ireader.mvp.presenter;

import android.content.Context;

import com.hdmb.ireader.resp.Article;

/**
 * 作者    武博
 * 时间    2016/7/25 0025 10:15
 * 文件    TbReader
 * 描述
 */
public interface CategoryViewPresenter extends BasePresenter {
    void getCategoryArticleList(String categoryId, int pageNumber);

    /**
     * 加入书架
     * @param context
     * @param article
     */
    void addBookshelf(Context context, Article article);
}
