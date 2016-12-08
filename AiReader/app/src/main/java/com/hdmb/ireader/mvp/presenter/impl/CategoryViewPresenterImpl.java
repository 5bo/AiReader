package com.hdmb.ireader.mvp.presenter.impl;

import android.content.Context;

import com.hdmb.ireader.db.BookshelfManager;
import com.hdmb.ireader.mvp.model.CategoryViewModel;
import com.hdmb.ireader.mvp.model.impl.CategoryViewModelImpl;
import com.hdmb.ireader.mvp.presenter.CategoryViewPresenter;
import com.hdmb.ireader.mvp.view.CategoryViewView;
import com.hdmb.ireader.resp.Article;

import rx.Observable;

/**
 * 作者    武博
 * 时间    2016/7/25 0025 10:21
 * 文件    TbReader
 * 描述
 */
public class CategoryViewPresenterImpl extends BasePresenterImpl implements CategoryViewPresenter {

    CategoryViewModel model;
    CategoryViewView view;

    public CategoryViewPresenterImpl(CategoryViewView view) {
        this.view = view;
        model = new CategoryViewModelImpl();
    }

    @Override
    public void getCategoryArticleList(String categoryId, int pageNumber) {
        toSubscribe(model.requestCategoryView(categoryId, pageNumber),
                categoryView -> {
                    if (categoryView.getArticles() != null || categoryView.getArticles().size() > 0) {
                        view.showArticleView(categoryView);
                    } else {
                        view.noDate();
                    }
                }, throwable -> {
                    view.noDate();
                });
    }

    @Override
    public void addBookshelf(Context context, Article article) {
        Observable<Long> o = Observable.just(BookshelfManager.addBookshelf(context, article));
        toSubscribe(o, (row) -> {
            view.showAddBookshelfStatus(row != -1 ? true : false);
        }, e -> {
            view.noDate();
        });
    }
}
