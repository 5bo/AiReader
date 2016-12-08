package com.hdmb.ireader.txtreader.bean;

/**
 * 作者    武博
 * 时间    2016/8/8 0008 16:33
 * 文件    TbReader
 * 描述
 */
public interface LineChar {
    int getElementSize();

    void addElement(CharElement charElement);

    String getLineString();

    char[] getLineChars();

    void clear();

    boolean hasData();

    CharElement getFirstElement();

    CharElement getLastElement();
}
