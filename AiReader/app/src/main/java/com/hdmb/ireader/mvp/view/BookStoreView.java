package com.hdmb.ireader.mvp.view;

import com.hdmb.ireader.resp.Category;

import java.util.List;

/**
 * 作者    武博
 * 时间    2016/7/25 0025 9:20
 * 文件    TbReader
 * 描述
 */
public interface BookStoreView {

    void noData();

    void showCategoryTab(List<Category> categories);
}
