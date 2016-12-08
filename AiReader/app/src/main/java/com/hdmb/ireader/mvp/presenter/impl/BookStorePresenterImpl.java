package com.hdmb.ireader.mvp.presenter.impl;

import com.hdmb.ireader.mvp.model.BookStoreModel;
import com.hdmb.ireader.mvp.model.impl.BookStoreModelImpl;
import com.hdmb.ireader.mvp.presenter.BookStorePresenter;
import com.hdmb.ireader.mvp.view.BookStoreView;

/**
 * 作者    武博
 * 时间    2016/7/14 0014 14:39
 * 文件    TbReader
 * 描述
 */
public class BookStorePresenterImpl extends BasePresenterImpl implements BookStorePresenter {

    BookStoreModel bookStoreModel;
    BookStoreView bookStoreView;

    public BookStorePresenterImpl(BookStoreView view) {
        this.bookStoreView = view;
        bookStoreModel = new BookStoreModelImpl();
    }

    @Override
    public void getCategoryList() {
        toSubscribe(bookStoreModel.getCategoryList(), categories -> {
            if (categories != null && categories.size() > 0)
                bookStoreView.showCategoryTab(categories);
            else
                bookStoreView.noData();
        }, throwable -> bookStoreView.noData());
    }
}
