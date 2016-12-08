package com.hdmb.ireader.mvp.model.impl;

import com.hdmb.ireader.api.HttpManager;
import com.hdmb.ireader.mvp.model.BookStoreModel;
import com.hdmb.ireader.resp.Category;

import java.util.List;

import rx.Observable;

/**
 * 作者    武博
 * 时间    2016/7/25 0025 9:27
 * 文件    TbReader
 * 描述
 */
public class BookStoreModelImpl implements BookStoreModel {
    @Override
    public Observable<List<Category>> getCategoryList() {
        return HttpManager.getInstance().getApiService().getCategoryList();
    }
}
