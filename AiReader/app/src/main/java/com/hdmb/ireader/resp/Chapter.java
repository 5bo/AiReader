package com.hdmb.ireader.resp;

/**
 * 作者    武博
 * 时间    2016/7/16 0016 16:04
 * 文件    TbReader
 * 描述
 */
public class Chapter {
    private String title;
    private String content;
    private int order;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }
}
