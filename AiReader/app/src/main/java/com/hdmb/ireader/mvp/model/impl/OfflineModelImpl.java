package com.hdmb.ireader.mvp.model.impl;

import android.content.Context;

import com.hdmb.ireader.db.BookshelfManager;
import com.hdmb.ireader.mvp.model.OfflineModel;
import com.hdmb.ireader.resp.Article;

import java.util.List;

import rx.Observable;

/**
 * 作者    武博
 * 时间    2016/8/24 0024 13:57
 * 文件    TbReader
 * 描述
 */
public class OfflineModelImpl implements OfflineModel {
    @Override
    public Observable<List<Article>> loadArticlesFromBookshelf(Context context) {
        return Observable.create(subscriber -> {
            subscriber.onNext(BookshelfManager.getBookshelfBooks(context));
        });
    }
}
