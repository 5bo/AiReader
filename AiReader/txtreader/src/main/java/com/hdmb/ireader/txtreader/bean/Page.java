package com.hdmb.ireader.txtreader.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者    武博
 * 时间    2016/8/8 0008 16:30
 * 文件    TbReader
 * 描述
 */
public class Page {
    int firstElementCharIndex;
    int firstElementParagraphIndex;
    int lastElementCharIndex;
    int lastElementParagraphIndex;
    int pageIndex;
    boolean isFirstPage = false;
    boolean isLastPage = false;
    List<LineChar> linesData = new ArrayList<>();

    public Page() {
    }

    public Page(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public void addLine(LineChar lineChar) {
        linesData.add(lineChar);
    }

    public void clearLine() {
        linesData.clear();
    }

    public int getFirstElementCharIndex() {
        return firstElementCharIndex;
    }

    public void setFirstElementCharIndex(int firstElementCharIndex) {
        this.firstElementCharIndex = firstElementCharIndex;
    }

    public int getFirstElementParagraphIndex() {
        return firstElementParagraphIndex;
    }

    public void setFirstElementParagraphIndex(int firstElementParagraphIndex) {
        this.firstElementParagraphIndex = firstElementParagraphIndex;
    }

    public int getLastElementCharIndex() {
        return lastElementCharIndex;
    }

    public void setLastElementCharIndex(int lastElementCharIndex) {
        this.lastElementCharIndex = lastElementCharIndex;
    }

    public int getLastElementParagraphIndex() {
        return lastElementParagraphIndex;
    }

    public void setLastElementParagraphIndex(int lastElementParagraphIndex) {
        this.lastElementParagraphIndex = lastElementParagraphIndex;
    }

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public boolean isFirstPage() {
        return isFirstPage;
    }

    public void setFirstPage(boolean firstPage) {
        isFirstPage = firstPage;
    }

    public boolean isLastPage() {
        return isLastPage;
    }

    public void setLastPage(boolean lastPage) {
        isLastPage = lastPage;
    }

    public List<LineChar> getLinesData() {
        return linesData;
    }

    public void setLinesData(List<LineChar> linesData) {
        this.linesData = linesData;
    }

    public String getPageString() {
        StringBuilder sb = new StringBuilder();
        for (LineChar lineChar : linesData) {
            sb.append(lineChar.getLineString());
        }
        return sb.toString();
    }
}
