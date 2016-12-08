package com.hdmb.ireader.mvp.model;

import com.hdmb.ireader.resp.CategoryView;

import rx.Observable;

/**
 * 作者    武博
 * 时间    2016/7/25 0025 10:23
 * 文件    TbReader
 * 描述
 */
public interface CategoryViewModel {

    Observable<CategoryView> requestCategoryView(String categoryId, int pageNumber);
}
