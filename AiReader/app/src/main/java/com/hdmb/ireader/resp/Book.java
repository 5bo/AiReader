package com.hdmb.ireader.resp;

import java.text.DecimalFormat;
import java.util.Date;

/**
 * 作者    武博
 * 时间    2016/7/15 0015 15:14
 * 文件    TbReader
 * 描述
 */
public class Book {
    private static final DecimalFormat decimalFormat = new DecimalFormat("0.00");
    private String id;
    private String name;// 书名
    private String fileName;// 文件名
    private Date lastReadTime;// 最后阅读时间
    private int begin = 0;// 从那里开始看书
    private String progress;// 进度比例

    public Book() {

    }

    public Book(String name, String fileName, Date lastReadTime, int begin, String progress) {
        this.name = name;
        this.fileName = fileName;
        this.lastReadTime = lastReadTime;
        this.begin = begin;
        this.progress = progress;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Date getLastReadTime() {
        return lastReadTime;
    }

    public void setLastReadTime(Date lastReadTime) {
        this.lastReadTime = lastReadTime;
    }

    public int getBegin() {
        return begin;
    }

    public void setBegin(int begin) {
        this.begin = begin;
    }

    public String getProgress() {
        return progress;
    }

    public void setProgress(String progress) {
        this.progress = progress;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", fileName='" + fileName + '\'' +
                ", lastReadTime=" + lastReadTime +
                ", begin=" + begin +
                ", progress='" + progress + '\'' +
                '}';
    }
}
