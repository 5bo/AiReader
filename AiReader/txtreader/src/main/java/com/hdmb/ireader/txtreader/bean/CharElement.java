package com.hdmb.ireader.txtreader.bean;

/**
 * 作者    武博
 * 时间    2016/8/8 0008 16:33
 * 文件    TbReader
 * 描述
 */
public class CharElement {
    public char data;
    public int paragraphIndex;
    public int charIndex;
    public float x;
    public float y;

    /**
     * 是否空白字符
     *
     * @return
     */
    public boolean isWhitespace() {
        return Character.isWhitespace(data);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof CharElement){
            CharElement ce = (CharElement) obj;
            return data == ce.data && paragraphIndex == ce.paragraphIndex && charIndex == ce.charIndex;
        }
        return false;
    }
}
