package com.hdmb.ireader.mvp.model.impl;

import android.content.Context;

import com.hdmb.ireader.db.BookshelfManager;
import com.hdmb.ireader.mvp.model.MyBookshelfModel;
import com.hdmb.ireader.resp.Article;

import java.util.List;

import rx.Observable;

/**
 * 作者    武博
 * 时间    2016/7/21 0021 15:23
 * 文件    TbReader
 * 描述
 */
public class MyBookshelfModelImpl implements MyBookshelfModel {
    @Override
    public Observable<List<Article>> loadMyBookshelfArticles(Context context) {
        return Observable.create(subscriber -> {
            subscriber.onNext(BookshelfManager.getBookshelfBooks(context));
        });
    }
}
