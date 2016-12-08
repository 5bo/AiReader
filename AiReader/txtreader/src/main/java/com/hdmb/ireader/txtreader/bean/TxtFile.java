package com.hdmb.ireader.txtreader.bean;

/**
 * 作者    武博
 * 时间    2016/8/10 0010 15:17
 * 文件    TbReader
 * 描述
 */
public class TxtFile {
    int part;
    String filePath;
    String bookName;

    public int getPart() {
        return part;
    }

    public void setPart(int part) {
        this.part = part;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }
}
