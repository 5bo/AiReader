package com.hdmb.ireader.mvp.presenter;

import android.content.Context;

import com.hdmb.ireader.resp.Article;

/**
 * 作者    武博
 * 时间    2016/7/15 0015 9:58
 * 文件    TbReader
 * 描述
 */
public interface ArticleDirectoryPresenter extends BasePresenter {

    void queryArticleDirectory(String articleId);

    void addBookshelf(Context context, Article article);
}
