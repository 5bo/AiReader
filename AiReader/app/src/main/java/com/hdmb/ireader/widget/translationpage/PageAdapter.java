package com.hdmb.ireader.widget.translationpage;

import android.view.View;

/**
 * 作者    武博
 * 时间    2016/7/27 0027 15:38
 * 文件    TbReader
 * 描述
 */
public abstract class PageAdapter {
    /**
     * @return 页面view
     */
    public abstract View getView();

    public abstract int getCount();

    /**
     * 将内容添加到view中
     *
     * @param view     包含内容的view
     * @param position 第position页
     */
    public abstract void addContent(View view, int position);
}
