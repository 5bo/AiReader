package com.hdmb.ireader.mvp.presenter.impl;

import android.content.Context;

import com.hdmb.ireader.mvp.model.MyBookshelfModel;
import com.hdmb.ireader.mvp.model.impl.MyBookshelfModelImpl;
import com.hdmb.ireader.mvp.presenter.MyBookshelfPresenter;
import com.hdmb.ireader.mvp.view.MyBookshelfView;
import com.hdmb.ireader.resp.Article;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者    武博
 * 时间    2016/7/13 0013 17:56
 * 文件    TbReader
 * 描述
 */
public class MyBookshelfPresenterImpl extends BasePresenterImpl implements MyBookshelfPresenter {

    Context context;
    MyBookshelfModel mMyBookshelfModel;
    MyBookshelfView mMyBookshelfView;

    public MyBookshelfPresenterImpl(Context context, MyBookshelfView view) {
        this.context = context;
        this.mMyBookshelfView = view;
        mMyBookshelfModel = new MyBookshelfModelImpl();
    }

    @Override
    public void loadMyBooks() {
        toSubscribe(mMyBookshelfModel.loadMyBookshelfArticles(context),
                (List<Article> articles) -> {
                    mMyBookshelfView.showMyBookshelfArticles(articles);
                }, (Throwable e) -> {
                    mMyBookshelfView.showMyBookshelfArticles(new ArrayList<>());
                });
    }
}
