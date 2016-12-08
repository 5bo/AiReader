package com.hdmb.ireader.mvp.model;

import com.hdmb.ireader.resp.Category;

import java.util.List;

import rx.Observable;

/**
 * 作者    武博
 * 时间    2016/7/25 0025 9:26
 * 文件    TbReader
 * 描述
 */
public interface BookStoreModel {

    Observable<List<Category>> getCategoryList();
}
