package com.hdmb.ireader.txtreader.bean;

/**
 * 作者    武博
 * 时间    2016/8/8 0008 16:58
 * 文件    TbReader
 * 描述    段落
 */
public class Paragraph {
    int index;
    String data;

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Paragraph{" +
                "index=" + index +
                ", data='" + data + '\'' +
                '}';
    }
}
