package com.hdmb.ireader.txtreader.bean;

import android.graphics.Bitmap;

/**
 * 作者    武博
 * 时间    2016/8/8 0008 18:52
 * 文件    TbReader
 * 描述
 */
public class TxtBitmapCache {

    private Bitmap pageBitmap;
    private Bitmap parvBitmap;
    private Bitmap midBitmap;
    private Bitmap nextBitmap;

    public Bitmap getPageBitmap() {
        return pageBitmap;
    }

    public void setPageBitmap(Bitmap pageBitmap) {
        this.pageBitmap = pageBitmap;
    }

    public Bitmap getPrevBitmap() {
        return parvBitmap;
    }

    public void setPrevBitmap(Bitmap parvBitmap) {
        this.parvBitmap = parvBitmap;
    }

    public Bitmap getMidBitmap() {
        return midBitmap;
    }

    public void setMidBitmap(Bitmap midBitmap) {
        this.midBitmap = midBitmap;
    }

    public Bitmap getNextBitmap() {
        return nextBitmap;
    }

    public void setNextBitmap(Bitmap nextBitmap) {
        this.nextBitmap = nextBitmap;
    }

    @Override
    public String toString() {
        return "TxtBitmapCache{" +
                "pageBitmap=" + pageBitmap +
                ", parvBitmap=" + parvBitmap +
                ", midBitmap=" + midBitmap +
                ", nextBitmap=" + nextBitmap +
                '}';
    }
}
