package com.hdmb.ireader.mvp.presenter;

/**
 * 作者    武博
 * 时间    2016/7/14 0014 15:14
 * 文件    TbReader
 * 描述
 */
public interface SearchArticlePresenter extends BasePresenter {

    /**
     * 检索图书
     * @param keyword
     */
    void query(String keyword);
}
