package com.hdmb.ireader.mvp.model;

import android.content.Context;

import com.hdmb.ireader.resp.Article;

import java.util.List;

import rx.Observable;

/**
 * 作者    武博
 * 时间    2016/8/24 0024 13:49
 * 文件    TbReader
 * 描述
 */
public interface OfflineModel {

    /**
     * 从书架里查找图书列表
     */
    Observable<List<Article>> loadArticlesFromBookshelf(Context context);
}
