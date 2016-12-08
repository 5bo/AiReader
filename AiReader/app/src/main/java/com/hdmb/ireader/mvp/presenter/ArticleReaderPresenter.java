package com.hdmb.ireader.mvp.presenter;

/**
 * 作者    武博
 * 时间    2016/7/15 0015 15:07
 * 文件    TbReader
 * 描述
 */
public interface ArticleReaderPresenter extends BasePresenter {

    void loadArticlePartContent(String articleId, int partId);

    void loadArticleParts(String articleId);
}
