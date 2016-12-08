package com.hdmb.ireader.txtreader;

public interface TxtPageChangeListsner {
    /**
     * @param pageIndex 当前页下标
     * @param pageNums  页数，分页没完成是返回的是-1
     */
    void onCurrentPage(int pageIndex, int pageNums);
}
