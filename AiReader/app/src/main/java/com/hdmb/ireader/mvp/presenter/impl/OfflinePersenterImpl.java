package com.hdmb.ireader.mvp.presenter.impl;

import android.content.Context;

import com.hdmb.ireader.mvp.model.OfflineModel;
import com.hdmb.ireader.mvp.model.impl.OfflineModelImpl;
import com.hdmb.ireader.mvp.presenter.OfflinePersenter;
import com.hdmb.ireader.mvp.view.OfflineView;
import com.hdmb.ireader.resp.Article;

import java.util.List;

import rx.Subscriber;

/**
 * 作者    武博
 * 时间    2016/8/24 0024 14:12
 * 文件    TbReader
 * 描述
 */
public class OfflinePersenterImpl extends BasePresenterImpl implements OfflinePersenter {

    Context context;
    OfflineView view;
    OfflineModel model;
    public OfflinePersenterImpl(Context context, OfflineView view) {
        this.view = view;
        model = new OfflineModelImpl();
    }

    @Override
    public void loadArticles() {
        toSubscribe(model.loadArticlesFromBookshelf(context), new Subscriber<List<Article>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(List<Article> articles) {
                view.showArticles(articles);
            }
        });
    }
}
