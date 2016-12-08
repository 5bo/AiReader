package com.hdmb.ireader.txtreader.pipeline;

import com.hdmb.ireader.txtreader.Transformer;

/**
 * 作者    武博
 * 时间    2016/8/10 0010 14:14
 * 文件    TbReader
 * 描述
 */
public interface ModeTransformer extends Transformer {
    void refreshView();

    void onLoadFirstPage(boolean isLastPage);

    void onLoadNextPage(boolean isLastPage);

    void onLoadPrevPage(boolean isFirstPage);

    void onLoadPageFromIndex(boolean isFirstPage, boolean isLastPage);

    void onLoadFileExecption();

    void onNoData();

    void onPageSeparateStart();

    void onPageSeparateDone();
}
