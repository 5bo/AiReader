package com.hdmb.ireader.mvp.presenter.impl;

import android.content.Context;

import com.hdmb.ireader.db.BookshelfManager;
import com.hdmb.ireader.mvp.model.ArticleDirectoryModel;
import com.hdmb.ireader.mvp.model.impl.ArticleDirectoryModelImpl;
import com.hdmb.ireader.mvp.presenter.ArticleDirectoryPresenter;
import com.hdmb.ireader.mvp.view.ArticleDirectoryView;
import com.hdmb.ireader.resp.Article;

import rx.Observable;

/**
 * 作者    武博
 * 时间    2016/7/15 0015 9:59
 * 文件    TbReader
 * 描述
 */
public class ArticleDirectoryPresenterImpl extends BasePresenterImpl implements ArticleDirectoryPresenter {

    ArticleDirectoryModel model;
    ArticleDirectoryView view;

    public ArticleDirectoryPresenterImpl(ArticleDirectoryView view) {
        model = new ArticleDirectoryModelImpl();
        this.view = view;
    }

    @Override
    public void queryArticleDirectory(String articleId) {
        toSubscribe(model.requestArticleDirectory(articleId),
               articleDirectory -> view.showDirectory(articleDirectory)
                , e -> {
        });
    }

    @Override
    public void addBookshelf(Context context, Article article) {
        Observable<Long> o = Observable.just(BookshelfManager.addBookshelf(context, article));
        toSubscribe(o, (row) -> {
            view.showAddBookshelfStatus(row != -1 ? true : false);
        }, e -> {
        });
    }
}
