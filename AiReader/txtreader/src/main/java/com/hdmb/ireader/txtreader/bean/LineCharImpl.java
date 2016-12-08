package com.hdmb.ireader.txtreader.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者    武博
 * 时间    2016/8/8 0008 16:40
 * 文件    TbReader
 * 描述
 */
public class LineCharImpl implements LineChar {

    private List<CharElement> lineData;

    public LineCharImpl() {
        lineData = new ArrayList<>();
    }

    @Override
    public int getElementSize() {
        return lineData.size();
    }

    @Override
    public void addElement(CharElement charElement) {
        lineData.add(charElement);
    }

    @Override
    public String getLineString() {
        return new String(getLineChars());
    }

    @Override
    public char[] getLineChars() {
        char[] chars = new char[lineData.size()];
        for (int i = 0; i < lineData.size(); i++)
            chars[i] = lineData.get(i).data;
        return chars;
    }

    @Override
    public void clear() {
        lineData.clear();
    }

    @Override
    public boolean hasData() {
        return getElementSize() != 0;
    }

    @Override
    public CharElement getFirstElement() {
        return getElementSize() == 0 ? null : lineData.get(0);
    }

    @Override
    public CharElement getLastElement() {
        return getElementSize() == 0 ? null : lineData.get(getElementSize() - 1);
    }
}
