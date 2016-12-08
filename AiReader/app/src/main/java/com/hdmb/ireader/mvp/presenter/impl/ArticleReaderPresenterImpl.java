package com.hdmb.ireader.mvp.presenter.impl;

import android.content.Context;
import android.text.TextUtils;

import com.hdmb.ireader.db.BookshelfManager;
import com.hdmb.ireader.manager.ArticleManager;
import com.hdmb.ireader.mvp.model.ArticleReaderModel;
import com.hdmb.ireader.mvp.model.impl.ArticleReaderModelImpl;
import com.hdmb.ireader.mvp.presenter.ArticleReaderPresenter;
import com.hdmb.ireader.mvp.view.ArticleReaderView;

import rx.Observable;

/**
 * 作者    武博
 * 时间    2016/7/15 0015 15:07
 * 文件    TbReader
 * 描述
 */
public class ArticleReaderPresenterImpl extends BasePresenterImpl implements ArticleReaderPresenter {

    Context context;
    ArticleReaderModel model;
    ArticleReaderView view;

    public ArticleReaderPresenterImpl(Context context, ArticleReaderView view) {
        this.context = context;
        model = new ArticleReaderModelImpl();
        this.view = view;
    }

    @Override
    public void loadArticlePartContent(String articleId, int partId) {
        view.showProgressBar("正在加载第" + partId + "节内容");
        toSubscribe(Observable.just(ArticleManager.getInstance().getPartFilePath(articleId, partId))
                        .flatMap(filepath -> {
                            // 更新已经读到的小节id
                            BookshelfManager.updatePartIdByArticleId(context, articleId, partId);
                            if (TextUtils.isEmpty(filepath)) {// 没有缓存
                                return model.requestArticlePartContent(articleId, partId)
                                        .map(partContent -> // 开始缓存
                                                ArticleManager.getInstance().cachePartContent(articleId, partId, partContent.getContent())
                                        );
                            } else {
                                return Observable.just(ArticleManager.getInstance().getPartFilePath(articleId, partId));
                            }
                        }),
                filename ->
                        view.showContentView(filename),
                e ->
                        view.loadError(e));
    }

    @Override
    public void loadArticleParts(String articleId) {
        toSubscribe(model.requestArticleDirectory(articleId),
                articleDirectory ->
                        view.handleParts(articleDirectory),
                throwable ->
                        view.loadError(throwable));
    }
}
