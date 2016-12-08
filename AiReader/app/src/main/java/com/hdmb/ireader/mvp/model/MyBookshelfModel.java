package com.hdmb.ireader.mvp.model;

import android.content.Context;

import com.hdmb.ireader.resp.Article;

import java.util.List;

import rx.Observable;

/**
 * 作者    武博
 * 时间    2016/7/21 0021 15:22
 * 文件    TbReader
 * 描述
 */
public interface MyBookshelfModel {

    Observable<List<Article>> loadMyBookshelfArticles(Context context);
}
