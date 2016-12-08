package com.hdmb.ireader.mvp.view;

import com.hdmb.ireader.resp.CategoryView;

/**
 * 作者    武博
 * 时间    2016/7/25 0025 10:38
 * 文件    TbReader
 * 描述
 */
public interface CategoryViewView {

    void noDate();

    void showArticleView(CategoryView categoryView);

    void showAddBookshelfStatus(boolean status);
}
