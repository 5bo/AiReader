package com.hdmb.ireader.mvp.model.impl;

import com.hdmb.ireader.api.HttpManager;
import com.hdmb.ireader.mvp.model.CategoryViewModel;
import com.hdmb.ireader.resp.CategoryView;

import rx.Observable;

/**
 * 作者    武博
 * 时间    2016/7/25 0025 10:37
 * 文件    TbReader
 * 描述
 */
public class CategoryViewModelImpl implements CategoryViewModel {
    @Override
    public Observable<CategoryView> requestCategoryView(String categoryId, int pageNumber) {
        return HttpManager.getInstance().getApiService().getCategoryView(categoryId, pageNumber);
    }
}
