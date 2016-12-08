package com.hdmb.ireader.mvp.presenter.impl;

import com.hdmb.ireader.mvp.model.SearchArticleModel;
import com.hdmb.ireader.mvp.model.impl.SearchArticleModelImpl;
import com.hdmb.ireader.mvp.presenter.SearchArticlePresenter;
import com.hdmb.ireader.mvp.view.SearchArticleView;
import com.hdmb.ireader.resp.Article;
import com.hdmb.ireader.utils.Log;

import java.util.List;

import rx.Subscriber;

/**
 * 作者    武博
 * 时间    2016/7/14 0014 15:14
 * 文件    TbReader
 * 描述
 */
public class SearchArticlePresenterImpl extends BasePresenterImpl implements SearchArticlePresenter {

    SearchArticleView mSearchArticleView;
    SearchArticleModel mSearchArticleModel;

    public SearchArticlePresenterImpl(SearchArticleView view) {
        this.mSearchArticleView = view;
        mSearchArticleModel = new SearchArticleModelImpl();
    }

    @Override
    public void query(String keyword) {
        toSubscribe(mSearchArticleModel.queryArticles(keyword), new Subscriber<List<Article>>() {
            @Override
            public void onCompleted() {
                Log.e("onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                Log.e("onError");
            }

            @Override
            public void onNext(List<Article> articles) {
                mSearchArticleView.showArticles(articles);
            }
        });
    }
}
