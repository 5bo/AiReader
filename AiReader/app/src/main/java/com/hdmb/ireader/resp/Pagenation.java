package com.hdmb.ireader.resp;

/**
 * 作者    武博
 * 时间    2016/7/25 0025 9:58
 * 文件    TbReader
 * 描述
 */
public class Pagenation {
    private int total;// 总页数，如15
    private int current; //当前页索引，如10
    private int prev;// 前一页索引，如9
    private int next;// 后一页索引，如11

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getCurrent() {
        return current;
    }

    public void setCurrent(int current) {
        this.current = current;
    }

    public int getPrev() {
        return prev;
    }

    public void setPrev(int prev) {
        this.prev = prev;
    }

    public int getNext() {
        return next;
    }

    public void setNext(int next) {
        this.next = next;
    }

    @Override
    public String toString() {
        return "Pagenation{" +
                "total=" + total +
                ", current=" + current +
                ", prev=" + prev +
                ", next=" + next +
                '}';
    }
}
